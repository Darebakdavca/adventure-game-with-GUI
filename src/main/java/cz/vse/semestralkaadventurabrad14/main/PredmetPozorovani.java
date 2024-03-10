package cz.vse.semestralkaadventurabrad14.main;

/**
 * PredmetPozorovani je rozhraní pro všechny třídy, které vystupují jako předmět pozorování.
 */
public interface PredmetPozorovani {
    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
