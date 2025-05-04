package uas.mtds.prototipo.ProductEngine;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.Objects;

public class Product {
    private final String id;
    private final String nombre;
    private Image imagen;
    private final double precio;
    private int stock;
    private final String categoria;

    // Constructor completo
    public Product(String id, String nombre, String rutaImagen, double precio, int stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        try {
            InputStream stream = getClass().getResourceAsStream(rutaImagen);
            if (stream != null) {
                this.imagen = new Image(stream);
            } else {
                // Imagen por defecto si no se encuentra la ruta
                this.imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uas/mtds/prototipo/products/product_default.png")));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + rutaImagen + " - " + e.getMessage());
            // Imagen por defecto en caso de error
            this.imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uas/mtds/prototipo/products/product_default.png")));
        }
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Constructor simplificado
    public Product(String id, String nombre, String rutaImagen, double precio) {
        this(id, nombre, rutaImagen, precio, 0, "General");
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getImagen() {
        return imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre;
    }
}