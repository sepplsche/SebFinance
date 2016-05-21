package de.seppl.sebfinance.pdf;

import java.time.LocalDate;
import java.util.Collection;

import de.seppl.sebfinance.kontoauszug.Posten;


public interface ContentParser
{
    LocalDate monat(RawPdf raw);

    Collection<Posten> posten(RawPdf raw);
}
