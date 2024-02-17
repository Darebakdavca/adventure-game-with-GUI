package cz.vse.semestralkaadventurabrad14.logika;
    /**
     * Třída PrikazVykonatPotrebu implementuje pro hru příkaz "oboutse".
     * Tato třída je součástí jednoduché textové hry.
     *
     * Herní postava si obuje boty
     */
public class PrikazOboutSe implements IPrikaz {
    public static final String NAZEV = "oboutse";

    private Hra hra;
    private HerniPlan herniPlan;

    /**
     * Konstruktor třídy
     *
     * @param hra odkaz na hru, u které se má změnit parametr obulSe
     * @param herniPlan odkaz na herni plan pro určení správného prostoru na obutí bot
     */
    public PrikazOboutSe(HerniPlan herniPlan, Hra hra) {
        this.hra = hra;
        this.herniPlan = herniPlan;
    }

    /**
     * Provádí příkaz "oboutse" a mění parametr obulSe
     * V případě, že příkaz má jen jedno slovo "oboutse" postava si obuje boty (volá se metoda setObulSe(true))
     * Jinak pokračuje např. při zadání "oboutse a".
     * Pokud je příkaz "oboutse" vyvolán mimo prostor předsíň,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (hra.isObulSe()) {
            return "Tvá postava je již obutá.";
        }
        if (parametry.length > 0) {
            return "Obout se kam? Nechápu, proč jste zadal druhé slovo.";
        }
        else if (herniPlan.getAktualniProstor().getNazev().equals("předsíň")){
            hra.setObulSe(true);
            return "Tvá postava se obula.";
        }
        return "Zde se nelze obout.";
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
