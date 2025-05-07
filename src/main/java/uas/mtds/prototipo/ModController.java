package uas.mtds.prototipo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uas.mtds.prototipo.ProductEngine.Product;

import javax.swing.text.html.ImageView;

public class ModController {
    @FXML
    private TitledPane tabSize;
    @FXML
    private TitledPane tabFlav;
    @FXML
    private ImageView imgProd;
    @FXML
    private Label labelNom;
    @FXML
    private Label labelDesc;
    @FXML
    private ToggleGroup radSize;
    @FXML
    private ToggleGroup radFlav;
    @FXML
    private Spinner<Integer> spinQty;
    @FXML
    private Button btnSave;

    private Product productoSeleccionado;

    @FXML
    public void initialize() {
        labelNom.setText(productoSeleccionado.getNombre());
        labelDesc.setText(productoSeleccionado.getDescripcion());

        // Asegurarse de que el panel se expanda después de que la UI esté lista
        javafx.application.Platform.runLater(() -> {
            tabSize.setExpanded(true);
        });
    }

    public Product getProduct() {
        return productoSeleccionado;
    }

    public void setProduct(Product productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
}
