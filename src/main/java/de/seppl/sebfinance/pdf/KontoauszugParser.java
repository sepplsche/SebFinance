package de.seppl.sebfinance.pdf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;

public class KontoauszugParser {
    private final Collection<ContentParser> parsers;
    private final LocalDate date;

    public KontoauszugParser(Collection<ContentParser> parsers, LocalDate date) {
        this.parsers = checkNotNull(parsers);
        this.date = checkNotNull(date);
    }

    public boolean filter(RawPdf raw) {
        return parsers.stream() //
                .map(parser -> parser.monat(raw)) //
                .anyMatch(monat -> monat.isAfter(date));
    }

    public Kontoauszug kontoauszug(RawPdf raw) {
        Collection<Kontoauszug> auszuege = new ArrayList<>();
        parsers.forEach(parser -> {
            LocalDate monat = parser.monat(raw);
            Collection<Posten> posten = parser.posten(raw);
            if (posten.isEmpty())
                return;
            auszuege.add(new Kontoauszug(monat, posten));
        });
        if (auszuege.isEmpty())
            throw new IllegalStateException("Kein Kontoauszug für Raw-PDF: " + raw.lines());
        return auszuege.iterator().next();
    }
}
