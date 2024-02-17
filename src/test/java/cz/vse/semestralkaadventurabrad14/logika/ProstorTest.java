package cz.vse.semestralkaadventurabrad14.logika;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author    Jarmila Pavlíčková
 * @version   pro skolní rok 2016/2017
 **/
public class ProstorTest {
    /**
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @BeforeEach
    public void setUp() {
    }

    /**
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }


    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {		
        Prostor prostor1 = new Prostor("hala", "vstupní hala budovy VŠE na Jižním městě");
        Prostor prostor2 = new Prostor("bufet", "bufet, kam si můžete zajít na svačinku");
        Prostor prostor3 = new Prostor("recepce", "recepce, kde se člověk může na něco zeptat");
        prostor1.setVychod(prostor2);
        prostor2.setVychod(prostor1);
        prostor2.setVychod(prostor3);
        assertEquals(prostor2, prostor1.vratSousedniProstor("bufet"));
        assertNull(prostor2.vratSousedniProstor("pokoj"));
        assertNull(prostor1.vratSousedniProstor(""));
    }

    /**
     * Testuje metody vlozPredmet, vyberPredmet a odeberPredmet
     */
    @Test
    public void testVlozPredmetVyberPredmetOdeberPredmet() {
        Prostor pokoj = new Prostor("pokoj", "nějaký popis");
        Predmet truhla = new Predmet("truhla", false, true, true);
        Predmet zlato =new Predmet("zlato", true, false, true);
        pokoj.vlozPredmet(truhla);
        truhla.vlozPredmet(zlato);
        assertEquals(zlato, truhla.vyberPredmet("zlato"));
        assertEquals(1, truhla.getSeznamPredmetu().size());
        truhla.odeberPredmet(zlato);
        assertEquals(0, truhla.getSeznamPredmetu().size());
    }
}
