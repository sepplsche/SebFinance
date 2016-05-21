package de.seppl.sebfinance.pdf;

import java.util.ArrayList;
import java.util.Collection;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;


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
            try
            {
                auszuege.add(new Kontoauszug(parser.monat(raw), parser.posten(raw)));
            }
            catch (Exception e)
            {
                // ignore
            }
        });
        if (auszuege.isEmpty())
            throw new IllegalStateException("Kein Kontoauszug für Raw-PDF: " + raw.lines());
        return auszuege.iterator().next();
    }
}
