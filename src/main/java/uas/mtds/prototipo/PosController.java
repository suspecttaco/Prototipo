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
                labelFecha.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE   dd / LLLL / yyyy")));
            }
        };
        timer.start();
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
    public void actionEliminarPedido(ActionEvent event) throws IOException{
        actionCancelar(event);
    }

    @FXML
    public void actionSalir(ActionEvent event) throws IOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText("Esta seguro que desea cerrar la sesion?");
        alerta.setHeaderText(null);
//        alerta.show();

        if (alerta.showAndWait().orElse(null) == ButtonType.OK) {
            if (event.getSource() instanceof Node) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("1-LOGIN.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.setResizable(false);
                stage.show();
            }
        }
    }
}