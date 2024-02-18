package cz.vse.semestralkaadventurabrad14.main;

import cz.vse.semestralkaadventurabrad14.logika.Hra;
import cz.vse.semestralkaadventurabrad14.logika.IHra;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML
    public TextArea vystup;
    @FXML
    public Button tlacitkoOdesli;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();



    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
    }

    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
        vstup.clear();

        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);
        }
    }
}

