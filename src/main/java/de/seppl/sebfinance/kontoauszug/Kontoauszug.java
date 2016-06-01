package de.seppl.sebfinance.kontoauszug;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

public class Kontoauszug implements Comparable<Kontoauszug> {
    private final LocalDate monat;
    private final Konto konto;
    private final Collection<Posten> posten;

    public Kontoauszug(LocalDate monat, String konto, Collection<Posten> posten) {
        this.monat = checkNotNull(monat);
        this.konto = Konto.of(konto);
        this.posten = ImmutableList.copyOf(posten);
    }

    public LocalDate monat() {
        return monat;
    }

    public Konto konto() {
        return konto;
    }

    public Collection<Posten> posten() {
        return posten;
    }

    @Override
    public int compareTo(Kontoauszug o) {
        return monat.compareTo(o.monat);
    }

    public static enum Konto {
        SEB("304302916"), //
        MARY("302302056");
        private final String nummer;

        private Konto(String nummer) {
            this.nummer = checkNotNull(nummer);
        }

        public static Konto of(String nummer) {
            return Stream.of(Konto.values()) //
                    .filter(value -> value.nummer.equals(nummer)) //
                    .findFirst() //
                    .orElseThrow(
                            () -> new IllegalArgumentException("Kein Konto zur Nummer '" + nummer + "' gefunden!"));

        }

    }
}
