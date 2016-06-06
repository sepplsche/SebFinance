package de.seppl.sebfinance;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import de.seppl.sebfinance.argument.ArgumentParser;
import de.seppl.sebfinance.argument.Arguments;
import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.pdf.ContentParser;
import de.seppl.sebfinance.pdf.ContentParserV1;
import de.seppl.sebfinance.pdf.ContentParserV2;
import de.seppl.sebfinance.pdf.KontoauszugParser;
import de.seppl.sebfinance.pdf.PdfParser;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format("init service with: '%s'...", StringUtils.join(args, " ")));
        Service service = init(args);

        Collection<File> pdfs = service.pdfs();

        System.out.println(String.format("parsing %s PDFs...", pdfs.size()));
        Collection<Kontoauszug> auszuege = service.parse(pdfs);

        System.out.println(String.format("filtering %s Kontoauszüge...", auszuege.size()));
        auszuege = service.filter(auszuege);

        Map<Kategorie, Integer> kategorieMap = service.reduce(auszuege);

        System.out.println(String.format("printing %s Entries...", kategorieMap.size()));
        service.print(kategorieMap);

        // System.out.println(String.format("printing %s Kontoauszüge...", auszuege.size()));
        // service.print(auszuege);
    }

    private static Service init(String[] args) {
        Arguments arguments = new Arguments();
        ArgumentParser argsParser = new ArgumentParser(args);

        Collection<File> pdfs = argsParser.parse(arguments.files());
        LocalDate date = argsParser.parse(arguments.date());
        Mode mode = argsParser.parse(arguments.mode());

        PdfParser pdfParser = new PdfParser();
        Collection<ContentParser> parsers = Arrays.asList(//
                new ContentParserV1(), //
                new ContentParserV2());
        KontoauszugParser kontoauszugParser = new KontoauszugParser(parsers);

        return new Service(mode, date, pdfs, pdfParser, kontoauszugParser);
    }
}
