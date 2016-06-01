package de.seppl.sebfinance.kontoauszug;

public enum Gutschrift implements Kategorie {
    GEHALT(//
            "BEDAG INFORMATIK AG", //
            "QUALIDOC", //
            "MEIERHOFER", //
            "COMPUGROUP"), //
    ZINS(//
            "ZINSABSCHLUSS"), //
    KAUTION(//
            "GIRO AUS KONTO 30-106-9"), //
    GESCHENK, //
    EINZAHLUNG(//
            "GIRO AUS ONLINE-SIC 790 AUFTRAGGEBER: MAY MARCUS MATTHAEUS", //
            "GIRO AUS ONLINE-SIC 769 AUFTRAGGEBER: MARTIN CH. HAEFELFINGER", //
            "GIRO AUS KONTO 30-579188-8", //
            "GIRO AUS KONTO 30-474825-1", //
            "EINZAHLUNG"), //
    ENTSPAREN(//
            "ÜBERTRAG AUS KONTO 92-532048-6", //
            "GIRO AUSLAND USD", //
            "GIRO AUSLAND REFERENZ-NR. 150504CH00IKV9MZ", //
            "ÜBERTRAG AUS KONTO 30-579188-8", //
            "ÜBERTRAG AUS KONTO 92-285988-7", //
            "EINMALZEICHNUNG", //
            "NETTOAUSSCHÜTTUNG"), //
    RUCKZAHLUNG(//
            "GIRO AUS ONLINE-SIC 6300 AUFTRAGGEBER: VORSORGESTIFTUNG DER MERZ", //
            "GIRO AUS KONTO 30-38100-3", //
            "GIRO AUS KONTO 60-135813-5", //
            "GIRO AUS KONTO 10-685-6", //
            "CASHBACK", //
            "GUTSCHRIFT", //
            "RUECKZAHLUNG", //
            "RÜCKBUCHUNG ÜBERWEISUNG");

    private final String[] verwendungen;

    private Gutschrift(String... verwendungen) {
        this.verwendungen = verwendungen;
    }

    @Override
    public String[] verwendungen() {
        return verwendungen;
    }
}
