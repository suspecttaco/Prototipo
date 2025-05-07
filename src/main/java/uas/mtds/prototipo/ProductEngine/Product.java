package uas.mtds.prototipo.ProductEngine;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.Objects;

public class Product {
    private final String id;
    private final String nombre;
    private final double precio;
    private String descripcion;
    private final String temperatura;
    //private String TamanoId;
   // private String SaborId;
    //private String fechaMod;
    private Image imagen;

    private int unidad;
    private String notas;

    // Constructor completo
    public Product(String id, String nombre, double precio,String temperatura, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        //this.descripcion = descripcion;
        this.temperatura = temperatura;
        //this.TamanoId = TamanoId;
        //this.SaborId = SaborId;
        //this.fechaMod = fechaMod;
        try {
            InputStream stream = getClass().getResourceAsStream(rutaImagen);
            // Imagen por defecto si no se encuentra la ruta
            this.imagen = new Image(Objects.requireNonNullElseGet(stream, () -> Objects.requireNonNull(getClass().getResourceAsStream("/uas/mtds/prototipo/products/product_default.png"))));
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + rutaImagen + " - " + e.getMessage());
            // Imagen por defecto en caso de error
            this.imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uas/mtds/prototipo/products/product_default.png")));
        }
        //this.imagen = new Image(rutaImagen);
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {return descripcion;}

    public String getTemperatura() {return temperatura;}

    public Image getImagen() {
        return imagen;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public void addUnidad(int unidad) {
        this.unidad += unidad;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getNotas() {
        return notas;
    }
}