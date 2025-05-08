module uas.mtds.prototipo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires atlantafx.base;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires mysql.connector.j;


    opens uas.mtds.prototipo to javafx.fxml;
    exports uas.mtds.prototipo;
    exports uas.mtds.prototipo.Controllers;
    opens uas.mtds.prototipo.Controllers to javafx.fxml;
}