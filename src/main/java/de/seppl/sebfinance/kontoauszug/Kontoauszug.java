package de.seppl.sebfinance.kontoauszug;

import java.time.LocalDate;
import java.util.Collection;

public class Kontoauszug {

	private final LocalDate monat;
	private final Collection<Posten> posten;

	public Kontoauszug(LocalDate monat, Collection<Posten> posten) {
		this.monat = monat;
		this.posten = posten;
	}

	public LocalDate monat() {
		return monat;
	}

	public Collection<Posten> posten() {
		return posten;
	}
}
