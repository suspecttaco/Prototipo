package uas.mtds.prototipo;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.*;
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
    //Hora & Fecha
    @FXML
    private Label labelHora;
    @FXML
    private Label labelFecha;
    //Pedido
    @FXML
    private TableView<Product> tablePedido;
    @FXML
    private TableColumn<Product, String> columnProducto;
    @FXML
    private TableColumn<Product, String> columnCantidad;
    @FXML
    private TableColumn<Product, String> columnImporte;
    @FXML
    private TableColumn<Product, String> columnNotas;
    //Productos
    @FXML
    private ScrollPane scrollProductos;
    private TilePane gridProductos;
    private ObservableList<Product> listaProductos;
    //Tabla de monto total
    @FXML
    private TableView<ConceptoImporte> tableImporte;
    @FXML
    private TableColumn<ConceptoImporte, String> columnConcepto;
    @FXML
    private TableColumn<ConceptoImporte, String> columnMonto;
    @FXML
    private ToggleButton toggleParaLlevar;
    @FXML
    private TextField textTotal;

    private final ObservableList<ConceptoImporte> listaImportes = FXCollections.observableArrayList();
    private static final double PORCENTAJE_DESECHABLE = 0.10; // 10% adicional


    private final ObservableList<Product>  pedidoProductos = FXCollections.observableArrayList();

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


        // PARTE 1 - LISTA DE PRODUCTOS
        // Inicializar lista observable
        ProductService productoService = new ProductService();
        //Inicializar lista de productos
        List<Product> productos = productoService.cargarProductosDesdeBaseDeDatos();
        // Actualizar la cuadrícula de productos
        listaProductos = FXCollections.observableArrayList();
        // Configurar vista en cuadrícula
        configurarVistaEnCuadricula();
        actualizarProductos(productos);

        //PARTE 2 - TABLA DE PEDIDOS
        columnProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUnidad())));
        columnImporte.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%.2f", cellData.getValue().getPrecio() * cellData.getValue().getUnidad())));
        columnNotas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNotas()));
        // Enlazar el modelo de datos con el TableView
        tablePedido.setItems(pedidoProductos);

        //PARTE 3 - TABLA DE MONTOS TOTALES
        // Configurar columnas de la tabla
        columnConcepto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().concepto()));
        columnMonto.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%.2f", cellData.getValue().importe())));

        // Enlazar el modelo de datos con la tabla
        tableImporte.setItems(listaImportes);

        // Listener para el toggle
        toggleParaLlevar.selectedProperty().addListener((_, _, _) -> actualizarImportes());


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
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("2-PAGO.fxml"));
        Parent root = loader.load();

        PayController payController = loader.getController();
        payController.setPosController(this);
        payController.cargarResumen(pedidoProductos, listaImportes); // Pasar productos e importes

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("PAGO");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    @FXML
    public void actionCancelar(ActionEvent event) throws IOException {
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        Stage owner = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("6-EDITARV.fxml"));
        Parent root = loader.load();

        EditController editController = loader.getController();
        editController.setPedidoProductos(pedidoProductos);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Inventario");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);

        stage.setOnHidden(_ -> {
            tablePedido.refresh();
            actualizarImportes();
        });

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
    public void actionEliminarProducto() {
        // Obtener el producto seleccionado
        Product productoSeleccionado = tablePedido.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            // Eliminar el producto de la lista
            pedidoProductos.remove(productoSeleccionado);

            // Refrescar la tabla
            tablePedido.refresh();
            // Actualizar importes
            actualizarImportes();
            // Avisar al usuario
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Producto eliminado");
            alerta.setHeaderText(null);
            alerta.setContentText("El producto \"" + productoSeleccionado.getNombre() + "\" ha sido eliminado del pedido.");
            alerta.showAndWait();
        } else {
            // Mostrar alerta si no hay selección
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("No se ha seleccionado ningún producto");
            alerta.setContentText("Por favor, seleccione un producto para eliminar.");
            alerta.showAndWait();
        }
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
        elementoProducto.setOnMouseClicked(_ -> {
            System.out.println("Producto seleccionado: " + producto.getNombre());
            agregarProductoAlPedido(producto);
        });

        return elementoProducto;
    }

    private void agregarProductoAlPedido(Product producto) {
        // Buscar producto en la lista de productos
        for (Product p : pedidoProductos) {
            if (p.getId().equals(producto.getId())){
                p.addUnidad(1);
                tablePedido.refresh();
                actualizarImportes();
                return;
            }
        }

        // Por si no esta añadido
        producto.setUnidad(1);
        producto.setNotas("");
        pedidoProductos.add(producto);
        // Actualizar importes al inicializar
        actualizarImportes();
    }

    private void actualizarImportes() {
        listaImportes.clear();

        // Calcular el total de los productos
        double totalProductos = pedidoProductos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getUnidad())
                .sum();

        // Añadir el concepto de productos
        listaImportes.add(new ConceptoImporte("Total Productos", totalProductos));

        // Si el toggle está activado, añadir el costo adicional
        if (toggleParaLlevar.isSelected()) {
            double costoDesechable = totalProductos * PORCENTAJE_DESECHABLE;
            listaImportes.add(new ConceptoImporte("Costo Desechable", costoDesechable));
            totalProductos += costoDesechable;
        }


        // Actualizar el TextField con el total general
        textTotal.setText(String.format("$%.2f", totalProductos));
    }

    public void limpiarPedido() {
        // Limpiar la lista de productos
        pedidoProductos.clear();

        // Limpiar la lista de importes
        listaImportes.clear();

        // Refrescar las tablas
        tablePedido.refresh();
        tableImporte.refresh();

        // Reiniciar el total
        textTotal.setText("$0.00");

        // Desactivar el toggle de para llevar si está activo
        if (toggleParaLlevar.isSelected()) {
            toggleParaLlevar.setSelected(false);
        }
    }
}

record ConceptoImporte(String concepto, double importe) {
}