package uas.mtds.prototipo.Vending;

import uas.mtds.prototipo.ProductEngine.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {
    private double importe = 0.0;
    private final int UsuarioId;
    private final boolean pLlevar;
    private final Date fecha;
    private int CuponesId;
    private final int ClienteId;
    private final List<Product> carrito = new ArrayList<>();

    public Ticket(int UsuarioId, boolean pLlevar, Date fecha, int ClienteId) {
        this.UsuarioId = UsuarioId;
        this.pLlevar = pLlevar;
        this.fecha = fecha;
        this.ClienteId = ClienteId;
    }

    public double getImporte() {
        return importe;
    }

    public void addProduct(Product p) {
        carrito.add(p);
        importe += p.getPrecio();
    }

    public void removeProduct(Product p) {
        carrito.remove(p);
    }

    public List<Product> getCarrito() {
        return carrito;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public boolean getpLlevar() {
        return pLlevar;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getCuponesId() {
        return CuponesId;
    }

    public int getClienteId() {
        return ClienteId;
    }
}
