package de.seppl.sebfinance.pdf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Posten;


public class ContentParserV2
    implements ContentParser
{
    private static final String MARKER_MONAT_START = "Kontoauszug ";
    private static final String MARKER_MONAT_BIS = " - ";
    private static final String MARKER_MONAT_END = " Kontonummer";

    private static final String MARKER_POSTEN_PAGE_FIRST_BEGIN = "Kontostand";
    private static final String MARKER_POSTEN_PAGE_END = " Seite ";
    private static final String MARKER_POSTEN_PAGE_NEXT_BEGIN = "Datum Text Gutschrift Lastschrift Valuta Saldo";
    private static final String MARKER_POSTEN_PAGE_LAST_END = "Total";

    @Override
    public LocalDate monat(RawPdf raw)
    {
        return raw.lines().stream() //
            .filter(line -> line.startsWith(MARKER_MONAT_START)) //
            .findFirst() //
            .map(monatLine -> StringUtils.substringAfter(monatLine, MARKER_MONAT_BIS)) //
            .map(monatLine -> StringUtils.substringBefore(monatLine, MARKER_MONAT_END)) //
            .map(monatLine -> LocalDate.parse(monatLine, DateTimeFormatter.ofPattern("dd.MM.uuuu"))) //
            .get();
    }

    @Override
    public Collection<Posten> posten(RawPdf raw)
    {
        return posten(postenLines(raw.lines()));
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

        List<String> realPostenLines = new ArrayList<>();
        LocalDate lastValuta = null;
        for (String line : postenLines)
        {
            Optional<LocalDate> valuta = valuta(line);
            if (valuta.isPresent())
            {
                if (lastValuta != null)
                {
                    int betrag = betrag(realPostenLines.get(realPostenLines.size() - 1));
                    if (betrag > -1)
                    {
                        if (betrag > 0)
                        {
                            String verwendung = StringUtils.join(realPostenLines, " ");
                            posten.add(
                                new Posten(valuta.get(), betrag, Kategorie.of(verwendung), verwendung));
                        }
                        realPostenLines.clear();
                    }
                }
                lastValuta = valuta.get();
            }
            realPostenLines.add(line);
        }

        if (lastValuta != null)
        {
            int betrag = betrag(realPostenLines.get(realPostenLines.size() - 1));
            if (betrag > 0)
            {
                String verwendung = StringUtils.join(realPostenLines, " ");
                posten.add(new Posten(lastValuta, betrag, Kategorie.of(verwendung), verwendung));
            }
        }

        return posten;
    }

    private int betrag(String line)
    {
        line = line.replace(" ", "");

        String subLine = StringUtils.substringAfter(line, ".");
        if (subLine.length() == 2)
        {
            String betragStr = StringUtils.substringBefore(line, ".");
            return toInt(betragStr);
        }
        else if (subLine.contains("."))
        {
            String betragStr = StringUtils.substringBefore(subLine, ".");
            betragStr = betragStr.substring(2);
            return toInt(betragStr);
        }
        return -1;
    }

    private int toInt(String betragStr)
    {
        try
        {
            return Integer.parseInt(betragStr);
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    private Optional<LocalDate> valuta(String line)
    {
        if (line.length() < 11)
            return Optional.empty();
        return date(line.substring(0, 8));
    }

    private Optional<LocalDate> date(String date)
    {
        try
        {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.uu")));
        }
        catch (Exception e)
        {
            return Optional.empty();
        }
    }
}