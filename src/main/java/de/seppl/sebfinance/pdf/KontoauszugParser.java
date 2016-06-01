package de.seppl.sebfinance.pdf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;

public class KontoauszugParser {
    private final Collection<ContentParser> parsers;

    public KontoauszugParser(Collection<ContentParser> parsers) {
        this.parsers = checkNotNull(parsers);
    }

    public Kontoauszug kontoauszug(RawPdf raw) {
        Collection<Kontoauszug> auszuege = new ArrayList<>();
        parsers.forEach(parser -> {
            LocalDate monat = parser.monat(raw);
            Collection<Posten> posten = parser.posten(raw);
            if (posten.isEmpty())
                return;
            auszuege.add(new Kontoauszug(monat, konto(raw), posten));
        });
        if (auszuege.isEmpty())
            throw new IllegalStateException("Kein Kontoauszug für Raw-PDF: " + raw.lines());
        return auszuege.iterator().next();
    }

    private String konto(RawPdf raw) {
        return StringUtils.substringAfter(StringUtils.substringBefore(raw.fileName(), "_"), "rep");
    }
}
