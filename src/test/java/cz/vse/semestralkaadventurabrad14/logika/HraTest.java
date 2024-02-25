package cz.vse.semestralkaadventurabrad14.logika;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra
 *
 * @author    Jarmila Pavlíčková
 * @version  pro školní rok 2016/2017
 */
public class HraTest {
    private Hra hra1;

    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @BeforeEach
    public void setUp() {
        hra1 = new Hra();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče.
     * 
     */
    @Test
    public void testPrubehHry() {
        assertEquals("pokojíček", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("jdi chodba");
        assertFalse(hra1.konecHry());
        assertEquals("chodba", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi koupelna");
        assertFalse(hra1.konecHry());
        assertEquals("koupelna", hra1.getHerniPlan().getAktualniProstor().getNazev());

        hra1.zpracujPrikaz("konec");
        hra1.zpracujPrikaz("ano");

        assertEquals(true, hra1.konecHry());
    }

    /**
     * Testuje první typ porážky: Hráč zapomněl vykonat potřebu
     */
    @Test
    public void testProhra1() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi dveře");
        assertEquals("dveře", hra1.getHerniPlan().getAktualniProstor().getNazev());
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("zkontroluj");
        assertEquals(false, hra1.isVykonalPotrebu());
        assertEquals(true, hra1.konecHry());
    }


    /**
     * Testuje druhý typ porážky: hráč si vezme místo vitamínů projímadlo
     */
    @Test
    public void testProhra2() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("vzítléky laxygal");
        assertEquals(true, hra1.konecHry());
    }

    /**
     * Testuje třetí typ porážky: hráč zapomene svačinu
     * Zároveň testuje přidávání a odebírání předmětů do batohu
     */
    @Test
    public void testProhra3() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("seber batoh");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("prozkoumej");
        hra1.zpracujPrikaz("prozkoumej lednička");
        hra1.zpracujPrikaz("prozkoumej skříň");

        hra1.zpracujPrikaz("seber houska");     //
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber marmeláda");  //
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());

        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi záchod");
        hra1.zpracujPrikaz("vykonatpotřebu");
        assertEquals(true, hra1.isVykonalPotrebu());
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi dveře");
        hra1.zpracujPrikaz("zkontroluj");
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("polož marmeláda");
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("seber pití");
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi dveře");
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("zkontroluj");
        assertEquals(true, hra1.konecHry());
    }

    /**
     * Testuje čtvrtý typ prohry: hráč zapomene vykonat některý z úkolů
     * Zároveň testuje funkčnost vykonávání úkolů
     */
    @Test
    public void testProhra4() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("seber batoh");
        hra1.zpracujPrikaz("obléknoutse");
        assertEquals(true, hra1.isObleknulSe());
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("vzítléky");
        // léky si může vzít jenom v kuchyni
        assertEquals(false, hra1.isVzalSiLeky());
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("prozkoumej");
        hra1.zpracujPrikaz("prozkoumej lednička");
        hra1.zpracujPrikaz("prozkoumej skříň");
        hra1.zpracujPrikaz("seber houska");
        hra1.zpracujPrikaz("seber pití");
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi koupelna");
        assertEquals("koupelna", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("učesatse");
        assertEquals(true, hra1.isUcesalSe());
        hra1.zpracujPrikaz("vyčistitzuby");
        assertEquals(true, hra1.isVycistilSiZuby());
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("oboutse");
        assertEquals(true, hra1.isObulSe());
        hra1.zpracujPrikaz("jdi záchod");
        hra1.zpracujPrikaz("vykonatpotřebu");
        assertEquals(true, hra1.isVykonalPotrebu());
        hra1.zpracujPrikaz("jdi předsíň");
        hra1.zpracujPrikaz("jdi dveře");
        assertEquals(false, hra1.konecHry());
        assertEquals(false, hra1.isPrvniVystraha());
        hra1.zpracujPrikaz("zkontroluj");
        // hráč má jednu šanci to napravit (nevzal si léky)
        assertEquals(false, hra1.konecHry());
        assertEquals(true, hra1.isPrvniVystraha());
        hra1.zpracujPrikaz("zkontroluj");
        // hráč si i po druhé nevzal léky
        assertEquals(true, hra1.konecHry());
    }

    /**
     * Testuje příkaz vzítléky
     */
    @Test
    public void testPrikazVzitLeky() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("vzítléky");
        assertEquals(false, hra1.isVzalSiLeky());
        hra1.zpracujPrikaz("vzítléky bkomplex");
        assertEquals(true, hra1.isVzalSiLeky());
    }

    /**
     * Testuje příkazy seber a polož
     */
    @Test
    public void testPrikazSeberPrikazPoloz() {
        hra1.zpracujPrikaz("vstát");
        hra1.zpracujPrikaz("seber batoh");
        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi kuchyň");
        hra1.zpracujPrikaz("prozkoumej");
        hra1.zpracujPrikaz("prozkoumej lednička");
        hra1.zpracujPrikaz("prozkoumej skříň");
        assertEquals(2, hra1.getHerniPlan().getAktualniProstor().vyberPredmet("lednička").getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber houska");
        assertEquals(1, hra1.getHerniPlan().getAktualniProstor().vyberPredmet("lednička").getSeznamPredmetu().size());
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber lednička");
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber skříň");
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber pití");
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("seber marmeláda");
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("polož marmeláda");
        assertEquals(2, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("polož pití");
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
        hra1.zpracujPrikaz("polož");
        assertEquals(1, hra1.getHerniPlan().getBatoh().getSeznamPredmetu().size());
    }
}
