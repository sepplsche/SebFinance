package de.seppl.sebfinance;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.seppl.sebfinance.argument.ArgumentParser;
import de.seppl.sebfinance.argument.Arguments;
import de.seppl.sebfinance.argument.Arguments.EmptyFile;
import de.seppl.sebfinance.content.ContentParser;
import de.seppl.sebfinance.content.ContentParserV1;
import de.seppl.sebfinance.content.ContentParserV2;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Lastschrift;
import de.seppl.sebfinance.pdf.PdfParser;
import de.seppl.sebfinance.print.ConsolePrinter;
import de.seppl.sebfinance.report.Report;
import de.seppl.sebfinance.report.Report.ReportLine;
import de.seppl.sebfinance.report.ReportBuilder;

public class Main {

	public static void main(String[] args) {

		Arguments arguments = new Arguments();
		ArgumentParser argsParser = new ArgumentParser(args);
		PdfParser pdfParser = new PdfParser();
		ReportBuilder reportBuilder = new ReportBuilder();
		ConsolePrinter<ReportLine> consolePrinter = new ConsolePrinter<>("Reportlines", ReportLine.columns());

		Collection<ContentParser> contentParsers = Arrays.asList(//
				new ContentParserV1(), //
				new ContentParserV2());

		Collection<File> pdfs = files(arguments, argsParser);

		List<Kontoauszug> auszuege = new ArrayList<>();
		pdfs.forEach(pdf -> {
			Optional<Kontoauszug> kontoauszug = parse(pdfParser, contentParsers, pdf);
			kontoauszug.ifPresent(auszug -> auszuege.add(auszug));
		});

		Collection<Report> reports = reportBuilder.sollReports(auszuege);

		reports.stream().sorted().forEach(r -> {
			System.out.println(r.auszug().monat());
			consolePrinter.print(r.lines());

			r.posten(Lastschrift.SONSTIGES).forEach(p -> System.out.println(p));
			System.out.println("");
		});
	}

	private static Collection<File> files(Arguments arguments, ArgumentParser argsParser) {
		File pdf = argsParser.parse(arguments.pdf());

		if (!(pdf instanceof EmptyFile))
			return Arrays.asList(pdf);

		File dir = argsParser.parse(arguments.dir());

		if (dir instanceof EmptyFile)
			throw new IllegalArgumentException("No files found!");

		if (!dir.isDirectory())
			throw new IllegalArgumentException("No direcory:" + dir);

		return Arrays.asList(dir.listFiles());
	}

	private static Optional<Kontoauszug> parse(PdfParser pdfParser, Collection<ContentParser> contentParsers,
			File pdf) {
		Collection<String> content = pdfParser.parse(pdf);
		if (content.isEmpty()) {
			System.err.println("Empty content: " + pdf);
			return Optional.empty();
		}

		for (ContentParser contentParser : contentParsers) {
			try {
				return Optional.of(contentParser.kontoauszug(content));
			} catch (IllegalStateException e) {
				if (!e.getMessage().equals("Keine Posten"))
					System.err.println(e.getMessage());
			}
		}
		System.err.println("No fitting ContentParser found!");
		return Optional.empty();
	}
}
