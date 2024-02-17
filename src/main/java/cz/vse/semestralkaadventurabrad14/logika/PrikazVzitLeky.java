package cz.vse.semestralkaadventurabrad14.logika;

/**
 * Třída PrikazVzitLeky implementuje pro hru příkaz "vzítléky".
 * Tato třída je součástí jednoduché textové hry.
 *
 * Herní postava si vezme léky
 */
public class PrikazVzitLeky implements IPrikaz {
    public static final String NAZEV = "vzítléky";

    private HerniPlan herniPlan;

    private Hra hra;

    /**
     * Konstruktor třídy
     *
     * @param hra odkaz na hru, u které se má změnit parametr vzalSiLeky
     * @param herniPlan odkaz na herni plan pro určení správného prostoru pro vezmutí si léků
     */
    public PrikazVzitLeky(HerniPlan herniPlan, Hra hra) {
        this.hra = hra;
        this.herniPlan = herniPlan;
    }

    /**
     * Provádí příkaz "vzítléky" a mění parametr vzalSiLeky
     * V případě, že příkaz má jen jedno slovo "vzítléky" vypíšou se možné léky na výběr.
     * Pokud zvolí projímadla, hra ihned končí a prohrál.
     * Jinak pokračuje např. při zadání "vzítléky jmenoleku".
     * Pokud si vezme sinupret, metoda skončí a hráč ji může znovu vyvolat a zvolit správný lék.
     * Pokud jsou správně zvoleny vitamíny, postava si vezme léky (volá se metoda setVzalSiLeky(true))
     * Pokud je příkaz "vzítléky" vyvolán mimo prostor kuchyň,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // hráč si už vzal léky
        if (hra.isVzalSiLeky()) {
            return "Tvá postava si už vzala léky.";
        }
        // Kontrola, zda hráč vstal z postele
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }
        // hráč se nachází v kuchyni
        if (herniPlan.getAktualniProstor().getNazev().equals("kuchyň")) {
            // hráč nespecifikoval lék
            if (parametry.length == 0) {
                return "Chceš si vzít vitamíny.\n" +
                        "Léky na výběr:\n" +
                        "- Sinupret\n" + "- BKomplex\n" + "- Laxygal";
            }

            String lek = parametry[0];
            // zvolený lék bkomplex
            if (lek.equalsIgnoreCase("bkomplex")) {
                hra.setVzalSiLeky(true);
                return "Úspěch! Tvá postava si vzala vitamíny.";
            }
            // zvolený lék laxygal
            else if (lek.equalsIgnoreCase("laxygal")) {
                hra.setKonecHry(true);
                return "Vzal sis projímadlo, nemůžeš jít do školy, protože by ses tam podělal.\n\n !!Prohrál jsi hru!!";
            }
            // jiný zvolený lék
            else {
                return "Nezvolil jsi správný lék. Vem si vitamíny.";
            }
        }
        // hráč není v kuchyni
        else {
            return "Zde si nelze vzít léky.";
        }

    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
