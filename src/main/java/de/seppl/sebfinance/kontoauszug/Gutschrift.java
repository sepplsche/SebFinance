package de.seppl.sebfinance.kontoauszug;

public enum Gutschrift implements Kategorie {
	GEHALT("GIRO AUS KONTO", //
			"QUALIDOC"), //
	//
	GESCHENK, //
	EINZAHLUNG("EINZAHLUNG"), //
	RUCKZAHLUNG;

	private final String[] verwendungen;

	private Gutschrift(String... verwendungen) {
		this.verwendungen = verwendungen;
	}

	@Override
	public String[] verwendungen() {
		return verwendungen;
	}
}
