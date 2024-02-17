package cz.vse.semestralkaadventurabrad14.logika;

/**
 * Třída PrikazProzkoumej implementuje pro hru příkaz prozkoumej.
 * Tato třída je součástí jednoduché textové hry.
 *
 */
public class PrikazProzkoumej implements IPrikaz {
    private static final String NAZEV = "prozkoumej";
    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy.
     *
     * @param herniPlan herní plán, ve kterém se bude ve hře "prozkoumávat"
     * @param hra instance aktuální hry
     */
    public PrikazProzkoumej(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "prozkoumej". Zkontroluje, zda hráč vstal z postele.
     * Pokud hráč nezadá název předmětu k prozkoumání, vrátí popis aktuálního prostoru.
     * Pokud hráč zadá název předmětu, pokusí se jej vybrat z aktuálního prostoru a vrátí jeho popis.
     * Pokud předmět neexistuje v aktuálním prostoru, vrátí chybovou zprávu.
     *
     * @param parametry - jako parametr obsahuje název předmětu k prozkoumání
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // Kontrola, zda hráč vstal z postele
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }

        Prostor aktualniProstor = herniPlan.getAktualniProstor();

        if (herniPlan.getAktualniProstor().getNazev().equals("pokojíček") && !hra.isSebranyBatoh()) {
            return "Prohlédl jsi si pokojíček, leží zde batoh.";
        }
        // pokud hráč nezadá název předmětu k prozkoumání
        if (parametry.length == 0) {
            return aktualniProstor.popisProzkoumej();
        }

        String nazevPredmetu = parametry[0];
        Predmet vybranyPredmet = aktualniProstor.vyberPredmet(nazevPredmetu);

        // pokud predmet existuje, prozkoumá se
        if (vybranyPredmet != null) {
            return vybranyPredmet.popisProzkoumej();
        }

        // predmet nenalezen
        else {
            return "Nenašel jsem na první pohled předmět " + nazevPredmetu + ".";
        }
    }

    /**
     * Metoda vrací název příkazu (slovo, které používá hráč pro jeho vyvolání).
     *
     * @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
