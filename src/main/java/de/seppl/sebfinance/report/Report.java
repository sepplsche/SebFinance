package de.seppl.sebfinance.report;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkNotNull;

import de.seppl.sebfinance.kontoauszug.Kategorie;

public class Report {

    private final Kategorie kategorie;
    private final Map<Integer, Integer> betraege;

    public Report(Kategorie kategorie, Map<Integer, Integer> betraege) {
        this.kategorie = checkNotNull(kategorie);
        this.betraege = ImmutableMap.copyOf(betraege);
    }

    public Kategorie kategorie() {
        return kategorie;
    }

    public int betrag(int year) {
        return Optional.ofNullable(betraege.get(year)).orElse(0);
    }
}
