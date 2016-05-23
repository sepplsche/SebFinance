package de.seppl.sebfinance.kontoauszug;

public enum Gutschrift implements Kategorie
{
    GEHALT(//
        "BEDAG INFORMATIK AG", //
        "QUALIDOC"), //
    ZINS(//
        "ZINSABSCHLUSS"), //
    KAUTION(//
        "GIRO AUS KONTO 30-106-9"), //
    GESCHENK, //
    EINZAHLUNG(//
        "GIRO AUS ONLINE-SIC 790 AUFTRAGGEBER: MAY MARCUS MATTHAEUS", //
        "GIRO AUS KONTO 30-579188-8", //
        "EINZAHLUNG"), //
    ENTSPAREN(//
        "ÜBERTRAG AUS KONTO 92-532048-6", //
        "ÜBERTRAG AUS KONTO 30-579188-8"), //
    RUCKZAHLUNG(//
        "GIRO AUS KONTO 30-38100-3", //
        "CASHBACK", //
        "GUTSCHRIFT", //
        "RUECKZAHLUNG", //
        "RÜCKBUCHUNG ÜBERWEISUNG");

    private final String[] verwendungen;

    private Gutschrift(String... verwendungen)
    {
        this.verwendungen = verwendungen;
    }

    @Override
    public String[] verwendungen()
    {
        return verwendungen;
    }
}
