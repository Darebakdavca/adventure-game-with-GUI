package cz.vse.semestralkaadventurabrad14.main;

import cz.vse.semestralkaadventurabrad14.logika.Prostor;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

import java.text.DecimalFormat;

/**
 * Třída ListCellProstor představuje grafickou podobu prostoru v seznamu prostorů.
 * Tato třída je součástí jednoduché textové hry.
 */
public class ListCellProstor extends ListCell<Prostor> {
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(prostor.getNazev());
            String cesta = getClass().getResource("Prostory/mistnosti/"+prostor.getNazev()+".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            iw.setFitWidth(150);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }
    }
}
