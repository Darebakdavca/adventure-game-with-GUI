package cz.vse.semestralkaadventurabrad14.logika;
    /**
     * Třída PrikazVykonatPotrebu implementuje pro hru příkaz "vykonatpotřebu".
     * Tato třída je součástí jednoduché textové hry.
     *
     * Herní postava sedne na záchod a vyprázdní se
     */
public class PrikazVykonatPotrebu implements IPrikaz {
    public static final String NAZEV = "vykonatpotřebu";

    private Hra hra;
    private HerniPlan herniPlan;

    /**
     * Konstruktor třídy
     *
     * @param hra odkaz na hru, u které se má změnit parametr vykonalPotrebu
     * @param herniPlan odkaz na herni plan pro určení správného prostoru na vykonání potřeby
     */
    public PrikazVykonatPotrebu(HerniPlan herniPlan, Hra hra) {
        this.hra = hra;
        this.herniPlan = herniPlan;
    }

    /**
     * Provádí příkaz "vykonatpotřebu" a mění parametr vykonalPotrebu
     * V případě, že příkaz má jen jedno slovo "vykonatpotřebu" postava vykoná potřebu (volá se metoda setVykonalPotrebu(true))
     * jinak pokračuje např. při zadání "vykonatpotřebu a".
     * Pokud je příkaz "vykonatpotřebu" vyvolán mimo prostor záchod,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (hra.isVykonalPotrebu()) {
            return "Už jsi vykonal potřebu";
        }
        if (parametry.length > 0) {
            return "Vykonat potřebu kam? Nechápu, proč jste zadal druhé slovo.";
        }
        else if (herniPlan.getAktualniProstor().getNazev().equals("záchod")) {
            hra.setVykonalPotrebu(true);
            return "Tvá postava vykonala potřebu.";
        }
        return "Zde nelze vykonat potřebu.";
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
