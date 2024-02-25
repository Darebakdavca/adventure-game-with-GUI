package cz.vse.semestralkaadventurabrad14.logika;


import cz.vse.semestralkaadventurabrad14.main.Pozorovatel;
import cz.vse.semestralkaadventurabrad14.main.PredmetPozorovani;

import java.util.HashSet;
import java.util.Set;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 */
public class HerniPlan implements PredmetPozorovani {
    
    private Prostor aktualniProstor;
    private Batoh batoh;
    private Set<Pozorovatel> seznamPozorovatelu = new HashSet<>();

    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     */
    public HerniPlan() {
        zalozProstoryHry();
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví pokojíček.
     *  Vytváří předměty a umisťuje je do příslušných prostorů.
     *  Inicializuje batoh.
     */
    private void zalozProstoryHry() {
        // vytvoření jednotlivých prostorů
        Prostor pokojicek = new Prostor("pokojíček","pokojíček, ve kterém spíš a můžeš se obléknout");
        Prostor chodba = new Prostor("chodba", "chodba, ze které se dá dostat do ostatních částí bytu");
        Prostor kuchyn = new Prostor("kuchyň","kuchyň, kde se nachází svačina, pití a je zde možnost si vzít léky");
        Prostor koupelna = new Prostor("koupelna","koupelna, kde se čistí zuby a češe se");
        Prostor predsin = new Prostor("předsíň","předsíň, ze které se dá dostat ke dveřím a nebo na záchod, jsou zde boty");
        Prostor zachod = new Prostor("záchod", "záchod, kde můžeš vykonat potřebu");
        Prostor dvere = new Prostor("dveře", "dveře, ze kterých se dá odejít z bytu, čeká zde matka");
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        pokojicek.setVychod(chodba);
        chodba.setVychod(pokojicek);
        chodba.setVychod(kuchyn);
        chodba.setVychod(koupelna);
        chodba.setVychod(predsin);
        kuchyn.setVychod(chodba);
        koupelna.setVychod(chodba);
        predsin.setVychod(chodba);
        predsin.setVychod(zachod);
        predsin.setVychod(dvere);
        zachod.setVychod(predsin);
        dvere.setVychod(predsin);

        // vytvoření předmětů
        Predmet lednice = new Predmet("lednička", false, true, false);
        Predmet houska = new Predmet("houska", true, false, true);
        Predmet marmelada = new Predmet("marmeláda", true, false, true);
        Predmet piti = new Predmet("pití", true, false, true);
        Predmet skrin = new Predmet("skříň", false, true, false);

        // umístění předmětů do místností nebo do jiných předmětů
        kuchyn.vlozPredmet(lednice);
        kuchyn.vlozPredmet(skrin);
        lednice.vlozPredmet(houska);
        lednice.vlozPredmet(marmelada);
        skrin.vlozPredmet(piti);


        // hra začíná v pokojíčku
        aktualniProstor = pokojicek;

        // vytvoření batohu
        batoh = new Batoh(2);
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     *  Metoda vrací odkaz na batoh.
     * @return batoh
     */
    public Batoh getBatoh() {
        return batoh;
    }

    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       upozorniPozorovatele();
    }

    @Override
    public void registruj(Pozorovatel pozorovatel) {
        seznamPozorovatelu.add(pozorovatel);
    }

    public void upozorniPozorovatele() {
        for (Pozorovatel pozorovatel : seznamPozorovatelu) {
            pozorovatel.aktualizuj();
        }
    }
}
