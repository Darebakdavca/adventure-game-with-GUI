package cz.vse.semestralkaadventurabrad14.logika;

import cz.vse.semestralkaadventurabrad14.main.Pozorovatel;
import cz.vse.semestralkaadventurabrad14.main.PredmetPozorovani;
import cz.vse.semestralkaadventurabrad14.main.ZmenaHry;

import java.util.*;

/**
 * Třída Batoh představuje batoh hráče ve hře.
 * Hráč v batohu uchovává předměty, které sebral.
 */
public class Batoh implements PredmetPozorovani {

    private List<Predmet> seznamPredmetu;
    private int kapacita; //maximální kapacita batohu

    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    /**
     * Konstruktor třídy.
     * Vytvoří prázdný batoh pro hráče.
     *
     * @param kapacita maximální kapacita batohu
     */
    public Batoh(int kapacita) {
        this.kapacita = kapacita;
        seznamPredmetu = new ArrayList<>();
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     * Vloží předmět do batohu.
     *
     * @param predmet předmět k vložení
     */
    public void vlozPredmet(Predmet predmet) {
        seznamPredmetu.add(predmet);
        upozorniPozorovatele(ZmenaHry.ZMENA_BATOHU);
    }

    /**
     * Odebere předmět z batohu
     *
     * @param nazevPredmetu předmět k odebrání
     * @return předmět, který je odebrán, nebo hodnotu null pokud předmět v batohu není
     */
    public Predmet odeberPredmet(String nazevPredmetu) {
        Predmet result = null;
        for (Predmet predmet : seznamPredmetu) {
            if (predmet.getNazev().equalsIgnoreCase(nazevPredmetu)) {
                result = predmet;
                seznamPredmetu.remove(predmet);
                upozorniPozorovatele(ZmenaHry.ZMENA_BATOHU);
                return result;
            }
        }
        return result;
    }

    /**
     * Zjistí, zda je předmět v batohu.
     *
     * @param nazevPredmetu název předmětu k ověření
     * @return true, pokud je předmět v batoh, jinak false
     */
    public boolean obsahujePredmet(String nazevPredmetu) {
        for (Predmet predmet : seznamPredmetu) {
            if (predmet.getNazev().equalsIgnoreCase(nazevPredmetu)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Vrací seznam předmětů v batohu.
     *
     * @return seznam předmětů v batohu
     */
    public List<Predmet> getSeznamPredmetu() {
        return seznamPredmetu;
    }


    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }

    public void upozorniPozorovatele(ZmenaHry zmenaHry) {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }
}
