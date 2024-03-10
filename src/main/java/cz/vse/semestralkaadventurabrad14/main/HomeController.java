package cz.vse.semestralkaadventurabrad14.main;

import cz.vse.semestralkaadventurabrad14.logika.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeController {

    @FXML
    public TextArea vystup;
    @FXML
    public Button tlacitkoOdesli;
    @FXML
    public ListView<Prostor> panelVychodu;
    @FXML
    public TitledPane titledPaneBatoh;
    @FXML
    public ListView<Predmet> panelBatohu;
    @FXML
    public ImageView hrac;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();

    private ObservableList<Prostor> seznamVychodu= FXCollections.observableArrayList();
    private ObservableList<Predmet> seznamBatohu = FXCollections.observableArrayList();

    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    @FXML
    private void initialize() {
        vystup.appendText(hra.vratUvitani() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        panelBatohu.setItems(seznamBatohu);
        registrujZmeny();
        aktualizujSeznamVychodu();
        vlozSouradnice();
        panelVychodu.setCellFactory(param -> new ListCellProstor());
        panelBatohu.setCellFactory(param -> new ListCellPredmet());
    }

    private void registrujZmeny() {
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            Platform.runLater(() -> vstup.requestFocus());
            aktualizujPolohuHrace();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        hra.getHerniPlan().getBatoh().registruj(ZmenaHry.ZMENA_BATOHU, () -> aktualizujSeznamBatohu());
        hra.getHerniPlan().getBatoh().registruj(ZmenaHry.ZMENA_SEBRANI_BATOHU, () -> aktualizujSebraniBatohu());
    }



    private void vytvorHru() {
        hra = new Hra();
        vystup.clear();
        vystup.appendText(hra.vratUvitani() + "\n\n");
        titledPaneBatoh.setVisible(false);
        titledPaneBatoh.setManaged(false);
        registrujZmeny();
        aktualizujSeznamVychodu();
        aktualizujSeznamBatohu();
        aktualizujPolohuHrace();
    }

    private void vlozSouradnice() {
        souradniceProstoru.put("pokojíček", new Point2D(211, 262));
        souradniceProstoru.put("chodba", new Point2D(205, 163));
        souradniceProstoru.put("kuchyň", new Point2D(53, 159));
        souradniceProstoru.put("koupelna", new Point2D(333, 174));
        souradniceProstoru.put("předsíň", new Point2D(197, 73));
        souradniceProstoru.put("záchod", new Point2D(301, 23));
        souradniceProstoru.put("dveře", new Point2D(79, 35));
    }
    private void aktualizujSebraniBatohu() {
        titledPaneBatoh.setVisible(true);
        titledPaneBatoh.setManaged(true);
    }
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }
    @FXML
    private void aktualizujSeznamBatohu() {
        seznamBatohu.clear();
        seznamBatohu.addAll(hra.getHerniPlan().getBatoh().getSeznamPredmetu());
    }


    private void aktualizujPolohuHrace() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }

    public void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }
        vstup.setDisable(hra.konecHry());
        tlacitkoOdesli.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
    }

    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
    }

    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pozor! Opravdu chceš ukončit hru? Bude ztracen veškerý postup.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil==null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil.getNazev();
        zpracujPrikaz(prikaz);
    }

    @FXML
    private void napovedaKlik(ActionEvent actionEvent) {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }

    @FXML
    private void novaHraKlik(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pozor! Opravdu chceš začít novou hru? Bude ztracen veškerý postup.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vytvorHru();
        }
    }
}

