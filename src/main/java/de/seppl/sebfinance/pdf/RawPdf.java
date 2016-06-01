package de.seppl.sebfinance.pdf;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

public class RawPdf {
    private final String fileName;
    private final Collection<String> lines;

    public RawPdf(String fileName, Collection<String> lines) {
        this.fileName = checkNotNull(fileName);
        this.lines = ImmutableList.copyOf(lines);
    }

    public String fileName() {
        return fileName;
    }

    public Collection<String> lines() {
        return lines;
    }
}
