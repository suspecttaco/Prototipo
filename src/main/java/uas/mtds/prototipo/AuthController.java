package uas.mtds.prototipo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AuthController {
    @FXML
    private TextField textfield_Usuario;
    @FXML
    private PasswordField passw_Usuario;
    @FXML
    private Button btn_Autorizar;
    @FXML
    private Button btn_Regresar;

    private final String CREDENTIALS_FILE = "data/credentials.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private PosController posController;

    public void initialize() {
        btn_Autorizar.setOnAction(event -> verificarAutorizacion());
        btn_Regresar.setOnAction(event -> cerrarVentana());

        Platform.runLater(() -> {
            Stage stage = (Stage) btn_Regresar.getScene().getWindow();
            // Previene el cierre por defecto
            stage.setOnCloseRequest(Event::consume);
        });
    }

    public void setPosController(PosController posController) {
        this.posController = posController;
    }

    private void verificarAutorizacion() {
        String username = textfield_Usuario.getText();
        String password = passw_Usuario.getText();

        if (verificarCredenciales(username, password)) {
            // Autorización exitosa
            if (posController != null) {
                posController.limpiarPedido();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Pedido cancelado correctamente");
            alert.showAndWait();

            cerrarVentana();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales inválidas");
            alert.showAndWait();
        }
    }

    private boolean verificarCredenciales(String username, String password) {
        try {
            File file = new File(CREDENTIALS_FILE);
            if (!file.exists()) {
                return false;
            }

            List<Credentials> credentials = mapper.readValue(file,
                new TypeReference<List<Credentials>>(){});

            return credentials.stream()
                .anyMatch(c -> c.getUsername().equals(username)
                    && c.getPassword().equals(password));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btn_Regresar.getScene().getWindow();
        stage.close();
    }
}
