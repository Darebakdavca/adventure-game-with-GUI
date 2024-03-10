/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package cz.vse.semestralkaadventurabrad14.main;


import cz.vse.semestralkaadventurabrad14.logika.Hra;
import cz.vse.semestralkaadventurabrad14.logika.IHra;
import cz.vse.semestralkaadventurabrad14.uiText.TextoveRozhrani;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru určenou k dalším úpravám a rozšiřování.
 *
 * @author    Jarmila Pavlíčková
 * @version   ZS 2016/2017
 */
public class Start extends Application
{
    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */
    public static void main(String[] args)
    {
        if (args.length > 0 && args[0].equals("text")) {
            IHra hra = new Hra();
            TextoveRozhrani ui = new TextoveRozhrani(hra);
            ui.hraj();
            Platform.exit();
        } else {
            launch();
        }
    }

    /**
     *  Je volána, pokud je aplikace spuštěna bez parametru "text" v příkazovém řádku,
     *  a zahajuje grafické uživatelské rozhraní (GUI) verzi hry.
     *
     *  V této metodě se nastavuje a zobrazuje hlavní okno aplikace.
     *  Inicializuje se {@link FXMLLoader} pro načtení GUI z definovaného souboru FXML,
     *  vytváří se scéna z načteného kořenového uzlu a nastavuje se na pódium (stage).
     *  Nakonec se pódium zobrazí uživateli s titulkem "Adventura".
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Adventura");

    }
}

