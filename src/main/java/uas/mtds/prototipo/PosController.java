package uas.mtds.prototipo;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PosController {
    private Stage owner;
    @FXML
    private Label labelHora;
    @FXML
    private Label labelFecha;

    public void initialize() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                labelHora.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
                labelFecha.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE - dd / LL / yyyy")));
            }
        };
        timer.start();
        // Ejecutar después de que la interfaz se haya inicializado
        javafx.application.Platform.runLater(this::setupCloseHandler);
    }

    /**
     * Configura el manejador del evento de cierre de ventana
     */
    private void setupCloseHandler() {
        // Obtener la ventana actual
        Stage currentStage = (Stage) Stage.getWindows().stream()
                .filter(window -> window instanceof Stage && window.isShowing())
                .findFirst()
                .orElse(null);

        if (currentStage != null) {
            // Agregar el manejador de evento de cierre
            currentStage.setOnCloseRequest(this::handleCloseRequest);
        }
    }

    /**
     * Maneja el evento de cierre de ventana
     * @param event El evento de cierre de ventana
     */
    private void handleCloseRequest(WindowEvent event) {
        // Consumir el evento para prevenir el cierre automático
        event.consume();

        // Reutilizar la función de cierre de sesión
        try {
            actionSalir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void actionCobrar(ActionEvent event) throws IOException {
        // Obtener el Stage principal desde el evento
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Crear el nuevo Stage
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("2-PAGO.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("PAGO");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner); // Establecer el propietario
        stage.show();
    }

    @FXML
    public void actionCancelar(ActionEvent event) throws IOException {
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("3-CANCELAR.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Autorizacion");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionClientes(ActionEvent event) throws IOException{
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("4-CLIENTES.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Gestion de clientes");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionCupon(ActionEvent event) throws IOException{
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("5-CUPONES.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Descuento");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionBuscarProductos(ActionEvent event) throws IOException{
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("8-BUSCAR.fxml"))));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Inventario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionEditarPedido(ActionEvent event) throws IOException{
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("6-EDITARV.fxml")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Inventario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionCorte() throws IOException{
        Stage currentStage = (Stage) Stage.getWindows().stream()
                .filter(window -> window instanceof Stage && window.isShowing())
                .findFirst()
                .orElse(null);

        if (currentStage != null) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("9-CORTE.fxml")));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Finalizar Jornada");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(currentStage);
            stage.show();
        }
    }


    @FXML
    public void actionEliminarPedido(ActionEvent event) throws IOException{
        actionCancelar(event);
    }

    @FXML
    public void actionSalir() throws IOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar sesión");
        alerta.setContentText("¿Está seguro que desea cerrar la sesión?");
        alerta.setHeaderText(null);

        if (alerta.showAndWait().orElse(null) == ButtonType.OK) {
            // Buscar directamente la ventana activa principal
            Stage currentStage = (Stage) Stage.getWindows().stream()
                    .filter(window -> window instanceof Stage && window.isShowing())
                    .findFirst()
                    .orElse(null);

            if (currentStage != null) {
                // Cerrar la ventana actual
                currentStage.close();

                // Crear una nueva instancia de la ventana de login
                Stage loginStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("7-LOGIN.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                loginStage.setTitle("Login");
                loginStage.setScene(scene);
                loginStage.setResizable(false);
                loginStage.show();
            }
        }
    }
}