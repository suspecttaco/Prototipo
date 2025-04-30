module uas.mtds.prototipo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens uas.mtds.prototipo to javafx.fxml;
    exports uas.mtds.prototipo;
}