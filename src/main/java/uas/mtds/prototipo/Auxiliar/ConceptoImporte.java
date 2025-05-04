package uas.mtds.prototipo.Auxiliar;

public class ConceptoImporte {
    private final String concepto;
    private final double importe;

    public ConceptoImporte(String concepto, double importe) {
        this.concepto = concepto;
        this.importe = importe;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getImporte() {
        return importe;
    }
}