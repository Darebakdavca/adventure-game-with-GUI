package cz.vse.semestralkaadventurabrad14.logika;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *
 */
public class PrikazJdi implements IPrikaz {
    public static final String NAZEV = "jdi";
    private HerniPlan herniPlan;
    private Hra hra;

    /**
    *  Konstruktor třídy
    *  
    *  @param herniPlan herní plán, ve kterém se bude ve hře "chodit"
    */    
    public PrikazJdi(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud hráč
     * nevstal z postele, vrátí chybovou zprávu. Pokud chybí druhé slovo
     * (sousední prostor), vrátí informaci, že musí být zadáno jméno východu.
     * Zkouší přejít do sousedního prostoru. Pokud sousední prostor neexistuje,
     * vrátí chybovou zprávu, jinak hráče přesune do nového prostoru a vrátí jeho
     * dlouhý popis.
     *
     * @param parametry - jako parametr obsahuje jméno prostoru (východu),
     *                   do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // Kontrola, zda hráč vstal z postele
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }

        // pokud chybí druhé slovo (sousední prostor), tak ....
        if (parametry.length == 0) {
            return "Kam mám jít? Musíš k příkazu zadat i jméno východu.\n" + herniPlan.getAktualniProstor().popisVychodu();
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = herniPlan.getAktualniProstor().vratSousedniProstor(smer);
        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }
        else {
            herniPlan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
