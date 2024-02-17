package cz.vse.semestralkaadventurabrad14.logika;
/**
 * Třída PříkazSeber implementuje pro hru příkaz seber.
 * Tato třída je součástí jednoduché textové hry.
 */
public class PrikazSeber implements IPrikaz {
    public static final String NAZEV = "seber";

    private HerniPlan herniPlan;

    private Hra hra;


    /**
     * Konstruktor třídy
     *
     * @param herniPlan herní plán, ve kterém se bude ve hře "sbírat" předměty
     */
    public PrikazSeber(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "seber". Zkouší sebrat předmět z aktuálního prostoru a vložit ho do inventáře hráče.
     * Pokud je předmět přenositelný a nachází se v aktuálním prostoru, hráč si ho sebere.
     *
     * @param parametry pole parametrů (jediným povoleným parametrem je název předmětu k sebrání)
     * @return výsledek provedení příkazu
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }
        if (parametry.length == 0) {
            // pokud hráč nezadá název předmětu k sebrání
            return "Co mám sebrat? Musíš k příkazu zadat i název předmětu.";
        }

        String nazevPredmetu = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Batoh batoh = herniPlan.getBatoh();
        Predmet sebranyPredmet = aktualniProstor.vyberPredmet(nazevPredmetu);


            // pokud se má sebrat batoh
            if (nazevPredmetu.equals("batoh")) {
                // pokud je postava v pokojíčku
                if (herniPlan.getAktualniProstor().getNazev().equals("pokojíček")) {
                        // pokud batoh ještě není sebraný
                    if (!hra.isSebranyBatoh()) {
                        hra.setSebranyBatoh(true);
                        return "Sebral jsi batoh, nyní můžeš sbírat ostatní předměty.";

                        // batoh je již sebraný
                    } else {
                        return "Batoh jsi již sebral.";
                    }
                    // pokud není postava v pokojíčku
                } else {
                        // pokud je batoh již sebraný
                    if (hra.isSebranyBatoh()) {
                        return "Batoh jsi již sebral";


                    }   // pokud není v této místnosti
                    return "Batoh není v této místnosti. Najdeš ho v pokojíčku.";
                }

            } else { // pokud se má sebrat jiný předmět než batoh
                // pokud batoh ještě není sebraný
                if (!hra.isSebranyBatoh()) {
                    return "Nejprve musíš sebrat batoh. Najdeš ho v pokojíčku.";

                    // pokud batoh již obsahuje předmět
                } else if (batoh.obsahujePredmet(nazevPredmetu)) {
                    return "Předmět " + nazevPredmetu + " jsi již sebral.";

                    // pokud předmět nebyl nalezen na první prozkoumání nebo není prozkoumaný
                } else if (sebranyPredmet == null) {
                    return "Nenašel jsem tento předmět. Zkus příkaz prozkoumej";

                    // pokud předmět není prozkoumaný
                } else if (!sebranyPredmet.isProzkoumany()) {
                    return "Nenašel jsem tento předmět. Zkus příkaz 'prozkoumej'";

                    // pokud předmět není přenositelný
                } else if (!sebranyPredmet.isPrenositelny()) {
                    return "Předmět " + nazevPredmetu + " není možné sebrat.";

                    // pokud se předmět dá přenést a je prozkoumaný
                } else if (sebranyPredmet.isProzkoumany()){

                    if (batoh.getSeznamPredmetu().size() < 2) {
                        herniPlan.getAktualniProstor().odeberPredmet(nazevPredmetu); // odebereme předmět z místnosti
                        batoh.vlozPredmet(sebranyPredmet); // přidáme předmět do batohu
                        return "Sebral jsi předmět " + nazevPredmetu + ".";
                    }
                    return "Batoh je plný. Musíš z batohu nějaký předmět položit.";
                    // pokud predmet není prozkoumany
                } else {
                    return "Nenašel jsem tento předmět. Zkus příkaz 'prozkoumej'";
                }
            }
        }


    /**
     * Vrací název příkazu (slovo, které hráč zadá pro vyvolání tohoto příkazu).
     *
     * @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
