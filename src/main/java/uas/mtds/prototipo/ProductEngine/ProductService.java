package uas.mtds.prototipo.ProductEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    // Ejemplo con datos estáticos
    public List<Product> obtenerProductosEjemplo() {
        List<Product> productos = new ArrayList<>();

        productos.add(new Product("P001", "Café Americano", 30.00, "/uas/mtds/prototipo/products/americano.png", "Café negro tradicional"));
        productos.add(new Product("P002", "Capuchino", 42.00, "/uas/mtds/prototipo/products/capuchino.png", "Café con espuma de leche"));
        productos.add(new Product("P003", "Muffin", 20.00, "/uas/mtds/prototipo/images/muffin.png", "Panecillo dulce horneado"));
        productos.add(new Product("P004", "Sandwich", 45.00, "/uas/mtds/prototipo/images/sandwich.png", "Sándwich fresco"));
        productos.add(new Product("P005", "Té Verde", 18.00, "/uas/mtds/prototipo/images/te_verde.png", "Té verde natural"));
        productos.add(new Product("P006", "Pastel", 30.00, "/uas/mtds/prototipo/images/pastel.png", "Rebanada de pastel"));
        productos.add(new Product("P007", "Galletas", 15.00, "/uas/mtds/prototipo/images/galletas.png", "Galletas horneadas"));
        productos.add(new Product("P008", "Jugo", 22.00, "/uas/mtds/prototipo/images/jugo.png", "Jugo natural"));
        productos.add(new Product("P009", "Refresco", 18.00, "/uas/mtds/prototipo/images/refresco.png", "Bebida carbonatada"));

        return productos;
    }

    // Ejemplo de método para cargar desde base de datos (esquema)
    public List<Product> cargarProductosDesdeBaseDeDatos() {
        List<Product> productos = new ArrayList<>();

        // Definir la conexión
        String url = "jdbc:mysql://localhost:3306/cafeteria";
        String usuario = "root";
        String password = "123456789";

        try (Connection conn = DriverManager.getConnection(url, usuario, password)) {
            String sql = "SELECT ProductoId, nombre, precio, temperatura, imagen_ruta FROM producto";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("ProductoId");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    //String descripcion = rs.getString("descripcion") != null ? rs.getString("descripcion") : "";
                    String temperatura = rs.getString("temperatura");
                    //String TamanoId = rs.getString("TamanoId");
                    //String SaborId = rs.getString("SaborId");
                    //String fechaMod = rs.getString("fecha_mod");
                    String rutaImagen = rs.getString("imagen_ruta");

                    //productos.add(new Product(id, nombre, precio,descripcion, rutaImagen));
                    //productos.add(new Product());

                    productos.add(new Product(id,nombre, precio,temperatura,rutaImagen));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            e.printStackTrace();

            // Si hay error, retornar algunos productos por defecto
            return obtenerProductosEjemplo();
        }

        return productos;
    }
}