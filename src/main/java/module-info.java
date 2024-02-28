module cz.vse.semestralkaadventurabrad14 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens cz.vse.semestralkaadventurabrad14.main to javafx.fxml;
    exports cz.vse.semestralkaadventurabrad14.main;
}