package uas.mtds.prototipo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class LogController {
    @FXML
    protected TextField textUser;
    @FXML
    protected PasswordField textPass;


    private final ObjectMapper mapper = new ObjectMapper();

    private boolean verificarCredenciales(String username, String password) {
        try {
            String CREDENTIALS_FILE = "data/credentials.json";
            File file = new File(CREDENTIALS_FILE);
            if (!file.exists()) {
                // Crear archivo con credenciales por defecto si no existe
                List<Credentials> defaultCreds = new ArrayList<>();
                defaultCreds.add(new Credentials("admin", "12345"));
                mapper.writeValue(file, defaultCreds);
            }

            List<Credentials> credentials = mapper.readValue(file,
                    new TypeReference<>() {
                    });

            return credentials.stream()
                    .anyMatch(c -> c.getUsername().equals(username)
                            && c.getPassword().equals(password));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void actionLogin(ActionEvent event) throws IOException {
        if (verificarCredenciales(textUser.getText(), textPass.getText())) {
            Stage root = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("1-POS.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Punto de venta");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de autenticación");
            alerta.setHeaderText("Usuario o contraseña incorrectos");
            alerta.setContentText("Por favor, verifica tus credenciales e \nintenta nuevamente.");
            alerta.showAndWait();
        }
    }
}
