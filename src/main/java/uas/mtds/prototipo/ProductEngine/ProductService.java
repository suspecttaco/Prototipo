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

        productos.add(new Product("P001", "Café Americano", "/uas/mtds/prototipo/images/cafe_americano.png", 25.00, 100, "Bebidas"));
        productos.add(new Product("P002", "Capuchino", "/uas/mtds/prototipo/images/capuchino.png", 35.00, 100, "Bebidas"));
        productos.add(new Product("P003", "Muffin", "/uas/mtds/prototipo/images/muffin.png", 20.00, 50, "Postres"));
        productos.add(new Product("P004", "Sandwich", "/uas/mtds/prototipo/images/sandwich.png", 45.00, 30, "Alimentos"));
        productos.add(new Product("P005", "Té Verde", "/uas/mtds/prototipo/images/te_verde.png", 18.00, 80, "Bebidas"));
        productos.add(new Product("P006", "Pastel", "/uas/mtds/prototipo/images/pastel.png", 30.00, 20, "Postres"));
        productos.add(new Product("P007", "Galletas", "/uas/mtds/prototipo/images/galletas.png", 15.00, 60, "Postres"));
        productos.add(new Product("P008", "Jugo", "/uas/mtds/prototipo/images/jugo.png", 22.00, 40, "Bebidas"));
        productos.add(new Product("P009", "Refresco", "/uas/mtds/prototipo/images/refresco.png", 18.00, 100, "Bebidas"));

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
            String sql = "SELECT id, nombre, imagen_ruta, precio, stock, categoria FROM productos";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    String rutaImagen = rs.getString("imagen_ruta");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");
                    String categoria = rs.getString("categoria");

                    productos.add(new Product(id, nombre, rutaImagen, precio, stock, categoria));
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