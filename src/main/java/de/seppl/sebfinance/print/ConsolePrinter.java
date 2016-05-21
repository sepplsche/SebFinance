package de.seppl.sebfinance.print;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ConsolePrinter<T>
{

    private final ScheduledThreadPoolExecutor executor;
    private final String elementsName;
    private final Collection<PrintableColumn<T>> columns;
    private final Collection<T> elements;

    public ConsolePrinter(String elementsName, Collection<PrintableColumn<T>> columns,
        Collection<T> elements)
    {
        this.elementsName = elementsName;
        this.columns = columns;
        this.elements = elements;
        executor = new ScheduledThreadPoolExecutor(2);
    }

    public ConsolePrinter(String elementsName, Collection<PrintableColumn<T>> columns)
    {
        this(elementsName, columns, Collections.emptyList());
    }

    public void print()
    {
        if (elements.isEmpty())
            System.out.println("nothing to print");
        print(elements);
    }

    public void print(Collection<T> elements)
    {
        System.out.println(String.format("printing %s %s:", elements.size(), elementsName));

        print(columns, elements.stream().sorted().collect(Collectors.toList()));
    }

    public void print(Callable<Collection<T>> domainCall)
    {
        Future<Collection<T>> elementsFuture = executor.submit(domainCall);
        waitForElements(elementsFuture);
        try
        {
            Collection<T> elements = elementsFuture.get();
            if (elements.isEmpty())
            {
                System.out.println(String.format("No %s found!", elementsName));
                return;
            }
            int foundSize = elements.size();
            elements = applyAdditionalOperations(elements);
            int actualSize = elements.size();
            System.out.println(String.format("%d %s gefunden, %d %s aufgelistet", //
                foundSize, elementsName, actualSize, elementsName));
            print(columns, elements);
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new IllegalStateException(e);
        }
    }

    private void waitForElements(Future<Collection<T>> elementsFuture)
    {
        System.out.print("Calulating " + elementsName);
        while (!elementsFuture.isDone())
        {
            System.out.print(".");
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // ignore
            }
        }
        System.out.println();
    }

    protected Collection<T> applyAdditionalOperations(Collection<T> elements)
    {
        return elements;
    }

    private void print(Collection<PrintableColumn<T>> columns, Collection<T> elements)
    {
        LinkedList<Column> headLine = columns.stream() //
            .map(c -> new Column(c.head(), c.size(elements), c.left())) //
            .collect(Collectors.toCollection(() -> new LinkedList<>()));

        Stream<LinkedList<Column>> lines = elements.stream() //
            .map(e -> line(columns, e, elements));

        print(headLine, lines);
    }

    private LinkedList<Column> line(Collection<PrintableColumn<T>> columns, T element,
        Collection<T> elements)
    {
        return columns.stream() //
            .map(c -> new Column(c.value(element), c.size(elements), c.left())) //
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
        return "|" + (column.left ? printLeftBound(column) : printRightBound(column))
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
        private final boolean left;

        public Column(String value, int size, boolean left)
        {
            this.value = value;
            this.size = size;
            this.left = left;
        }
    }
}
