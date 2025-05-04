package uas.mtds.prototipo;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uas.mtds.prototipo.ProductEngine.Product;
import uas.mtds.prototipo.ProductEngine.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class PosController {
    private Stage owner;
    @FXML
    private Label labelHora;
    @FXML
    private Label labelFecha;

    @FXML
    private ScrollPane scrollProductos;

    private TilePane gridProductos;
    private ObservableList<Product> listaProductos;

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
        Platform.runLater(this::setupCloseHandler);

        // Inicializar lista observable
        ProductService productoService = new ProductService();

        // Opción 1: Cargar productos de ejemplo
        // List<Producto> productos = productoService.obtenerProductosEjemplo();

        // Opción 2: Cargar productos desde la base de datos
        List<Product> productos = productoService.cargarProductosDesdeBaseDeDatos();

        // Actualizar la cuadrícula de productos


        listaProductos = FXCollections.observableArrayList();
        // Configurar vista en cuadrícula
        configurarVistaEnCuadricula();
        actualizarProductos(productos);
    }

    /**
     * Configura el manejador del evento de cierre de ventana
     */
    private void setupCloseHandler() {
        Stage currentStage = (Stage) Stage.getWindows().stream()
                .filter(window -> window instanceof Stage && window.isShowing())
                .findFirst()
                .orElse(null);

        if (currentStage != null) {
            currentStage.setOnCloseRequest(this::handleCloseRequest);
        }
    }

    /**
     * Maneja el evento de cierre de ventana
     *
     * @param event El evento de cierre de ventana
     */
    private void handleCloseRequest(WindowEvent event) {
        event.consume();

        try {
            actionSalir();
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Ocurrió un error inesperado");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    public void actionCobrar(ActionEvent event) throws IOException {
        owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("2-PAGO.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("PAGO");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
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
    public void actionClientes(ActionEvent event) throws IOException {
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
    public void actionCupon(ActionEvent event) throws IOException {
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
    public void actionBuscarProductos(ActionEvent event) throws IOException {
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
    public void actionEditarPedido(ActionEvent event) throws IOException {
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
    public void actionCorte() throws IOException {
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
    public void actionEliminarPedido(ActionEvent event) throws IOException {
        actionCancelar(event);
    }

    @FXML
    public void actionSalir() throws IOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cerrar sesión");
        alerta.setContentText("¿Está seguro que desea cerrar la sesión?");
        alerta.setHeaderText(null);

        if (alerta.showAndWait().orElse(null) == ButtonType.OK) {

            Stage currentStage = (Stage) Stage.getWindows().stream()
                    .filter(window -> window instanceof Stage && window.isShowing())
                    .findFirst()
                    .orElse(null);

            if (currentStage != null) {

                currentStage.close();

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

    private void configurarVistaEnCuadricula() {
        gridProductos = new TilePane();
        gridProductos.setPrefColumns(3); // Número de columnas
        gridProductos.setHgap(15);
        gridProductos.setVgap(15);
        gridProductos.setPadding(new Insets(10));
        gridProductos.setAlignment(Pos.CENTER);

        scrollProductos.setContent(gridProductos);
        scrollProductos.setFitToWidth(true);
        scrollProductos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProductos.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    /**
     * Método público para actualizar los productos desde otra clase
     * @param products Lista de productos para mostrar
     */
    public void actualizarProductos(List<Product> products) {
        // Limpiar lista actual
        listaProductos.clear();

        // Añadir nuevos productos
        listaProductos.addAll(products);

        // Actualizar la interfaz
        mostrarProductosEnGrid();
    }

    /**
     * Método para filtrar productos por categoría
     * @param categoria Categoría a filtrar, o null para mostrar todos
     */
    public void filtrarProductosPorCategoria(String categoria) {
        gridProductos.getChildren().clear();

        for (Product producto : listaProductos) {
            if (categoria == null || categoria.isEmpty() || producto.getCategoria().equals(categoria)) {
                gridProductos.getChildren().add(crearElementoProducto(producto));
            }
        }
    }

    /**
     * Método para buscar productos por nombre
     * @param termino Término de búsqueda
     */
    public void buscarProductos(String termino) {
        gridProductos.getChildren().clear();

        if (termino == null || termino.isEmpty()) {
            mostrarProductosEnGrid(); // Mostrar todos si no hay término
            return;
        }

        String terminoLower = termino.toLowerCase();

        for (Product producto : listaProductos) {
            if (producto.getNombre().toLowerCase().contains(terminoLower)) {
                gridProductos.getChildren().add(crearElementoProducto(producto));
            }
        }
    }

    /**
     * Método privado para actualizar la interfaz con los productos
     */
    private void mostrarProductosEnGrid() {
        gridProductos.getChildren().clear();

        for (Product producto : listaProductos) {
            gridProductos.getChildren().add(crearElementoProducto(producto));
        }
    }

    private VBox crearElementoProducto(Product producto) {
        // Crear VBox para contener la imagen y el nombre
        VBox elementoProducto = new VBox(5);
        elementoProducto.setAlignment(Pos.CENTER);
        elementoProducto.setPadding(new Insets(10));
        elementoProducto.setStyle("-fx-background-color: #f4f4f4; -fx-border-radius: 5; -fx-background-radius: 5;");
        elementoProducto.setPrefWidth(120);
        elementoProducto.setPrefHeight(150);

        // Configurar la imagen
        ImageView imageView = new ImageView(producto.getImagen());
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        // Configurar el texto
        Text nombreText = new Text(producto.getNombre());
        nombreText.setFont(Font.font("Roboto", 14));
        nombreText.setWrappingWidth(100);
        nombreText.setTextAlignment(TextAlignment.CENTER);

        // Añadir precio
        Text precioText = new Text(String.format("$%.2f", producto.getPrecio()));
        precioText.setFont(Font.font("Roboto", 12));

        // Añadir elementos al VBox
        elementoProducto.getChildren().addAll(imageView, nombreText, precioText);

        // Manejar evento de clic
        elementoProducto.setOnMouseClicked(event -> {
            System.out.println("Producto seleccionado: " + producto.getNombre());
            agregarProductoAlPedido(producto);
        });

        return elementoProducto;
    }

    private void agregarProductoAlPedido(Product producto) {
        // Implementar lógica para añadir al pedido
        // Esta es solo una implementación de ejemplo
        System.out.println("Agregando al pedido: " + producto.getNombre() + " - $" + producto.getPrecio());
    }
}