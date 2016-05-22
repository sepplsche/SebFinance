package de.seppl.sebfinance.print;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import de.seppl.sebfinance.print.PrintableColumn.Align;


public class ConsolePrinter<T>
{
    private final Collection<PrintableColumn<T>> columns;

    public ConsolePrinter(Collection<PrintableColumn<T>> columns)
    {
        this.columns = ImmutableList.copyOf(columns);
    }

    public void print(Collection<T> elements)
    {
        LinkedList<Column> headLine = columns.stream() //
            .map(c -> new Column(c.head(), c.size(elements), c.align())) //
            .collect(Collectors.toCollection(() -> new LinkedList<>()));

        Stream<LinkedList<Column>> lines = elements.stream() //
            .map(e -> line(columns, e, elements));

        print(headLine, lines);
    }

    private LinkedList<Column> line(Collection<PrintableColumn<T>> columns, T element,
        Collection<T> elements)
    {
        return columns.stream() //
            .map(c -> new Column(c.value(element), c.size(elements), c.align())) //
            .collect(Collectors.toCollection(() -> new LinkedList<>()));
    }

    private void print(LinkedList<Column> headLine, Stream<LinkedList<Column>> lines)
    {
        int maxSize = headLine.stream().map(column -> column.size).reduce(0, (a, b) -> (a + b));
        maxSize += (headLine.size() + 1) * 3 - 2;

        System.out.println(printLine(maxSize, "=", false));
        printLine(headLine);
        System.out.println(printLine(maxSize, "-", false));
        lines.forEach(l -> printLine(l));
        System.out.println(printLine(maxSize, "=", false));
    }

    private void printLine(LinkedList<Column> line)
    {
        Column lastColumn = line.getLast();
        line.stream()
            .forEach(column -> System.out.print(printColumn(column, lastColumn.equals(column))));
        System.out.println();
    }

    private String printLine(int maxSize, String c, boolean startSpace)
    {
        StringBuilder cs = new StringBuilder(startSpace ? " " : "");
        for (int i = 0; i < maxSize; i++)
            cs.append(c);
        return cs.toString();
    }

    private String printColumn(Column column, boolean lastColumn)
    {
        return "|" + (column.align == Align.LEFT ? printLeftBound(column) : printRightBound(column))
            + (lastColumn ? "|" : "");
    }

    private String printLeftBound(Column column)
    {
        return " " + column.value + printLine(column.size - column.value.length(), " ", true);
    }

    private String printRightBound(Column column)
    {
        return printLine(column.size - column.value.length(), " ", true) + column.value + " ";
    }

    private static class Column
    {
        private final String value;
        private final int size;
        private final Align align;

        public Column(String value, int size, Align align)
        {
            this.value = value;
            this.size = size;
            this.align = align;
        }
    }
}
