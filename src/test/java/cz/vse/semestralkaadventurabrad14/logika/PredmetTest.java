package cz.vse.semestralkaadventurabrad14.logika;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PredmetTest {
    /**
     * Testuje metody vlozPredmet, vyberPredmet a odeberPredmet
     */
    @Test
    public void testVlozPredmetVyberPredmetOdeberPredmet() {
        Prostor pokoj = new Prostor("pokoj", "nějaký popis");
        Predmet truhla = new Predmet("truhla", false, true, true);
        Predmet zlato = new Predmet("zlato", true, false, true);
        pokoj.vlozPredmet(truhla);
        truhla.vlozPredmet(zlato);
        assertEquals(zlato, truhla.vyberPredmet("zlato"));
        assertEquals(1, truhla.getSeznamPredmetu().size());
        truhla.odeberPredmet(zlato);
        assertEquals(0, truhla.getSeznamPredmetu().size());
    }

}