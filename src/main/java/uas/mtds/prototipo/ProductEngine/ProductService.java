package uas.mtds.prototipo.ProductEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductService {
    // Ejemplo con datos estáticos
    public List<Product> obtenerProductosEjemplo() {
        List<Product> productos = new ArrayList<>();

        productos.add(new Product(1, "Café Americano", 30.00, "/uas/mtds/prototipo/products/americano.png", "Café negro tradicional"));
        productos.add(new Product(2, "Capuchino", 42.00, "/uas/mtds/prototipo/products/capuchino.png", "Café con espuma de leche"));
        productos.add(new Product(3, "Muffin", 20.00, "/uas/mtds/prototipo/images/muffin.png", "Panecillo dulce horneado"));
        productos.add(new Product(4, "Sandwich", 45.00, "/uas/mtds/prototipo/images/sandwich.png", "Sándwich fresco"));
        productos.add(new Product(5, "Té Verde", 18.00, "/uas/mtds/prototipo/images/te_verde.png", "Té verde natural"));
        productos.add(new Product(6, "Pastel", 30.00, "/uas/mtds/prototipo/images/pastel.png", "Rebanada de pastel"));
        productos.add(new Product(7, "Galletas", 15.00, "/uas/mtds/prototipo/images/galletas.png", "Galletas horneadas"));
        productos.add(new Product(8, "Jugo", 22.00, "/uas/mtds/prototipo/images/jugo.png", "Jugo natural"));
        productos.add(new Product(9, "Refresco", 18.00, "/uas/mtds/prototipo/images/refresco.png", "Bebida carbonatada"));

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
            String sql = "SELECT ProductoId, nombre, precio, descripcion, imagen_ruta FROM producto";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("ProductoId");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String descripcion = rs.getString("descripcion");
                    String rutaImagen = rs.getString("imagen_ruta");

                    productos.add(new Product(id, nombre, precio, rutaImagen, descripcion));
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(ProductService.class.getName());
            logger.log(Level.SEVERE, "Error al cargar productos: ", e);

            // Si hay error, retornar algunos productos por defecto
            return obtenerProductosEjemplo();
        }

        return productos;
    }
}