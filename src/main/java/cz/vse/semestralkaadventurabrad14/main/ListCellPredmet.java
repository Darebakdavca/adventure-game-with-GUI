package cz.vse.semestralkaadventurabrad14.main;

import cz.vse.semestralkaadventurabrad14.logika.Predmet;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída ListCellPredmet představuje grafickou podobu předmětu v seznamu předmětů.
 * Tato třída je součástí jednoduché textové hry.
 */
public class ListCellPredmet extends ListCell<Predmet> {
    @Override
    protected void updateItem(Predmet predmet, boolean empty) {
        super.updateItem(predmet, empty);
        if (empty) {
            setGraphic(null);
        } else {
            String cesta = getClass().getResource("batoh/"+predmet.getNazev()+".jpeg").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(40);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
