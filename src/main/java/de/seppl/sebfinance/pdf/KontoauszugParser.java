package de.seppl.sebfinance.pdf;

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
        parsers.forEach(parser -> {
            try
            {
                new Kontoauszug(parser.monat(raw), parser.posten(raw));
            }
            catch (Exception e)
            {

            }
        });
    }
}
