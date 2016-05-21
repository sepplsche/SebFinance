package de.seppl.sebfinance.print;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.seppl.sebfinance.kontoauszug.Gutschrift;
import de.seppl.sebfinance.kontoauszug.Kategorie;
import de.seppl.sebfinance.kontoauszug.Kontoauszug;
import de.seppl.sebfinance.kontoauszug.Lastschrift;
import de.seppl.sebfinance.kontoauszug.Posten;

public class ReportBuilder {

	public Collection<Report> habenReports(Collection<Kontoauszug> auszuege) {
		return auszuege.stream() //
				.map(this::habenReport)//
				.collect(Collectors.toList());
	}

	public Collection<Report> sollReports(Collection<Kontoauszug> auszuege) {
		return auszuege.stream() //
				.map(this::sollReport)//
				.collect(Collectors.toList());
	}

	public Report gesamtHabenReport(Collection<Kontoauszug> auszuege) {
		List<Posten> posten = auszuege.stream() //
				.flatMap(a -> a.posten().stream()) //
				.collect(Collectors.toList());
		return habenReport(new Kontoauszug(LocalDate.now(), posten));
	}

	public Report gesamtSollReport(Collection<Kontoauszug> auszuege) {
		List<Posten> posten = auszuege.stream() //
				.flatMap(a -> a.posten().stream()) //
				.collect(Collectors.toList());
		return sollReport(new Kontoauszug(LocalDate.now(), posten));
	}

	public Report habenReport(Kontoauszug auszug) {
		int summe = summe(auszug, Posten::gutschrift);

		Map<Kategorie, Integer> summeByKat = summeByKat(auszug);
		Map<Kategorie, Integer> prozentByKat = prozentByKat(summeByKat, summe, Gutschrift.class);

		return new Report(auszug, summe, summeByKat, prozentByKat);
	}

	public Report sollReport(Kontoauszug auszug) {
		int summe = summe(auszug, Posten::lastschrift);

		Map<Kategorie, Integer> summeByKat = summeByKat(auszug);
		Map<Kategorie, Integer> prozentByKat = prozentByKat(summeByKat, summe, Lastschrift.class);

		return new Report(auszug, summe, summeByKat, prozentByKat);
	}

	private int summe(Kontoauszug auszug, Predicate<? super Posten> predicate) {
		return auszug.posten().stream() //
				.filter(predicate) //
				.map(Posten::betrag) //
				.reduce(0, (a, b) -> a + b);
	}

	private Map<Kategorie, Integer> summeByKat(Kontoauszug auszug) {
		return auszug.posten().stream() //
				.collect(Collectors.toMap(Posten::kategorie, Posten::betrag, (a, b) -> a + b));
	}

	private Map<Kategorie, Integer> prozentByKat(Map<Kategorie, Integer> summeByKat, int summe,
			Class<? extends Kategorie> clazz) {
		Map<Kategorie, Integer> prozentByKat = new HashMap<>();

		summeByKat.keySet().stream() //
				.filter(k -> k.getClass().isAssignableFrom(clazz)) //
				.forEach(k -> {
					int v = summeByKat.get(k);
					int prozent = summe == 0 ? -1 : 100 * v / summe;
					prozentByKat.put(k, prozent);
				});
		return prozentByKat;
	}
}
