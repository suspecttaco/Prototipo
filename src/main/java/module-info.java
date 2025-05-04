module uas.mtds.prototipo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires atlantafx.base;
    requires java.sql;
    requires com.fasterxml.jackson.databind;


    opens uas.mtds.prototipo to javafx.fxml;
    exports uas.mtds.prototipo;
}