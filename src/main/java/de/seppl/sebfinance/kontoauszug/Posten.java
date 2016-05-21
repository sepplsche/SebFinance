package de.seppl.sebfinance.kontoauszug;

import java.time.LocalDate;

public class Posten {

	private final LocalDate valuta;
	private final int betrag;
	private final Kategorie kategorie;
	private final String verwendung;

	public Posten(LocalDate valuta, int betrag, Kategorie kategorie, String verwendung) {
		this.valuta = valuta;
		this.betrag = betrag;
		this.kategorie = kategorie;
		this.verwendung = verwendung;
	}

	public LocalDate valuta() {
		return valuta;
	}

	public int betrag() {
		return betrag;
	}

	public Kategorie kategorie() {
		return kategorie;
	}

	public String verwendung() {
		return verwendung;
	}

	public boolean lastschrift() {
		return kategorie instanceof Lastschrift;
	}

	public boolean gutschrift() {
		return kategorie instanceof Gutschrift;
	}

	@Override
	public String toString() {
		return "Posten[Valuta:" + valuta + ";Betrag:" + betrag + ";" + kategorie.getClass().getSimpleName() + ":"
				+ kategorie + ";Verwendung:" + verwendung + "]";
	}
}
