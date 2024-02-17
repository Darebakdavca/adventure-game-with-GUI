package cz.vse.semestralkaadventurabrad14.logika;

/**
 * Třída PrikazUcesatSe implementuje pro hru příkaz "učesatse".
 */
public class PrikazUcesatSe implements IPrikaz {
    public static final String NAZEV = "učesatse";

    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy
     *
     * @param herniPlan odkaz na herni plan pro určení správného prostoru pro učesání se
     * @param hra odkaz na hru, u které se má změnit parametr ucesalSe
     */
    public PrikazUcesatSe(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "učesatse" a mění parametr ucesalSe
     * V případě, že příkaz má jen jedno slovo "učesatse" postava se učeše.
     * Jinak pokračuje např. při zadání "učesatse a".
     * Pokud je příkaz "učesatse" vyvolán mimo prostor koupelna,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (hra.isUcesalSe()) {
            return "Postava se již učesala.";
        }
        if (parametry.length > 0) {
            return "Učet se kam? Nechápu, proč jste zadal druhé slovo.";
        }
        else if (herniPlan.getAktualniProstor().getNazev().equals("koupelna")) {
            hra.setUcesalSe(true);
            return "Tvá postava se učesala.";
        }
        return "Zde se nelze učesat.";
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
