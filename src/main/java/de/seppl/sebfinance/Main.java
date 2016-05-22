package de.seppl.sebfinance;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import static de.seppl.sebfinance.print.PrintableColumn.left;
import static de.seppl.sebfinance.print.PrintableColumn.right;

import de.seppl.sebfinance.argument.ArgumentParser;
import de.seppl.sebfinance.argument.Arguments;
import de.seppl.sebfinance.argument.Arguments.EmptyFile;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Lastschrift;
import de.seppl.sebfinance.kontoauszug.Posten;
import de.seppl.sebfinance.pdf.ContentParser;
import de.seppl.sebfinance.pdf.ContentParserV1;
import de.seppl.sebfinance.pdf.ContentParserV2;
import de.seppl.sebfinance.pdf.KontoauszugParser;
import de.seppl.sebfinance.pdf.PdfParser;
import de.seppl.sebfinance.print.ConsolePrinter;
import de.seppl.sebfinance.print.PrintableColumn;
import de.seppl.sebfinance.report.Report;
import de.seppl.sebfinance.report.ReportBuilder;
import de.seppl.sebfinance.report.Report.ReportLine;


public class Main
{

    public static void main(String[] args)
    {
        Collection<File> pdfs = files(args);
        Collection<Kontoauszug> auszuege = parse(pdfs);
        printAuszuege(auszuege);
    }

    private static Collection<File> files(String[] args)
    {
        System.out.println(String.format("reading arguments %s", StringUtils.join(args, " ")));

        Arguments arguments = new Arguments();
        ArgumentParser argsParser = new ArgumentParser(args);
        try
        {
            File pdf = argsParser.parse(arguments.pdf());

            if (!(pdf instanceof EmptyFile))
                return Arrays.asList(pdf);

            File dir = argsParser.parse(arguments.dir());

            if (dir instanceof EmptyFile)
                throw new IllegalArgumentException("No files found in dir: " + dir);

            if (!dir.isDirectory())
                throw new IllegalArgumentException("No direcory:" + dir);

            return Arrays.asList(dir.listFiles());
        }
        finally
        {
            System.out.println("arguments read.");
        }
    }

    private static Collection<Kontoauszug> parse(Collection<File> pdfs)
    {
        System.out.println(String.format("parsing %s PDFs...", pdfs.size()));

        PdfParser pdfParser = new PdfParser();
        Collection<ContentParser> contentParsers = Arrays.asList(//
            new ContentParserV1(), //
            new ContentParserV2());
        KontoauszugParser kontoauszugParser = new KontoauszugParser(contentParsers);

        try
        {
            return pdfs.stream() //
                .filter(pdf -> pdf.getName().startsWith("rep")) //
                .map(pdfParser::raw) //
                .map(kontoauszugParser::kontoauszug).collect(Collectors.toList());
        }
        finally
        {
            System.out.println("PDFs parsed.");
        }
    }

    @SuppressWarnings("unused")
    private static void printReports(Collection<Kontoauszug> auszuege)
    {
        ReportBuilder reportBuilder = new ReportBuilder();
        ConsolePrinter<ReportLine> consolePrinter = new ConsolePrinter<>("Reportlines",
            ReportLine.columns());

        System.out.println(String.format("printing %s Kontoauszüge...", auszuege.size()));

        Collection<Report> reports = reportBuilder.sollReports(auszuege);

        reports.stream().sorted().forEach(r -> {
            System.out.println(r.auszug().monat());
            consolePrinter.print(r.lines());

            r.posten(Lastschrift.SONSTIGES).forEach(p -> System.out.println(p));
            System.out.println("");
        });

        System.out.println("Kontoauszüge printed.");
    }

    private static void printAuszuege(Collection<Kontoauszug> auszuege)
    {
        Collection<PrintableColumn<Posten>> columns = Arrays.asList(
            right("Betrag", e -> e.betrag() + " CHF"), //
            left("Kategorie",
                e -> e.kategorie().getClass().getSimpleName() + ":" + e.kategorie().name()));

        auszuege.stream() //
            .sorted() //
            .map(auszug -> new ConsolePrinter<Posten>("Posten für " + auszug.monat(), columns,
                auszug.posten())) //
            .forEach(ConsolePrinter::print);
    }
}
