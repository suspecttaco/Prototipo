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

        productos.add(new Product("P001", "Café Americano",  25.00,"/uas/mtds/prototipo/images/cafe_americano.png"));
        productos.add(new Product("P002", "Capuchino",  35.00, "/uas/mtds/prototipo/images/capuchino.png"));
        productos.add(new Product("P003", "Muffin",  20.00, "/uas/mtds/prototipo/images/muffin.png"));
        productos.add(new Product("P004", "Sandwich",  45.00, "/uas/mtds/prototipo/images/sandwich.png"));
        productos.add(new Product("P005", "Té Verde",  18.00,"/uas/mtds/prototipo/images/te_verde.png"));
        productos.add(new Product("P006", "Pastel",  30.00, "/uas/mtds/prototipo/images/pastel.png"));
        productos.add(new Product("P007", "Galletas",  15.00,"/uas/mtds/prototipo/images/galletas.png"));
        productos.add(new Product("P008", "Jugo",  22.00,"/uas/mtds/prototipo/images/jugo.png"));
        productos.add(new Product("P009", "Refresco",  18.00,"/uas/mtds/prototipo/images/refresco.png"));

        return productos;
    }

    // Ejemplo de método para cargar desde base de datos (esquema)
    public List<Product> cargarProductosDesdeBaseDeDatos() {
        List<Product> productos = new ArrayList<>();

        // Definir la conexión
        String url = "jdbc:mysql://localhost:3306/cafeteria";
        String usuario = "usuario";
        String password = "contraseña";

        try (Connection conn = DriverManager.getConnection(url, usuario, password)) {
            String sql = "SELECT id, nombre, precio,imagen_rutaFROM productos";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String rutaImagen = rs.getString("imagen_ruta");

                    productos.add(new Product(id, nombre, precio, rutaImagen));
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