package cz.vse.semestralkaadventurabrad14.logika;

import java.util.Scanner;

/**
 *  Třída PrikazKonec implementuje pro hru příkaz konec.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 */

class PrikazKonec implements IPrikaz {

    private static final String NAZEV = "konec";

    private Hra hra;

    /**
     *  Konstruktor třídy
     *  
     *  @param hra odkaz na hru, která má být příkazem konec ukončena
     */    
    public PrikazKonec(Hra hra) {
        this.hra = hra;
    }

    /**
     * V případě, že příkaz má jen jedno slovo "konec" hra končí(volá se metoda setKonecHry(true))
     * jinak pokračuje např. při zadání "konec a".
     * 
     * @return zpráva, kterou vypíše hra hráči
     */

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length > 0) {
            return "Ukončit co? Nechápu, proč jste zadal druhé slovo.";
        }
        else {
            return varovaniKonec();
        }
    }

    /**
     * Metoda zajišťuje kontrolu, že hráč chce opravdu ukončit hru.
     *
     * @return zpráva, která je vypsána hráči
     */
    public String varovaniKonec() {
        System.out.println("Pozor! Opravdu chceš ukončit hru? Bude ztracen veškerý postup.");
        System.out.println("Ukončit hru: 'ano' 'ne'");
        Scanner in = new Scanner(System.in);
        String volba = in.nextLine().toLowerCase();
        if (volba.equals("ano")) {
            hra.setKonecHry(true);
            return "Hra ukončena příkazem konec";
        }
        if (volba.equals("ne")) {
            return "Hra nebyla ukončena";
        }
        else {
            return "Neplatný příkaz";
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
