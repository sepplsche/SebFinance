package de.seppl.sebfinance.pdf;

import java.util.ArrayList;
import java.util.Collection;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;


public class KontoauszugParser
{
    private final Collection<ContentParser> parsers;

    public KontoauszugParser(Collection<ContentParser> parsers)
    {
        this.parsers = parsers;
    }

    public Kontoauszug kontoauszug(RawPdf raw)
    {
        Collection<Kontoauszug> auszuege = new ArrayList<>();
        parsers.forEach(parser -> {
            Collection<Posten> posten = parser.posten(raw);
            if (posten.isEmpty())
                return;
            auszuege.add(new Kontoauszug(parser.monat(raw), posten));
        });
        if (auszuege.isEmpty())
            throw new IllegalStateException("Kein Kontoauszug für Raw-PDF: " + raw.lines());
        return auszuege.iterator().next();
    }
}
