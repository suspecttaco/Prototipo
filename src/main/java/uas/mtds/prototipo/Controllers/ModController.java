package uas.mtds.prototipo.Controllers;

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

        // Configurar el Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinQty.setValueFactory(valueFactory);

        // Validar entrada manual para no permitir valores menores a 1
        spinQty.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinQty.getEditor().setText(oldValue);
            } else {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 1) {
                        spinQty.getEditor().setText("1");
                    }
                } catch (NumberFormatException e) {
                    spinQty.getEditor().setText("1");
                }
            }
        });

        // Agregar listeners para los TitledPane
        tabSize.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                tabFlav.setExpanded(false);
            }
        });

        tabFlav.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                tabSize.setExpanded(false);
            }
        });

        // Asegurarse de que el panel se expanda después de que la UI esté lista
        javafx.application.Platform.runLater(() -> {
            labelNom.setText(productoSeleccionado.getNombre());
            labelDesc.setText(productoSeleccionado.getDescripcion());
        });
    }

    public Product getProduct() {
        return productoSeleccionado;
    }

    public void setProduct(Product productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
}
