package uas.mtds.prototipo.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import uas.mtds.prototipo.ProductEngine.Product;

import java.util.List;

@SuppressWarnings("ClassEscapesDefinedScope")
public class EditController {
    @FXML
    private ComboBox<Product> comboBoxProductos;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private TextArea textAreaNotas;
    @FXML
    private Button buttonGuardar;

    public void setPedidoProductos(List<Product> pedidoProductos) {

        // Configurar ComboBox con los productos
        comboBoxProductos.setItems(FXCollections.observableArrayList(pedidoProductos));
        comboBoxProductos.setConverter(new StringConverter<>() {
            @Override
            public String toString(Product product) {
                return product.getNombre();
            }

            @Override
            public Product fromString(String string) {
                return null; // No se necesita
            }
        });

        // Listener para actualizar los controles al cambiar el producto
        comboBoxProductos.getSelectionModel().selectedItemProperty().addListener((_, _, newProduct) -> {
            if (newProduct != null) {
                spinnerCantidad.getValueFactory().setValue(newProduct.getUnidad());
                textAreaNotas.setText(newProduct.getNotas());
            }
        });

        // Seleccionar el primer producto por defecto
        if (!pedidoProductos.isEmpty()) {
            comboBoxProductos.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void initialize() {
        // Configurar Spinner para cantidades
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinnerCantidad.setValueFactory(valueFactory);

        // Configurar acción del botón Guardar
        buttonGuardar.setOnAction(_ -> guardarCambios());
    }

    private void guardarCambios() {
        Product productoSeleccionado = comboBoxProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productoSeleccionado.setUnidad(spinnerCantidad.getValue());
            productoSeleccionado.setNotas(textAreaNotas.getText());

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Cambios guardados");
            alerta.setHeaderText(null);
            alerta.setContentText("Los cambios para el producto \"" + productoSeleccionado.getNombre() + "\" se han guardado.");
            alerta.showAndWait();
        }
    }
}