package de.seppl.sebfinance;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

import static de.seppl.sebfinance.print.PrintableColumn.left;
import static de.seppl.sebfinance.print.PrintableColumn.right;

import de.seppl.sebfinance.kontoauszug.Gutschrift;
import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Lastschrift;
import de.seppl.sebfinance.kontoauszug.Posten;
import de.seppl.sebfinance.pdf.KontoauszugParser;
import de.seppl.sebfinance.pdf.PdfParser;
import de.seppl.sebfinance.print.ConsolePrinter;
import de.seppl.sebfinance.print.PrintableColumn;

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

    public Map<Kategorie, Integer> reduce(Collection<Kontoauszug> auszuege) {
        return auszuege.stream() //
                .filter(auszug -> auszug.monat().getYear() == 2015) //
                .map(Kontoauszug::posten) //
                .flatMap(Collection::stream) //
                .collect(toMap(Posten::kategorie, Posten::betrag, (a, b) -> a + b));
    }

    public void print(Map<Kategorie, Integer> kategorieMap) {
        List<Entry<Kategorie, Integer>> gutschriften =
                entries(kategorieMap, entry -> entry.getKey() instanceof Gutschrift);
        List<Entry<Kategorie, Integer>> lastschriften =
                entries(kategorieMap, entry -> entry.getKey() instanceof Lastschrift);

        Collection<PrintableColumn<Entry<Kategorie, Integer>>> columns =
                Arrays.asList(left("Kategorie", e -> e.getKey().name()), //
                        right("Betrag", e -> (e.getKey() instanceof Gutschrift ? "+" : "-") + e.getValue() + " CHF"));
        ConsolePrinter<Entry<Kategorie, Integer>> printer = new ConsolePrinter<>(columns);

        printer.print(gutschriften);
        int gutschrift = summe(gutschriften);
        System.out.println("Gutschriften: " + gutschrift);

        printer.print(lastschriften);
        int lastschrift = summe(lastschriften);
        System.out.println("Lastschriften: " + lastschrift);

        System.out.println("Gespart: " + (gutschrift - lastschrift));
    }

    private List<Entry<Kategorie, Integer>> entries(Map<Kategorie, Integer> kategorieMap,
            Predicate<Entry<Kategorie, Integer>> filter) {
        return kategorieMap.entrySet().stream() //
                .filter(filter) //
                .sorted((a, b) -> a.getKey().name().compareTo(b.getKey().name())) //
                .collect(toList());
    }

    private int summe(List<Entry<Kategorie, Integer>> entries) {
        return entries.stream() //
                .map(Entry::getValue) //
                .reduce(0, (a, b) -> a + b);
    }

    public void print(Collection<Kontoauszug> auszuege) {
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
