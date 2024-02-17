package cz.vse.semestralkaadventurabrad14.logika;
    /**
     * Třída PrikazVykonatPotrebu implementuje pro hru příkaz "obléknout".
     * Tato třída je součástí jednoduché textové hry.
     *
     * Herní postava se oblékne
     */
public class PrikazOblectSe implements IPrikaz {
    public static final String NAZEV = "obléknoutse";

    private Hra hra;
    private HerniPlan herniPlan;

    /**
     * Konstruktor třídy
     *
     * @param hra odkaz na hru, u které se má změnit parametr obleknulSe
     * @param herniPlan odkaz na herni plan pro určení správného prostoru pro obléknutí
     */
    public PrikazOblectSe(HerniPlan herniPlan, Hra hra) {
        this.hra = hra;
        this.herniPlan = herniPlan;
    }

    /**
     * Provádí příkaz "obléknoutse" a mění parametr obleknulSe
     * V případě, že příkaz má jen jedno slovo "obléknoutse" postava se oblékne (volá se metoda setObleknulSe(true))
     * Jinak pokračuje např. při zadání "obléknoutse a".
     * Pokud je příkaz "obléknoutse" vyvolán mimo prostor pokojicek,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (!hra.isVstat()) {
            return "Nejprve musíš vstát z postele.";
        }
        if (hra.isObleknulSe()) {
            return "Tvá postava je již oblečená.";
        }
        if (parametry.length > 0) {
            return "Obléct se kam? Nechápu, proč jste zadal druhé slovo.";
        }
        else if (herniPlan.getAktualniProstor().getNazev().equals("pokojíček")) {
            hra.setObleknulSe(true);
            return "Tvá postava se oblékla.";
        }
        return "Zde se nelze obléknout.";
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
