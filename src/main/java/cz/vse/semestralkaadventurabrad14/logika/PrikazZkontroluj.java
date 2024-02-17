package cz.vse.semestralkaadventurabrad14.logika;
/**
 * Třída PrikazZkontroluj implementuje pro hru příkaz "zkontroluj".
 * Tato třída je součástí jednoduché textové hry.
 *
 * Matka zkontroluje hráčovi jeho splnění všech úkolů
 */
public class PrikazZkontroluj implements IPrikaz {
    private static final String NAZEV = "zkontroluj";

    private Hra hra;
    private HerniPlan herniPlan;
    public PrikazZkontroluj(HerniPlan herniPlan, Hra hra) {
        this.hra = hra;
        this.herniPlan = herniPlan;
    }

    /**
     * Příkaz proběhne jen v prostoru dveře.
     * U dveří bude bude zkontrolováno, že byli splněny úkoly.
     *
     * Můžou nastat různé scénáře, podle toho co je splěno a co ne.
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return
     */
    @Override
    public String provedPrikaz(String... parametry) {
        // Kontrola, zda hráč vstal z postele
        if (!hra.isVstat()) {
            return "Stále jsi nevstal z postele!";
        }

        // Příkaz lze použít jen u dveří
        if (herniPlan.getAktualniProstor().getNazev() == "dveře") {
            // Porážka: Hráč zapomněl vykonat potřebu
            if (!hra.isVykonalPotrebu()) {
                hra.setKonecHry(true);
                return "Prohrál jsi! Zapomněl jsi vykonat potřebu a podělal ses.";
            }

            // Porážka: hráč si zapomněl něco vzít nebo si vzal něco navíc
            // zapomněl housku
            else if (!herniPlan.getBatoh().obsahujePredmet("houska")) {
                if (hra.isPrvniVystraha()) {
                    hra.setKonecHry(true);
                    return  "Plesk! Dostal jsi druhý flákanec od matky.\nChybí ti v batohu houska! Prohrál jsi hru.";
                } else {
                    hra.setPrvniVystraha(true);
                    return "Plesk! Dostal jsi flákanec od matky.\nChybí ti v batohu houska! Máš jednu šanci to napravit, jinak bude zle!";
                }
            } // zapomněl pití
            else if (!herniPlan.getBatoh().obsahujePredmet("pití")) {
                if (hra.isPrvniVystraha()) {
                    hra.setKonecHry(true);
                    return "Plesk! Dostal jsi druhý flákanec od matky.\nChybí ti v batohu pití! Prohrál jsi hru.";
                } else {
                    hra.setPrvniVystraha(true);
                    return "Plesk! Dostal jsi flákanec od matky.\nChybí ti v batohu pití! Máš jednu šanci to napravit, jinak bude zle!";
                }
            } // má v batohu marmeládu
            else if (herniPlan.getBatoh().obsahujePredmet("marmeláda")) {
                if (hra.isPrvniVystraha()) {
                    hra.setKonecHry(true);
                    return "Plesk! Dostal jsi druhý flákanec od matky.\nUž podruhé jsi jí sbalil sebou do školy marmeládu! Prohrál jsi hru.";
                } else {
                    hra.setPrvniVystraha(true);
                    return "Plesk! Dostal jsi flákanec od matky.\nVzal jsi ji z ledničky její oblíbenou marmeládu od babičky!\nMáš jednu šanci na to jí z batohu vyndat\na vzít si správnou svačinu, jinak bude zle!";
                }
            } // nesplnil nějaký z úkolů
            else if (!hra.isObleknulSe() || !hra.isObulSe() || !hra.isUcesalSe() || !hra.isSebranyBatoh() || !hra.isVycistilSiZuby() || !hra.isVzalSiLeky()) {
                if (hra.isPrvniVystraha()) {
                    hra.setKonecHry(true);
                    return "Plesk! Dostal jsi druhý flákanec od matky.\nNeprovedl jsi některý z úkolů! Prohrál jsi hru.";
                } else {
                    hra.setPrvniVystraha(true);
                    return "Plesk! Dostal jsi flákanec od matky.\nNeprovedl jsi některý z úkolů!\nMáš jednu šanci na to vše splnit.\n" +
                            "Pro zjištění stavu splnění úkolů použij příkaz 'ukaž postup'.\nPro zjištění obsahu batohu použij 'ukaž batoh'";
                }
            }
            //výhra
            else {
                hra.setKonecHry(true);
                return "\nGratulace! Úspěšně jsi se vypravil do školy!";
            }
        }
        return "Zde ti nikdo tvé splnění úkolů nezkontroluje. Matka na tebe čeká u dveří.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
