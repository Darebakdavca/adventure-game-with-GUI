package cz.vse.semestralkaadventurabrad14.main;

import cz.vse.semestralkaadventurabrad14.logika.Hra;
import cz.vse.semestralkaadventurabrad14.logika.IHra;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

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

    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pozor! Opravdu chceš ukončit hru? Bude ztracen veškerý postup.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}

