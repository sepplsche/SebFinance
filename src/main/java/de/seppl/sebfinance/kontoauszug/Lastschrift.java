package de.seppl.sebfinance.kontoauszug;

public enum Lastschrift implements Kategorie {
    MIETE(//
            "NIEDERER AG IMMOBILIEN", //
            "MIETE", //
            "MEITE", //
            "YELLOWNET 01-3302-3", //
            "42/4.205.369.55", //
            "REST ALTE WOHNUNG", //
            "SCHLUSSABRECHNUNG WEBERSTR", //
            "NEBENKOSTEN", //
            "NEBEKOSTEN", //
            "MIETKAUTION"), //
    WOHNUNG(//
            "IKEA", //
            "TOPTIP", //
            "CONFORAMA", //
            "INTERIO", //
            "PFISTER", //
            "JUMBO-MARKT", //
            "MOEBEL-MAERKI", //
            "GOBAG GUMMI OBERLEITNER", //
            "BABY-ROSE", //
            "BABYPHONE", //
            "KLINGELSCHILDER", //
            "NAMENSSCHILDER"), //
    ELEKTRONIK(//
            "FUST", //
            "TECHMANIA", //
            "TOMTOM", //
            "WIN7", //
            "PC-HAI", //
            "GALAXUS", //
            "DIGITEC", //
            "GRAKA", //
            "PC HIGH", //
            "CONRAD", //
            "CAMERA", //
            "SERIELLES KABEL", //
            "INTERDISCOUNT", //
            "MEDIAMARKT", //
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
    INTERNET_TV(//
            "BILLAG", //
            "INTERNET TV", //
            "CABLECOM"), //
    SKI_VELO(//
            "ZWEISIMMEN", //
            "WENGEN", //
            "ADELBODEN", //
            "MÜRREN", //
            "SKI-UND VELO-CENTER", //
            "SAANENMÖSER", //
            "SCHALLER VELOS", //
            "LENK", //
            "SKI- UND VELO", //
            "CYCLETECH", //
            "SÜDSTRASSE GMBH MÜNSINGEN", //
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
            "ADAM TOURING", //
            "AUTO SERVICE", //
            "TREIBSTOFF", //
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
            "AUTO-ZENTRUM", //
            "OSTERMUNDIGEN GEBÜHR BETR.AUSZ", //
            "SCHULTHESS", //
            "FAHRAUSWEIS MARY", //
            "AMAG", //
            "47 478.47ÜBERTRAG AUS KONTO 92-532048-6", //
            "01-800-7 GE MONEY BANK BRUGG AG", //
            "TANKSTELLE", //
            "TAMOIL", //
            "BENZIN", //
            "FÜHRERSCHEIN", //
            "STRASSENVERKEHRSAMT", //
            "AUTO REPARATUR", //
            "374.90 08.08.06", // doppelte autosteuer
            "AGIP"), //
    BAHN(//
            "BAHNHOF", //
            "STATION SVB BERN JURAHAUS", //
            "BERNMOBIL", //
            "SBB"), //
    VERSICHERUNG(//
            "RECHTSCHUTZ", //
            "BASEL HAUSRAT", //
            "BASEL VERS. HAUSHALT"), //
    KRANKHEIT(//
            "AKU M", //
            "AKKU M", //
            "M. AKKU", //
            "RÖNTGEN", //
            "APOTHEKE", //
            "SPITEX", //
            "KRÜCKEN", //
            "CRYOSAFE", //
            "ARZT", //
            "VISANA", //
            "SWICA", //
            "KLINIK", //
            "INSELSPITAL", //
            "AMBULANZ", //
            "GEBURT", //
            "SANA24", //
            "BLUTTEST", //
            "LOCALMED", //
            "WINCARE", //
            "ASSURA", //
            "ARTZT"), //
    STEUERN(//
            "STEUERVERWALTUNG"), //
    FLUG(//
            "BG-TICKET", //
            "UMBUCHUNGSGEBÜHR", //
            "FLUG", //
            "ISE TICKET", //
            "TENERIFFA", //
            "NELI"), //
    RESTAURANT(//
            "PAPA JOE", //
            "BURGER KING", //
            "DESPERADO", //
            "MR. PICKWICK", //
            "ROSENGARTEN GASTRO", //
            "HOTEL NATIONAL BERN", //
            "RESTAURANT"), //
    VERPFLEGUNG(//
            "ALDI", //
            "MAXI MARKET ZEKA", //
            "MAXI MARKT", //
            "MERKUR CONFISERIEN", //
            "SPAR SUPERMARKT", //
            "COOP TS GÜMLIGEN GÜMLIGEN WAREN", //
            "COOP-3315", //
            "COOP-5026", //
            "COOP-1390", //
            "COOP-1083", //
            "COOP-2502", //
            "COOP-1572", //
            "COOP-1396", //
            "DRINKS OF THE WORLD", //
            "COOP-1198", //
            "COOP-1497", //
            "COOP-2464", //
            "COOP-1207", //
            "COOP-1194", //
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

    private Lastschrift(String... verwendungen) {
        this.verwendungen = verwendungen;
    }

    @Override
    public String[] verwendungen() {
        return verwendungen;
    }
}
