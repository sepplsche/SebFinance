package de.seppl.sebfinance.kontoauszug;

import java.time.LocalDate;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;


public class Kontoauszug
{

    private final LocalDate monat;
    private final Collection<Posten> posten;

    public Kontoauszug(LocalDate monat, Collection<Posten> posten)
    {
        this.monat = monat;
        this.posten = posten;
        checkArgument(!posten.isEmpty(), "Keine Posten bei Monat: " + monat);
    }

    public LocalDate monat()
    {
        return monat;
    }

    public Collection<Posten> posten()
    {
        return posten;
    }
}
