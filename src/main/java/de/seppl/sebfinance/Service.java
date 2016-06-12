package de.seppl.sebfinance;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

import static de.seppl.sebfinance.print.PrintableColumn.left;
import static de.seppl.sebfinance.print.PrintableColumn.right;

import de.seppl.sebfinance.kontoauszug.Gutschrift;
import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;
import de.seppl.sebfinance.pdf.KontoauszugParser;
import de.seppl.sebfinance.pdf.PdfParser;
import de.seppl.sebfinance.print.ConsolePrinter;
import de.seppl.sebfinance.print.PrintableColumn;
import de.seppl.sebfinance.report.Report;

/**
 * @author Seppl
 */
public class Service {

    private final Mode mode;
    private final LocalDate date;
    private final PdfParser pdfParser;
    private final KontoauszugParser kontoauszugParser;
    private final Collection<File> pdfs;

    public Service(Mode mode, LocalDate date, Collection<File> pdfs, PdfParser pdfParser,
            KontoauszugParser kontoauszugParser) {
        this.mode = checkNotNull(mode);
        this.date = checkNotNull(date);
        this.pdfs = ImmutableList.copyOf(pdfs);
        this.pdfParser = checkNotNull(pdfParser);
        this.kontoauszugParser = checkNotNull(kontoauszugParser);
    }

    public Collection<File> pdfs() {
        return pdfs;
    }

    public Collection<Kontoauszug> parse(Collection<File> pdfs) {
        return pdfs.stream() //
                .map(pdfParser::raw) //
                .map(kontoauszugParser::kontoauszug) //
                .collect(toList());
    }

    public Collection<Kontoauszug> filter(Collection<Kontoauszug> auszuege) {
        return auszuege.stream() //
                .filter(auszug -> auszug.monat().isAfter(date)) //
                .filter(auszug -> filter(mode, auszug.posten())) //
                .collect(toList());
    }

    private boolean filter(Mode mode, Collection<Posten> postens) {
        if (mode == Mode.NORMAL)
            return true;
        return postens.stream().anyMatch(Posten::sonstiges);
    }

    public Collection<Report> reduce(Collection<Kontoauszug> auszuege) {
        Map<Integer, Map<Kategorie, Integer>> years = auszuege.stream() //
                .map(Kontoauszug::monat) //
                .map(LocalDate::getYear) //
                .collect(toMap(year -> year, year -> year(auszuege, year)));

        Collection<Report> reports = new ArrayList<>();
        return reports;
    }

    private Map<Kategorie, Integer> year(Collection<Kontoauszug> auszuege, int year) {
        return auszuege.stream() //
                .filter(auszug -> auszug.monat().getYear() == year) //
                .map(Kontoauszug::posten) //
                .flatMap(Collection::stream) //
                .collect(toMap(Posten::kategorie, Posten::betrag, (a, b) -> a + b));
    }

    public void printReports(Collection<Report> reports, IntStream years) {
        Collection<PrintableColumn<Report>> columns = new ArrayList<>();
        columns.add(left("Kategorie", Report::kategorie));
        years.forEach(year -> {
            columns.add(right("" + year, r -> betrag(r, year)));
        });
        ConsolePrinter<Report> printer = new ConsolePrinter<>(columns);
        printer.print(reports);
    }

    private String betrag(Report report, int year) {
        return (report.kategorie() instanceof Gutschrift ? "+" : "-") + report.betrag(year) + " CHF";
    }

    public void printAuszuege(Collection<Kontoauszug> auszuege) {
        Collection<PrintableColumn<Posten>> columns =
                Arrays.asList(right("Betrag", e -> (e.gutschrift() ? "+" : "-") + e.betrag() + " CHF"), //
                        left("Kategorie", e -> e.kategorie().name()), //
                        left("Verwendung", e -> e.sonstiges() ? e.verwendung() : ""));

        ConsolePrinter<Posten> printer = new ConsolePrinter<>(columns);

        auszuege.stream() //
                .sorted() //
                .forEach(auszug -> {
                    System.out.println(String.format("%s Posten für Konto %s am %s:", //
                            auszug.posten().size(), auszug.konto(), auszug.monat()));

                    Collection<Posten> posten = auszug.posten().stream() //
                            .sorted((a, b) -> compare(a, b)) //
                            .collect(toList());

                    printer.print(posten);
                });
    }

    private int compare(Posten a, Posten b) {
        int compKatClass = a.kategorie().getClass().getSimpleName().compareTo(b.kategorie().getClass().getSimpleName());
        if (compKatClass != 0)
            return compKatClass;
        int compKat = a.kategorie().name().compareTo(b.kategorie().name());
        if (compKat != 0)
            return compKat;
        return a.betrag() - b.betrag();
    }
}
