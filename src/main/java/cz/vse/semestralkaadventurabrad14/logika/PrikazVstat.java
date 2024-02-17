package cz.vse.semestralkaadventurabrad14.logika;
    /**
     * Třída PrikazVstat implementuje pro hru příkaz "vstát".
     * Tato třída je součástí jednoduché textové hry.
     *
     * Herní postava vstane z postele.
     */
public class PrikazVstat implements IPrikaz {
    public static final String NAZEV = "vstát";
    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy
     *
     * @param hra odkaz na hru, u které se má změnit parametr vstal
     */
    public PrikazVstat(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "vstát" a změní parametr vstal na true
     * V případě, že příkaz má jen jedno slovo "vstát" postava vstane (volá se metoda setVstalZPostele(true))
     * jinak pokračuje např. při zadání "vstát a".
     * Pokud je příkaz "vstát" vyvolán mimo prostor pokojíček,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již splěnném úkolu.
     *
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length > 0) {
            return "Vstát kam? Nechápu, proč jste zadal druhé slovo.";
        } else if (hra.isVstat()){
            return "Už jsi vstal.";
        } else if (herniPlan.getAktualniProstor().getNazev().equals("pokojíček")) {
            hra.setVstalZPostele(true);
            return "Tvá postava vstala z postele.";
        } else {
            return "Zde nelze vstát.";
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
