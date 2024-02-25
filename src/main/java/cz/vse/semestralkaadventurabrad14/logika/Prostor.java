package cz.vse.semestralkaadventurabrad14.logika;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 */
public class Prostor {

    private String nazev;           // nazev prostoru
    private String popis;           // popis prostoru
    private Set<Prostor> vychody;   // obsahuje sousední prostory
    private List<Predmet> seznamPredmetu; // seznam předmětů v prostoru

    /**
     * Vytvoří prostor se zadaným názvem a popisem.
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *        víceslovný název bez mezer
     * @param popis popis prostoru
     */
    public Prostor(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
        seznamPredmetu = new ArrayList<Predmet>();
        vychody = new HashSet<>();
    }

    /**
     * Přidá východ z prostoru, tj. sousední prostor, který lze dosáhnout z tohoto prostoru.
     *
     * @param vedlejsi prostor, který sousedí s aktuálním prostorem
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (Objects.equals(this.nazev, druhy.nazev));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru " + popis + ".\n"
                + popisVychodu();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy.
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    public String popisVychodu() {
        String vracenyText = "Východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += "\n- " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * Vkládá předmět do seznamu předmětů v prostoru.
     *
     * @param predmet předmět, který má být vložen do prostoru
     */
    public void vlozPredmet (Predmet predmet) {
        seznamPredmetu.add(predmet);
    }


    /**
     * Metoda vyberPredmet vybírá a vrací předmět se zadaným názvem z aktuálního prostoru.
     *
     * @param nazev název předmětu, který se má vybrat
     * @return vybraný předmět nebo null, pokud není možné předmět sebrat
     */
    public Predmet vyberPredmet (String nazev) {
        Predmet vybranyPredmet = najdiPredmetVMistnosti(nazev);
        return vybranyPredmet;
    }

    /**
     * Metoda odebere zvoleny předmět z místnosti nebo z předmětu ve kterém se zvolený předmět nachází
     *
     * @param nazev název předmětu, který se má najít
     */
    public void odeberPredmet (String nazev) {
        Predmet odebiranyPredmet = najdiPredmetVMistnosti(nazev);
        for (Predmet predmet : seznamPredmetu) {
            if (predmet.equals(odebiranyPredmet) && predmet.isPrenositelny()) {
                seznamPredmetu.remove(predmet);
                break;
            } else if (predmet.isMuzeObsahovatPredmety() && predmet.isProzkoumany()) {
                Predmet predmetKOdebrani = predmet.vyberPredmet(nazev);
                predmet.getSeznamPredmetu().remove(predmetKOdebrani);
            }
        }
    }

    /**
     * Vyhledá a vrátí předmět se zadaným názvem v aktuálním prostoru.
     * Pokud předmět s daným názvem není přímo v prostoru, ale je uvnitř jiného předmětu
     * v prostoru (např. v bedně nebo tašce), metoda rekurzivně prochází předměty v prostoru
     * a hledá předmět s odpovídajícím názvem.
     *
     * @param nazev název předmětu, který se má najít
     * @return nalezený předmět nebo null, pokud předmět s daným názvem není v aktuálním prostoru
     */
    private Predmet najdiPredmetVMistnosti(String nazev) {
        Predmet predmet = null;
        for (Predmet neco : seznamPredmetu) {
            if (neco.getNazev().equals(nazev)) {
                predmet = neco;
                predmet.prozkoumano(true);
                break;
            }
            else if (neco.isMuzeObsahovatPredmety() && neco.isProzkoumany()) {
                predmet = neco.vyberPredmet(nazev);
                if (predmet != null) {
                    break;
                }
            }
        }
        return predmet;
    }

    /**
     * Prozkoumá a případně popíše prostor
     * @return popis
     */
    public String popisProzkoumej() {
        if (seznamPredmetu.isEmpty()) {
            return "Prohlédl jsi si " + nazev + ".";
        }

        String popis = "Prohlédl jsi si " + nazev + " a uvnitř jsi našel:\n";
        for (Predmet neco : seznamPredmetu) {
            popis += "- " + neco.getNazev() + "\n";
        }
        return popis;
    }

    @Override
    public String toString() {
        return getNazev();
    }
}
