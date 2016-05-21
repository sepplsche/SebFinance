package de.seppl.sebfinance.pdf;

import java.util.Collection;


public class RawPdf
{
    private final Collection<String> lines;

    public RawPdf(Collection<String> lines)
    {
        this.lines = lines;
    }

    public Collection<String> lines()
    {
        return lines;
    }
}
