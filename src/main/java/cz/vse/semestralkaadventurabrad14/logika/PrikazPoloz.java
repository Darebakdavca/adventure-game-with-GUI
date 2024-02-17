package cz.vse.semestralkaadventurabrad14.logika;

/**
 * Třída PrikazPoloz implementuje pro hru příkaz "polož".
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 */
public class PrikazPoloz implements IPrikaz {
    private static final String NAZEV = "polož";
    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy.
     *
     * @param herniPlan herní plán, ve kterém se budou ve hře "pokládat" předměty
     * @param hra       instance hry, která ovládá celkový průběh
     */
    public PrikazPoloz(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "polož". Zkouší se položit zadaný předmět, který musí být v batohu.
     * Pokud předmět neexistuje nebo není v batohu, vypíše se chybové hlášení.
     *
     * @param parametry - jako parametr obsahuje název předmětu k položení
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // Kontrola, zda hráč vstal z postele
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }

        if (parametry.length == 0) {
            // pokud hráč nezadá název předmětu k položení
            return "Co mám položit? Musíš k příkazu polož zadat i název předmětu.";
        }

        String nazevPredmetu = parametry[0];

        // Zjistíme, zda je předmět v batohu
        Batoh batoh = herniPlan.getBatoh();
        if (!batoh.obsahujePredmet(nazevPredmetu)) {
            return "Takový předmět v batohu nemáš!";
        }

        // Odebere předmět z batohu a umístí ho do aktuálního prostoru
        Predmet predmet = batoh.odeberPredmet(nazevPredmetu);
        if (predmet != null) {
            herniPlan.getAktualniProstor().vlozPredmet(predmet);
            return "Položil jsi předmět: " + predmet.getNazev();
        }
        return "Tento předmět v batohu není.";
    }

    /**
     * Metoda vrací název příkazu (slovo, které hráč používá pro jeho vyvolání).
     *
     * @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}

