package cz.vse.semestralkaadventurabrad14.logika;
/**
 * Třída PrikazVycistitZuby implementuje pro hru příkaz "vyčistitzuby".
 * Tato třída je součástí jednoduché textové hry.
 *
 * Herní postava si vyčistí zuby
 */
public class PrikazVycistitZuby implements IPrikaz {
    public static final String NAZEV = "vyčistitzuby";

    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy
     *
     * @param herniPlan odkaz na herni plan pro určení správného prostoru pro vyčištění zubů
     * @param hra odkaz na hru, u které se má změnit parametr vycistilSiZuby
     */
    public PrikazVycistitZuby(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Provádí příkaz "vyčistitzuby" a mění parametr vycistilSiZuby
     * V případě, že příkaz má jen jedno slovo "vyčistitzuby" postava si vyčistí zuby.
     * Jinak pokračuje např. při zadání "vyčistizuby a".
     * Pokud je příkaz "vyčistizuby" vyvolán mimo prostor koupelna,
     * je tato skutečnost o nemožnosti použití příkazu hráči sdělena.
     * Hráč je po vyvolání příkazu informován v případě splnění o již hotovém úkolu.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (hra.isVycistilSiZuby()) {
            return "Postava is již vyčistila zuby.";
        }
        if (parametry.length > 0) {
            return "Vyčistit si zuby kde? Nechápu, proč jste zadal druhé slovo.";
        }
        else if (herniPlan.getAktualniProstor().getNazev().equals("koupelna")) {
            System.out.println("Začínáš si čistit zuby...");

            // časovač na 10 vteřin
            try {
                for (int i = 0; i < 10; i++) {
                    // simulace průběhu čištění zubů
                    Thread.sleep(1000); // 1 vteřina

                    // použití # pro zobrazení postupu
                    System.out.print("#");
                    System.out.flush(); // pro okamžité zobrazení výstupu
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hra.setVycistilSiZuby(true);
            return "\nZuby jsou čisté! Dobrá práce!";
        }
        return "Zde si nemůžeš čistit zuby.";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
