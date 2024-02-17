package cz.vse.semestralkaadventurabrad14.logika;

import java.util.ArrayList;
import java.util.List;


/**
 * Trida Predmet - popisuje jednotlivé predmety (věci) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * Tato třída umožňuje vytvářet herní předměty.
 * Předmět může v sobě obsahovat více předmětů (např. lednička má v sobě housku a jogurt)
 */
public class Predmet {
    private String nazev;

    // určí se, zda je předmet prenositelný
    private boolean prenositelny;

    private boolean muzeObsahovatPredmety;

    private boolean prozkoumany;

    // vytvoření listu pro ukládání předmětů do předmětu
    private List<Predmet> seznamPredmetu;

    /**
     * Konstruktor třídy.
     *
     * @param nazev
     * @param prenositelny
     * @param muzeObsahovatPredmety
     * @param prozkoumany
     */
    public Predmet(String nazev, boolean prenositelny, boolean muzeObsahovatPredmety, boolean prozkoumany) {
        this.nazev = nazev;
        this.prenositelny = prenositelny;
        this.muzeObsahovatPredmety = muzeObsahovatPredmety;
        this.prozkoumany = prozkoumany;

        seznamPredmetu = new ArrayList<>();
    }

    /**
     * Vrací název předmětu
     * @return nazev
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * vrací jestli je předmět přenositelný
     * @return stav prenositelny
     */
    public boolean isPrenositelny() {
        return prenositelny;
    }

    /**
     * vrací jestli je předmět prozkoumaný
     * @return stav prozkoumany
     */
    public boolean isProzkoumany() {
        return prozkoumany;
    }

    /**
     * Nastavuje stav prozkoumany.
     *
     * @param prozkoumany hodnota false = předmět je neprozkoumaný, true = předmět je prozkoumaný
     */
    public void prozkoumano (boolean prozkoumany) {
        this.prozkoumany = prozkoumany;
    }

    /**
     * vrací jestli předmět může obsahovat předměty
     * @return boolean
     */
    public boolean isMuzeObsahovatPredmety() {
        return muzeObsahovatPredmety;
    }

    /**
     * Nastavuje jestli předmět může obsahovat předměty
     * @param muzeObsahovatPredmety
     */
    public void setMuzeObsahovatPredmety(boolean muzeObsahovatPredmety) {
        this.muzeObsahovatPredmety = muzeObsahovatPredmety;
    }

    /**
     * Vloží předmět do seznamu předmětů v předmětu.
     *
     * @param predmet
     */
    public void vlozPredmet (Predmet predmet) {
        seznamPredmetu.add(predmet);
    }

    /**
     * Vybere zvolený předmět z předmětu
     * @param nazev
     * @return predmet nebo null, pokud předmět s daným názvem není v aktuálním prostoru
     */
    public Predmet vyberPredmet(String nazev) {
        Predmet predmet = null;
        for (Predmet neco : seznamPredmetu) {
            if (neco.getNazev().equals(nazev)) {
                predmet = neco;
                break;
            }
        }
        return predmet;
    }

    /**
     * Metoda odebere zvoleny předmět z místnosti
     *
     * @param predmet název předmětu, který se má najít
     */
    public void odeberPredmet (Predmet predmet) {
        if (predmet.isPrenositelny()) {
            seznamPredmetu.remove(predmet);
        }
    }

    /**
     * Prozkoumá a případně popíše předmět
     * @return popis
     */
    public String popisProzkoumej() {
        if (!this.muzeObsahovatPredmety) {
            this.prozkoumano(true);
            return "Prohlédl jsi si " + nazev + ".";
        }
        String popis = "Prohlédl jsi si " + nazev + " a uvnitř jsi našel:\n";
        for (Predmet predmet : seznamPredmetu) {
            popis += "- " + predmet.getNazev() + "\n";
        }
        this.prozkoumano(true);
        return popis;
    }

    /**
     * Metoda vrací název třídy "Předmět"
     * @return nazev
     */
    public String toString() {
        return nazev;
    }

    /**
     * Metoda vrací seznam předmětů v předmětu
     * @return
     */
    public List<Predmet> getSeznamPredmetu() {
        return seznamPredmetu;
    }


}


