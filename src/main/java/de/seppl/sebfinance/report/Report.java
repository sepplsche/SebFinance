package de.seppl.sebfinance.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Posten;
import de.seppl.sebfinance.print.PrintableColumn;

public class Report implements Comparable<Report> {

	private final Kontoauszug auszug;
	private final int summe;
	private final Map<Kategorie, Integer> betragByKat;
	private final Map<Kategorie, Integer> prozentByKat;

	public Report(Kontoauszug auszug, int summe, Map<Kategorie, Integer> betragByKat,
			Map<Kategorie, Integer> prozentByKat) {
		this.auszug = auszug;
		this.summe = summe;
		this.betragByKat = betragByKat;
		this.prozentByKat = prozentByKat;
	}

	public Kontoauszug auszug() {
		return auszug;
	}

	public int summe() {
		return summe;
	}

	public Set<Kategorie> kategorien() {
		return prozentByKat.keySet();
	}

	public int betrag(Kategorie kategorie) {
		return nullSafe(betragByKat.get(kategorie));
	}

	public int prozent(Kategorie kategorie) {
		return nullSafe(prozentByKat.get(kategorie));
	}

	public List<Posten> posten(Kategorie kategorie) {
		return auszug.posten().stream() //
				.filter(p -> p.kategorie().equals(kategorie)) //
				.collect(Collectors.toList());
	}

	private int nullSafe(Integer v) {
		if (v == null)
			return 0;
		return v;
	}

	public Collection<ReportLine> lines() {
		Collection<ReportLine> lines = new ArrayList<>();
		kategorien().forEach(k -> {
			lines.add(new ReportLine(k, prozent(k), betrag(k)));
		});
		return lines;
	}

	@Override
	public int compareTo(Report o) {
		return auszug.monat().compareTo(o.auszug.monat());
	}

	public static class ReportLine implements Comparable<ReportLine> {
		private final Kategorie kat;
		private final int prozent;
		private final int betrag;

		ReportLine(Kategorie kat, int prozent, int betrag) {
			this.kat = kat;
			this.prozent = prozent;
			this.betrag = betrag;
		}

		public Kategorie kat() {
			return kat;
		}

		public int prozent() {
			return prozent;
		}

		public int betrag() {
			return betrag;
		}

		@Override
		public int compareTo(ReportLine o) {
			return kat.toString().compareTo(o.kat.toString());
		}

		public static Collection<PrintableColumn<ReportLine>> columns() {
			Collection<PrintableColumn<ReportLine>> columns = new ArrayList<>();
			columns.add(new PrintableColumn<>("Kategorie", e -> e.kat().toString(), true));
			columns.add(new PrintableColumn<>("Prozent", e -> String.valueOf(e.prozent()) + "%", false));
			columns.add(new PrintableColumn<>("Betrag", e -> String.valueOf(e.betrag()) + " CHF", false));
			return columns;
		}
	}
}
