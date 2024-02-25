package cz.vse.semestralkaadventurabrad14.logika;
/**
 * Třída PrikazUkaz implementuje pro hru příkaz "ukaž".
 * Tímto příkazem lze zobrazit obsah batohu nebo postup v plnění úkolů.
 * Implementuje rozhraní IPrikaz.
 */
public class PrikazUkaz implements IPrikaz {
    public static final String NAZEV = "ukaž";

    private HerniPlan herniPlan;
    private Hra hra;

    /**
     * Konstruktor třídy PrikazUkaz.
     *
     * @param herniPlan instance HerniPlan, ve které se provádí příkaz
     * @param hra       instance Hra, ve které se provádí příkaz
     */
    public PrikazUkaz(HerniPlan herniPlan, Hra hra) {
        this.herniPlan = herniPlan;
        this.hra = hra;
    }

    /**
     * Metoda provedPrikaz provádí příkaz "ukaž". Zobrazuje obsah batohu nebo postup v plnění úkolů.
     *
     * @param parametry parametry příkazu, může obsahovat název objektu k ukázání
     * @return výsledek provedení příkazu jako řetězec
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // pokud hráč nezadá název předmětu k ukázání
        if (parametry.length == 0) {
            return "Co mám ukázat? Musíš k příkazu ukaž přidat i objekt k ukázání: Ukázat ti můžu 'batoh' nebo 'postup'.";
        }

        // Získání názvu objektu k ukázání
        String nazevPredmetu = parametry[0];

        // Pokud hráč chce ukázat obsah batohu
        if (nazevPredmetu.equals("batoh")) {
            // Získání instance batohu z herního plánu
            Batoh batoh = herniPlan.getBatoh();

            // Kontrola, zda hráč vstal z postele
            if (!hra.isVstat()) {
                return "Stále jsi nevstal z postele!";
            }

            // Kontrola, zda hráč má sebraný batoh
            if (!hra.isSebranyBatoh()) {
                return "Nejprve musíš sebrat batoh.";
            }

            // Pokud batoh obsahuje nějaké předměty
            if (!batoh.getSeznamPredmetu().isEmpty()) {
                // Vytvoření popisu obsahu batohu
                String popis = "Tvůj batoh obsahuje:\n";
                for (Predmet predmet : batoh.getSeznamPredmetu()) {
                    popis += "- " + predmet.getNazev() + "\n";

                }
                return popis;
            }

            // Pokud je batoh prázdný
            return "Batoh je zatím prázdný.";
        }

        // Pokud hráč chce ukázat postup v plnění úkolů
        if (nazevPredmetu.equals("postup")) {
            return ukazPostup();
        }

        // Pokud hráč zadal neplatný objekt k ukázání
        return "Tuto věc ti nemohu ukázat. Ukázat ti můžu 'batoh' nebo 'postup'.";
    }

    /**
     * Metoda ukazPostup zobrazuje postup v plnění úkolů.
     *
     * @return postup v plnění úkolů jako řetězec
     */
    private String ukazPostup() {
        // Inicializace objektu pro sestavení textového popisu postupu
        StringBuilder postupText = new StringBuilder();

        // Přidání informací o postupu v plnění úkolů do textového popisu
        postupText.append("Postup ve splňování úkolů:\n");
        postupText.append("- Vstát: ").append(hra.isVstat() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Vykonat potřebu: ").append(hra.isVykonalPotrebu() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Vzít si léky: ").append(hra.isVzalSiLeky() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Učesat se: ").append(hra.isUcesalSe() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Vyčistit si zuby: ").append(hra.isVycistilSiZuby() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Obléknout se: ").append(hra.isObleknulSe() ? "Splněno" : "Nesplněno").append("\n");
        postupText.append("- Obout se: ").append(hra.isObulSe() ? "Splněno" : "Nesplněno").append("\n");

        // Přidání dodatečného textu o prohlížení sebraných předmětů v batohu
        postupText.append("\n Sebranné předměty si prohlédni v batohu pomocí 'ukaž batoh'.");

        // Vrácení textového popisu postupu
        return postupText.toString();
    }

    /**
     * Metoda getNazev vrací název příkazu.
     *
     * @return název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
