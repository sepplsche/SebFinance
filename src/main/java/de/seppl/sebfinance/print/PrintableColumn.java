package de.seppl.sebfinance.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class PrintableColumn<T>
{
    private final String head;
    private final Function<T, ?> column;
    private final Align align;

    private PrintableColumn(String head, Function<T, ?> column, Align align)
    {
        this.head = head;
        this.column = column;
        this.align = align;
    }

    public static <T> PrintableColumn<T> right(String head, Function<T, ?> columnToString)
    {
        return new PrintableColumn<>(head, columnToString, Align.RIGHT);
    }

    public static <T> PrintableColumn<T> left(String head, Function<T, ?> columnToString)
    {
        return new PrintableColumn<>(head, columnToString, Align.LEFT);
    }

    public String head()
    {
        return head;
    }

    public String value(T element)
    {
        return String.valueOf(column.apply(element));
    }

    public int size(Collection<T> elements)
    {
        List<String> strings = new ArrayList<>(elements.stream() //
            .map(column) //
            .map(String::valueOf) //
            .collect(Collectors.toList()));
        strings.add(head);
        return strings.stream().mapToInt(s -> s.length()).max().orElse(0);
    }

    public Align align()
    {
        return align;
    }

    public static enum Align
    {
        LEFT, RIGHT;
    }
}
