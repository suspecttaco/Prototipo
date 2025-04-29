module uas.mtds.prototipo {
    requires javafx.controls;
    requires javafx.fxml;


    opens uas.mtds.prototipo to javafx.fxml;
    exports uas.mtds.prototipo;
}