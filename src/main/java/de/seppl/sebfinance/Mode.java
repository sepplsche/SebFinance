package de.seppl.sebfinance;

import java.util.stream.Stream;

/**
 * @author Seppl
 */
public enum Mode {
    NORMAL, CLEAN;

    public static Mode of(String value) {
        return Stream.of(Mode.values()) //
                .filter(mode -> mode.name().startsWith(value.toUpperCase())) //
                .findFirst() //
                .orElse(Mode.NORMAL);
    }
}
