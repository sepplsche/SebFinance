package de.seppl.sebfinance.kontoauszug;

public enum Lastschrift implements Kategorie {
	MEITE("NIEDERER AG IMMOBILIEN", //
			"MIETE", //
			"YELLOWNET 01-3302-3", //
			"WOHNUNG"), //
	//
	STROM("STROM"), //
	INTERNET("CABLECOM"), //
	NEBENKOSTEN, //
	AUTO("FALSCH GEPARKT", //
			"PARKEN", //
			"ZU SCHNELL", //
			"ZURICH CONNECT", //
			"FAHRAUSWEIS MARY", //
			"AMAG", //
			"01-800-7 GE MONEY BANK BRUGG AG"), //
	//
	BAHN("SBB"), //
	VERSICHERUNG, //
	KRANKHEIT("WINCARE", //
			"ASSURA", //
			"ARTZT"), //
	//
	STEUERN, //
	BAR("BARGELDBEZUG"), //
	BAR_AUSLAND("INTERNATIONAL", //
			"10 000.00 19.03.08"), //
	//
	SPAREN("AUTOMATISCHE ABDISPOSITION", //
			"ERSTEINLAGE 03.04.08"), //
	//
	TELEFON("YALLO"), //
	SONSTIGES;

	private final String[] verwendungen;

	private Lastschrift(String... verwendungen) {
		this.verwendungen = verwendungen;
	}

	@Override
	public String[] verwendungen() {
		return verwendungen;
	}
}
