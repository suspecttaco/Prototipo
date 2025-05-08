package uas.mtds.prototipo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import uas.mtds.prototipo.ProductEngine.Product;

@SuppressWarnings("ClassEscapesDefinedScope")
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

        // Asegurarse de que el panel se expanda después de que la UI esté lista
        javafx.application.Platform.runLater(() -> {
            labelNom.setText(productoSeleccionado.getNombre());
            labelDesc.setText(productoSeleccionado.getDescripcion());
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
