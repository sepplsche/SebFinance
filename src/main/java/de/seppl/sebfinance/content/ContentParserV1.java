package de.seppl.sebfinance.content;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;


public class ContentParserV1
    implements ContentParser
{

    private static final String MARKER_MONAT_START = "Kontoauszug ";
    private static final String MARKER_MONAT_BIS = " - ";
    private static final String MARKER_MONAT_END = " Kontonummer";

    private static final String MARKER_POSTEN_PAGE_FIRST_BEGIN = "KONTOSTAND";
    private static final String MARKER_POSTEN_PAGE_END = "/";
    private static final String MARKER_POSTEN_PAGE_NEXT_BEGIN = "Datum Text Gutschrift Lastschrift Valuta Saldo";
    private static final String MARKER_POSTEN_PAGE_LAST_END = "TOTAL";

    @Override
    public Kontoauszug kontoauszug(Collection<String> content)
    {
        LocalDate monat = parseMonat(content);
        Collection<Posten> posten = parsePosten(content);
        if (posten.isEmpty())
            throw new IllegalStateException("Keine Posten");
        return new Kontoauszug(monat, posten);
    }

    private LocalDate parseMonat(Collection<String> content)
    {
        return content.stream() //
            .filter(line -> line.startsWith(MARKER_MONAT_START)) //
            .findFirst() //
            .map(monatLine -> StringUtils.substringAfter(monatLine, MARKER_MONAT_BIS)) //
            .map(monatLine -> StringUtils.substringBefore(monatLine, MARKER_MONAT_END)) //
            .map(monatLine -> LocalDate.parse(monatLine, DateTimeFormatter.ofPattern("dd.MM.uuuu"))) //
            .get();
    }

    private Collection<Posten> parsePosten(Collection<String> content)
    {
        Collection<String> postenLines = postenLines(content);
        return posten(postenLines);
    }

    private Collection<String> postenLines(Collection<String> content)
    {
        Collection<String> postenLines = new ArrayList<>();

        boolean firstPage = true;
        boolean isForPosten = false;
        for (String line : content)
        {
            if (line.contains(MARKER_POSTEN_PAGE_END) //
                || line.contains(MARKER_POSTEN_PAGE_LAST_END))
                isForPosten = false;

            if (isForPosten)
                postenLines.add(line);

            if (firstPage && line.contains(MARKER_POSTEN_PAGE_FIRST_BEGIN))
            {
                firstPage = false;
                isForPosten = true;
            }
            if (!firstPage && line.contains(MARKER_POSTEN_PAGE_NEXT_BEGIN))
                isForPosten = true;
        }
        return postenLines;
    }

    private Collection<Posten> posten(Collection<String> postenLines)
    {
        Collection<Posten> posten = new ArrayList<>();

        Collection<String> realPostenLines = new ArrayList<>();

        int lastBetrag = 0;
        LocalDate lastValuta = null;

        for (String line : postenLines)
        {
            try
            {
                int betrag = Integer.parseInt(StringUtils.substringBefore(line.replace(" ", ""), "."));
                String date = StringUtils.substringAfter(line, ".").substring(3, 11);
                LocalDate valuta = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.uu"));

                if (lastValuta != null && betrag > 0)
                {
                    String verwendung = StringUtils.join(realPostenLines, " ");
                    Kategorie kategorie = Kategorie.of(verwendung);
                    posten.add(new Posten(lastValuta, lastBetrag, kategorie, verwendung));
                }

                lastBetrag = betrag;
                lastValuta = valuta;
                realPostenLines.clear();
            }
            catch (Exception e)
            {
                // kein neuer Posten
            }
            realPostenLines.add(line);
        }

        if (lastValuta != null)
        {
            String verwendung = StringUtils.join(realPostenLines, " ");
            Kategorie kategorie = Kategorie.of(verwendung);
            posten.add(new Posten(lastValuta, lastBetrag, kategorie, verwendung));
        }

        return posten;
    }
}
