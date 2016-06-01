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
            "LAPTOP UND W-LAN-ROUTER", //
            "PC HIGH", //
            "CONRAD", //
            "CAMERA", //
            "SERIELLES KABEL", //
            "INTERDISCOUNT", //
            "MEDIAMARKT", //
            "MEDIA MARKT"), //
    KONTOAUSGLEICH(//
            "YELLOWNET 30-579188-8", //
            "E-FINANCE 30-579188-8", //
            "E-FINANCE 30-474825-1", //
            "ÜBERTRAG AUF KONTO 30-474825-1"), //
    STROM(//
            "BKW ENERGIE", //
            "STROM"), //
    TRADE(//
            "BANKENGIRO 5246.79.99", //
            "E-TRADING", //
            "FONDSANTEILEN", //
            "AUFTRAG 1 TRANSAKTION(EN)"), //
    INTERNET_TV(//
            "BILLAG", //
            "INTERNET TV", //
            "INTERNETTV", //
            "CABLECOM"), //
    SKI_VELO(//
            "ZWEISIMMEN", //
            "WENGEN", //
            "ADELBODEN", //
            "MÜRREN", //
            "SKITESTWE", //
            "SKI-UND VELO-CENTER", //
            "MOTO-CENTER BERN", //
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
            "VERS. ROLLER", //
            "VERS. AUTO", //
            "VERKEHRSSTARFE", //
            "AUTOGRILL", //
            "ARTHUR FEGBLI", //
            "AUTO MAKEUP", //
            "ADAM TOURING", //
            "AUTO SERVICE", //
            "TREIBSTOFF", //
            "STOSSSTANGE", //
            "BERN AUTO", //
            "ZU SCHNELL", //
            "WAB1", //
            "JAJA 28.10.08", //
            "ORDNUNGSBUSSEN", //
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
            "BÄRTSCHI KURT", //
            "ORDNUNGSBUSSEN BERN STRAFE", //
            "AUTO SPIEGEL", //
            "AUTO-ZENTRUM", //
            "L-AUSWEIS", //
            "ROLLERGRUNDKURS", //
            "ROLLERVERS", //
            "WAB2", //
            "OSTERMUNDIGEN GEBÜHR BETR.AUSZ", //
            "AUTO ÖL", //
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
            "M. ENDO", //
            "CRYOSAFE", //
            "ARZT", //
            "VISANA", //
            "DR. MED.", //
            "SWICA", //
            "DR.WIRZ", //
            "KLINIK", //
            "OPTIK BERN", //
            "SONNENHOF", //
            "AKKUPUNKTUR", //
            "LABOR", //
            "CITY NOTFALL", //
            "INSELSPITAL", //
            "GASTROSKOPIE", //
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
    FERIEN(//
            "BG-TICKET", //
            "UMBUCHUNGSGEBÜHR", //
            "BULGARIA AIR", //
            "MONZA", //
            "FLUG", //
            "BEATUSHÖHLEN", //
            "ISE TICKET", //
            "TENERIFFA", //
            "MIETAUTO", //
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
            "KKIOSK", //
            "MIGROL BE EGGHÖLZLI BERN WAREN", //
            "COOP TS GÜMLIGEN GÜMLIGEN WAREN", //
            "COOP MINERALOEL AG MÜNSINGEN WAREN", //
            "BP SERVICE HOLLIGEN BERN WAREN", //
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
            "ERSTEINLAGE", //
            "ÜBERTRAG AUF KONTO 92-285988-7", //
            "E-FINANCE 92-532048-6", //
            "FÜR ISIS WOHNUNG"), //
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
