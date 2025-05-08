package uas.mtds.prototipo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import uas.mtds.prototipo.ProductEngine.Product;

import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("ClassEscapesDefinedScope")
public class PayController {
    //TextFields
    @FXML
    private TextField textSubtotal;
    @FXML
    private TextField textIva;
    @FXML
    private TextField textTotal;
    //ToggleButtons
    @FXML
    private ToggleButton toggleTarjeta;
    @FXML
    private ToggleButton toggleEfectivo;
    @FXML
    private ToggleButton togglePaypal;
    @FXML
    private ToggleButton toggleTransferencia;
    //Table
    @FXML
    private TableView<Resumen> tableResumen;
    @FXML
    private TableColumn<Resumen, String> columnConcepto;
    @FXML
    private TableColumn<Resumen, String> columnMonto;

    private PosController posController;

    public void setPosController(PosController posController) {
        this.posController = posController;
    }

    public void cargarResumen(ObservableList<Product> pedidoProductos, @SuppressWarnings("ClassEscapesDefinedScope") ObservableList<ConceptoImporte> listaImportes) {
        // Configurar columnas de la tabla
        columnConcepto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().concepto()));
        columnMonto.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().monto())));

        // Crear una lista observable para los datos de la tabla
        ObservableList<Resumen> resumenData = FXCollections.observableArrayList();

        // Agregar los productos al resumen
        for (Product producto : pedidoProductos) {
            String concepto = producto.getNombre() + " × " + producto.getUnidad();
            double monto = Math.round(producto.getUnidad() * producto.getPrecio() * 100.0) / 100.0;
            resumenData.add(new Resumen(concepto, monto));
        }

        // Agregar los importes adicionales al resumen
        for (ConceptoImporte importe : listaImportes) {
            if (!importe.concepto().equals("Total Productos")) {
                double monto = Math.round(importe.importe() * 100.0) / 100.0;
                resumenData.add(new Resumen(importe.concepto(), monto));
            }
        }

        // Establecer los datos en la tabla
        tableResumen.setItems(resumenData);
        calcularTotales(tableResumen.getItems().stream().mapToDouble(Resumen::monto).sum());
    }

    public void calcularTotales(double total) {
        // Calcular subtotal e IVA
        double iva = total * 0.16;
        double subtotal = total - iva;

        // Mostrar los valores en los TextField
        textSubtotal.setText(String.format("$%.2f", subtotal));
        textIva.setText(String.format("$%.2f", iva));
        textTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    private void cobrarPedido() {
        // Obtener método de pago seleccionado
        String metodoPago = "";
        if (toggleTarjeta.isSelected()) {
            metodoPago = "Tarjeta";
        } else if (toggleEfectivo.isSelected()) {
            metodoPago = "Efectivo";
        } else if (togglePaypal.isSelected()) {
            metodoPago = "PayPal";
        } else if (toggleTransferencia.isSelected()) {
            metodoPago = "Transferencia";
        }

        if (metodoPago.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor seleccione un método de pago");
            alert.showAndWait();
            return;
        }

        // Generar contenido del ticket
        StringBuilder ticket = new StringBuilder();
        ticket.append("=== TICKET DE VENTA ===\n");
        ticket.append("Fecha: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        ticket.append("Método de pago: ").append(metodoPago).append("\n\n");

        // Agregar items del resumen
        for (Resumen item : tableResumen.getItems()) {
            ticket.append(String.format("%-30s $%.2f%n", item.concepto(), item.monto()));
        }

        ticket.append("\n");
        ticket.append("Subtotal: ").append(textSubtotal.getText()).append("\n");
        ticket.append("IVA: ").append(textIva.getText()).append("\n");
        ticket.append("Total: ").append(textTotal.getText()).append("\n");

        try {
            // Crear carpeta tickets si no existe
            Files.createDirectories(Paths.get("tickets"));

            // Generar nombre único para el ticket
            String fileName = "tickets/ticket_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";

            // Guardar ticket
            Files.writeString(Paths.get(fileName), ticket.toString());

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Ticket generado correctamente en: " + fileName);
            alert.showAndWait();

            // Limpiar el pedido en la ventana principal
            if (posController != null) {
                posController.limpiarPedido();
            }

            // Cerrar la ventana
            Stage stage = (Stage) textTotal.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al generar el ticket: " + e.getMessage());
            alert.showAndWait();
        }
    }
}

record Resumen(String concepto, double monto) {
}
