package uas.mtds.prototipo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogController {
    @FXML
    private TextField textUser;
    @FXML
    private PasswordField textPass;

    @FXML
    private void actionLogin (ActionEvent event) throws IOException {
        if (textUser.getText().equals("admin") && textPass.getText().equals("12345")) {
            Stage root = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.close();

            FXMLLoader fxmlLoader = new FXMLLoader(PosApplication.class.getResource("1-POS.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Punto de venta");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } else {
            System.out.println("Usuario no autenticado");
        }
    }
}
