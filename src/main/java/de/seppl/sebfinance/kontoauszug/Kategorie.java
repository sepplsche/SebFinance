package de.seppl.sebfinance.kontoauszug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


public interface Kategorie
{
    String name();

    String[] verwendungen();

    static Kategorie of(String verwendung)
    {
        Collection<Kategorie> values = new ArrayList<>();
        values.addAll(Arrays.asList(Gutschrift.values()));
        values.addAll(Arrays.asList(Lastschrift.values()));

        return values.stream() //
            .filter(value -> Stream.of(value.verwendungen()).anyMatch(v -> verwendung.contains(v))) //
            .findFirst() //
            .orElse(Lastschrift.SONSTIGES);
    }
}
