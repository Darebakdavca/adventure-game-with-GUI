package cz.vse.semestralkaadventurabrad14.logika;

/**
 *  Třída Hra - třída představující logiku adventury.
 * 
 *  Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vypisuje uvítací a ukončovací text hry.
 *  Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private boolean sebranyBatoh = false; // nastavení na false na začátku hry
    private boolean prvniVystraha = false; // slouží pro zaznamenání výstrahy, kterou hráč dostane u dveří při kontrole splnění úkolů matkou

    /**
     * deklarace počátečních false hodnot pro příkazy, které se musí splnit pro úspěšné dokončení hry
     */
    private boolean vstal = false; // při spuštění hry postava leží v posteli
    private boolean vykonalPotrebu = false;
    private boolean vzalSiLeky = false;
    private boolean ucesalSe = false;
    private boolean vycistilSiZuby = false;
    private boolean obleknulSe = false;
    private boolean obulSe = false;

    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {

        // inicializace
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVstat(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazVykonatPotrebu(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazVzitLeky(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazUcesatSe(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazVycistitZuby(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazOblectSe(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazOboutSe(herniPlan,this));
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazUkaz(herniPlan,this));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazZkontroluj(herniPlan, this));
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Vítejte!\n" +
               "Toto je příběh o ranním vstávání do školy.\n" +
               "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        return "\n\nDěkuji za zahrání adventury. Ahoj.\n";
    }

    /**
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String [] parametry = new String[slova.length-1];
        for(int i = 0 ;i < parametry.length; i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }


    /**
     * Nastaví, že postava vstala z postele.
     *
     * @param vstal hodnota false = nemůže jít pryč z pokoje, true = může pokračovat ve hře
     */
    void setVstalZPostele(boolean vstal) {
         this.vstal = vstal;
    }

    /**
     * vrací jestli postava vstala
     *
     * @return stav vstal
     */
    public boolean isVstat() {
        return vstal;
    }


    /**
     * Nastaví, že postava vykonala potřebu.
     *
     * @param vykonalPotrebu hodnota false = pokud postava přijde k matce tak se pokadí, true = nepokadí se a pokračuje se v hodnocení matkou
     */
    void setVykonalPotrebu(boolean vykonalPotrebu) {
        this.vykonalPotrebu = vykonalPotrebu;
    }

    /**
     * vrací jestli postava vykonala potřebu
     *
     * @return stav vykonalPotrebu
     */
    public boolean isVykonalPotrebu() {
        return vykonalPotrebu;
    }


    /**
     * Nastaví, že si postava vzala léky.
     *
     * @param vzalSiLeky hodnota false = postava si nevzala leky, true = postava si vzala leky
     */
    public void setVzalSiLeky(boolean vzalSiLeky) {
        this.vzalSiLeky = vzalSiLeky;
    }

    /**
     * vrací jestli si postava vzala leky
     *
     * @return stav vzalSiLeky
     */
    public boolean isVzalSiLeky() {
        return vzalSiLeky;
    }


    /**
     * Nastaví, že se postava učesala.
     *
     * @param ucesalSe hodnota false = postava se neučesala, true = postava se učesala
     */
    public void setUcesalSe(boolean ucesalSe) {
        this.ucesalSe = ucesalSe;
    }

    /**
     * vrací jestli se postava učesala
     * @return stav ucesalSe
     */
    public boolean isUcesalSe() {
        return ucesalSe;
    }


    /**
     * Nastaví, že si postava vyčistila zuby.
     *
     * @param vycistilSiZuby hodnota false = postava si nevyčistila zuby, true = postava si vyčistila zuby
     */
    public void setVycistilSiZuby(boolean vycistilSiZuby) {
        this.vycistilSiZuby = vycistilSiZuby;
    }

    /**
     * vrací jestli si postava vyčistila zuby
     * @return stav vycistilSiZuby
     */
    public boolean isVycistilSiZuby() {
        return vycistilSiZuby;
    }


    /**
     * Nastaví, že si postava vyčistila zuby.
     *
     * @param obleknulSe hodnota false = postava se neoblékla, true = postava se oblékla
     */
    public void setObleknulSe(boolean obleknulSe) {
        this.obleknulSe = obleknulSe;
    }

    /**
     * vrací jestli se postava obleknula
     * @return stav obleknulSe
     */
    public boolean isObleknulSe() {
        return obleknulSe;
    }


    /**
     * Nastaví, že si postava vyčistila zuby.
     *
     * @param obulSe hodnota false = postava se neobula, true = postava se obula
     */
    public void setObulSe(boolean obulSe) {
        this.obulSe = obulSe;
    }

    /**
     * vrací jestli se postava obleknula
     * @return stav obleknulSe
     */
    public boolean isObulSe() {
        return obulSe;
    }


    /**
     * Nastaví, že postava sebrala batoh
     *
     * @param sebranyBatoh hodnota false = batoh není sebraný, true = postava sebrala batoh
     */
    public void setSebranyBatoh(boolean sebranyBatoh) {
        this.sebranyBatoh = sebranyBatoh;
    }

    /**
     * vrací jestli postava sebrala batoh
     * @return stav sebranyBatoh
     */
    public boolean isSebranyBatoh() {
        return sebranyBatoh;
    }


    /**
     * Nastaví, že hráč dostal první výstrahu
     *
     * @param prvniVystraha hodnota false = nedostal první výstrahu, true = dostal první výstrahu
     */
    public void setPrvniVystraha(boolean prvniVystraha) {
        this.prvniVystraha = prvniVystraha;
    }

    /**
     * vrací jestli hráč už dostal první výstrahu
     * @return stav sebranyBatoh
     */
    public boolean isPrvniVystraha() {
        return prvniVystraha;
    }

    /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return herniPlan;
     }


}

