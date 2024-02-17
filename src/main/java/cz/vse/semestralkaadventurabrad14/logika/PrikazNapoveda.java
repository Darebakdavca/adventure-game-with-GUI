package cz.vse.semestralkaadventurabrad14.logika;

/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 *  Tato třída je součástí jednoduché textové hry.
 *
 */
class PrikazNapoveda implements IPrikaz {
    
    private static final String NAZEV = "nápověda";
    private SeznamPrikazu platnePrikazy;

    private boolean vstat = false;

    
    
    /**
    *  Konstruktor třídy
    *  
    *  @param platnePrikazy seznam příkazů,
    *                       které je možné ve hře použít,
    *                       aby je nápověda mohla zobrazit uživateli. 
    */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }
    
    /**
     *  Vrací základní nápovědu po zadání příkazu "nápověda".
     *  
     *  @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Tvým úkolem je se úspěšně vypravit do školy, musíš vykonat následující povinnosti. \n\n"
                + "Nejdříve musíš vstát z postele. \n\n"
                + "Další úkony mohou být udělány v libovolném pořadí: \n"
                + "- Musíš si dojít na záchod přes chodbu a předsíň, vykonat potřebu. \n"
                + "- Musíš dojít do kuchyně přes chodbu a vzít si vitamíny. \n"
                + "- Musíš jít přes chodbu do koupelny a učesat se a vyčistit si zuby. \n"
                + "- Musíš se jít obléknout do pokojíčku a v přesíni si obout boty. \n"
                + "- Musíš si vzít batoh a v kuchyni si do něj dát svačinu (houska a pití). Toť vše. \n\n"
                + "Na konci dojdeš ke dveřím, kde na tebe čeká matka. Matka zkontroluje, že sis \nnezapomněl vzít " +
                "svačinu a že jsi udělal všechno, co jsi měl.\n\n"
                + "Můžeš zadat tyto příkazy:\n"
                + platnePrikazy.vratNazvyPrikazu();
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
