package de.seppl.sebfinance.kontoauszug;

public enum Lastschrift implements Kategorie
{
    MIETE(//
        "NIEDERER AG IMMOBILIEN", //
        "MIETE", //
        "MEITE", //
        "YELLOWNET 01-3302-3", //
        "42/4.205.369.55", //
        "REST ALTE WOHNUNG", //
        "SCHLUSSABRECHNUNG WEBERSTR", //
        "MIETKAUTION"), //
    WOHNUNG(//
        "IKEA", //
        "TOPTIP", //
        "CONFORAMA", //
        "INTERIO", //
        "PFISTER", //
        "MOEBEL-MAERKI", //
        "KLINGELSCHILDER", //
        "NAMENSSCHILDER"), //
    ELEKTRONIK(//
        "FUST", //
        "TECHMANIA", //
        "TOMTOM", //
        "WIN7", //
        "PC-HAI", //
        "DIGITEC", //
        "GRAKA", //
        "CONRAD", //
        "CAMERA", //
        "INTERDISCOUNT", //
        "MEDIA MARKT"), //
    KONTOAUSGLEICH(//
        "YELLOWNET 30-579188-8 PETRISHKA MARIANA ILIEVA", //
        "E-FINANCE 30-579188-8 PETRISHKA MARIANA ILIEVA", //
        "ÜBERTRAG AUF KONTO 92-285988-7"), //
    STROM(//
        "BKW ENERGIE", //
        "STROM"), //
    TRADE(//
        "BANKENGIRO 5246.79.99", //
        "E-TRADING", //
        "AUFTRAG 1 TRANSAKTION(EN)"), //
    INTERNET(//
        "INTERNET TV", //
        "CABLECOM"), //
    SKI_VELO(//
        "ZWEISIMMEN", //
        "WENGEN", //
        "ADELBODEN", //
        "MÜRREN", //
        "SAANENMÖSER", //
        "SKI- UND VELO", //
        "CYCLETECH", //
        "GRINDELWALD"), //
    AUTO_ROLLER(//
        "L MARY", //
        "HONDA", //
        "PNEU FAHRNI", //
        "FALSCH GEPARKT", //
        "FAHRERPRÜFUNG", //
        "STEINSCHLAG", //
        "PARKEN", //
        "AUTOGRILL", //
        "ARTHUR FEGBLI", //
        "AUTO MAKEUP", //
        "AUTO SERVICE", //
        "STOSSSTANGE", //
        "ZU SCHNELL", //
        "WAB1", //
        "JAJA 28.10.08", //
        "STEUER AUTO", //
        "AUTO STEUER", //
        "AUTO MARTI", //
        "ZURICH CONNECT", //
        "VERSICHERUNGS-GESELLSCHAFT AG MYTHENQUAI 2 8002 ZÜRICH", //
        "GARAGE", //
        "PARKKARTE", //
        "FAHRZEUGPRÜFUNG", //
        "ZÜRITEL", //
        "DREHZAHLGEBER", //
        "FAHRSCHULE", //
        "ORDNUNGSBUSSEN BERN STRAFE", //
        "SCHULTHESS", //
        "FAHRAUSWEIS MARY", //
        "AMAG", //
        "47 478.47ÜBERTRAG AUS KONTO 92-532048-6", //
        "01-800-7 GE MONEY BANK BRUGG AG", //
        "TANKSTELLE", //
        "TAMOIL", //
        "BENZIN", //
        "FÜHRERSCHEIN", //
        "374.90 08.08.06", // doppelte autosteuer
        "AGIP"), //
    BAHN(//
        "BAHNHOF", //
        "STATION SVB BERN JURAHAUS", //
        "SBB"), //
    VERSICHERUNG(//
        "RECHTSCHUTZ"), //
    KRANKHEIT(//
        "AKU M", //
        "AKKU M", //
        "M. AKKU", //
        "RÖNTGEN", //
        "APOTHEKE", //
        "ARZT", //
        "VISANA", //
        "SANA24", //
        "BLUTTEST", //
        "WINCARE", //
        "ASSURA", //
        "ARTZT"), //
    STEUERN(//
        "STEUERVERWALTUNG"), //
    FLUG(//
        "BG-TICKET", //
        "UMBUCHUNGSGEBÜHR", //
        "FLUG BG", //
        "BG FLUG", //
        "NELI"), //
    RESTAURANT(//
        "PAPA JOE", //
        "ROSENGARTEN GASTRO", //
        "RESTAURANT"), //
    VERPFLEGUNG(//
        "ALDI", //
        "MAXI MARKET ZEKA", //
        "MAXI MARKT", //
        "MERKUR CONFISERIEN", //
        "SPAR SUPERMARKT", //
        "COOP", //
        "DENNER", //
        "LEBENSMITTEL", //
        "MIGROS"), //
    BAR(//
        "POSTSCHALTERGESCHÄFT", //
        "BARGELDBEZUG"), //
    BAR_AUSLAND(//
        "INTERNATIONAL"), //
    SPAREN(//
        "ÜBERTRAG AUF KONTO 92-532048-6", //
        "AUTOMATISCHE ABDISPOSITION", //
        "ERSTEINLAGE 03.04.08"), //
    TELEFON(//
        "ORANGE", //
        "SKYPE", //
        "BIBIT INTERNET PAYMENTS", //
        "ABN AMRO BANK", //
        "GLOBAL COLLECT BV", //
        "YALLO"), //
    SONSTIGES;

    private final String[] verwendungen;

    private Lastschrift(String... verwendungen)
    {
        this.verwendungen = verwendungen;
    }

    @Override
    public String[] verwendungen()
    {
        return verwendungen;
    }
}
