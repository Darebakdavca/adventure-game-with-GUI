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
    public TitledPane mapa;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();

    private ObservableList<Prostor> seznamVychodu= FXCollections.observableArrayList();
    private ObservableList<Predmet> seznamBatohu = FXCollections.observableArrayList();

    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    /**
     * Inicializuje hlavní kontrolér hry.
     * Nastavuje počáteční UI komponenty, včetně textových polí a seznamů, a registruje pozorovatele pro změny ve hře.
     */
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
        panelBatohu.setOnMouseClicked(event -> {
            Predmet vybranyPredmet = panelBatohu.getSelectionModel().getSelectedItem();
            if (vybranyPredmet != null) {
                hra.getHerniPlan().getBatoh().odeberPredmet(vybranyPredmet.getNazev());
                hra.getHerniPlan().getAktualniProstor().vlozPredmet(vybranyPredmet);
            }
        });
    }

    /**
     * Registruje pozorovatele pro různé typy změn ve hře, včetně změny místnosti, konce hry a změn v batohu.
     */
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


    /**
     * Vytvoří novou instanci hry, vymaže výstupní textové pole a aktualizuje UI komponenty pro odražení nového stavu hry.
     */
    private void vytvorHru() {
        hra = new Hra();
        vystup.clear();
        vystup.appendText(hra.vratUvitani() + "\n\n");
        titledPaneBatoh.setVisible(false);
        titledPaneBatoh.setManaged(false);
        tlacitkoOdesli.setDisable(false);
        panelVychodu.setDisable(false);
        panelBatohu.setDisable(false);
        vystup.setDisable(false);
        vstup.setDisable(false);
        mapa.setDisable(false);
        registrujZmeny();
        aktualizujSeznamVychodu();
        aktualizujSeznamBatohu();
        aktualizujPolohuHrace();
    }

    /**
     * Načte a uloží souřadnice pro různé prostory ve hře do mapy pro pozdější použití při aktualizaci polohy hráče.
     */
    private void vlozSouradnice() {
        souradniceProstoru.put("pokojíček", new Point2D(211, 262));
        souradniceProstoru.put("chodba", new Point2D(205, 163));
        souradniceProstoru.put("kuchyň", new Point2D(53, 159));
        souradniceProstoru.put("koupelna", new Point2D(333, 174));
        souradniceProstoru.put("předsíň", new Point2D(197, 73));
        souradniceProstoru.put("záchod", new Point2D(301, 23));
        souradniceProstoru.put("dveře", new Point2D(79, 35));
    }

    /**
     * Aktualizuje zobrazení panelu batohu pokud je batoh sebrán.
     */
    private void aktualizujSebraniBatohu() {
        titledPaneBatoh.setVisible(true);
        titledPaneBatoh.setManaged(true);
    }

    /**
     * Aktualizuje seznam vychodů v UI na základě aktuálního prostoru, ve kterém se hráč nachází.
     */
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * Aktualizuje seznam předmětů v batohu v UI.
     */
    @FXML
    private void aktualizujSeznamBatohu() {
        seznamBatohu.clear();
        seznamBatohu.addAll(hra.getHerniPlan().getBatoh().getSeznamPredmetu());
    }

    /**
     * Aktualizuje polohu hráče v UI na základě aktuálního prostoru.
     */
    private void aktualizujPolohuHrace() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }

    /**
     * Aktualizuje UI komponenty na základě aktuálního stavu hry.
     */
    public void aktualizujKonecHry() {
        if (hra.konecHry()) {
            vystup.appendText(hra.vratEpilog());
        }
        vstup.setDisable(hra.konecHry());
        tlacitkoOdesli.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
        panelBatohu.setDisable(hra.konecHry());
        vystup.setDisable(hra.konecHry());
        mapa.setDisable(hra.konecHry());
    }

    /**
     * Zpracuje vstup od hráče a zavolá metodu zpracujPrikaz.
     */
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    /**
     * Zpracuje příkaz od hráče a zobrazí výstup v UI.
     */
    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
    }

    /**
     * Zobrazí dialogové okno pro potvrzení ukončení hry a případně hru ukončí.
     */
    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pozor! Opravdu chceš ukončit hru? Bude ztracen veškerý postup.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Zpracuje kliknutí na panel východů a zavolá metodu zpracujPrikaz.
     */
    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil==null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil.getNazev();
        zpracujPrikaz(prikaz);
    }

    /**
     * Zobrazí okno s nápovědou.
     */
    @FXML
    private void napovedaKlik(ActionEvent actionEvent) {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }

    /**
     * Zobrazí dialogové okno pro potvrzení začátku nové hry a případně vytvoří novou hru.
     */
    @FXML
    private void novaHraKlik(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pozor! Opravdu chceš začít novou hru? Bude ztracen veškerý postup.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            vytvorHru();
        }
    }
}

