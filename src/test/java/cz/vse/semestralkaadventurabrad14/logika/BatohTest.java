package cz.vse.semestralkaadventurabrad14.logika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída BatohTest slouží k otestování funkčnosti přidávání a odebírání předmětů do a z batohu.
 */

class BatohTest {

    Batoh batoh = new Batoh(2);
    Predmet houska = new Predmet("houska", true, false, true);
    Predmet piti = new Predmet("pití", true, false, true);
    @BeforeEach
    public void setUp() {

    }

    @Test
    void vlozPredmet() {
        assertEquals(0, batoh.getSeznamPredmetu().size());
        batoh.vlozPredmet(houska);
        assertEquals(1, batoh.getSeznamPredmetu().size());
    }

    @Test
    void odeberPredmet() {
        batoh.vlozPredmet(houska);
        assertEquals(1, batoh.getSeznamPredmetu().size());
        batoh.odeberPredmet("houska");
        assertEquals(0, batoh.getSeznamPredmetu().size());
    }

    @Test
    void obsahujePredmet() {
        batoh.vlozPredmet(piti);
        assertEquals(true, batoh.obsahujePredmet("pití"));
        assertEquals(false, batoh.obsahujePredmet("houska"));
    }
}