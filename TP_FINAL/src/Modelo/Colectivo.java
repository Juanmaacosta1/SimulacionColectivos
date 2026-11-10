package Modelo;
import java.util.List;
import java.util.ArrayList;
public class Colectivo {
    
     private int id;
    private Linea linea;
    private boolean esIda;
    private List<Pasajero> pasajerosABordo;

    public Colectivo(int id, Linea linea, boolean esIda, List<Pasajero> pasajerosABordo) {
        this.id = id;
        this.linea = linea;
        this.esIda = esIda;
        this.pasajerosABordo = pasajerosABordo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }

    public boolean isEsIda() {
        return esIda;
    }

    public void setEsIda(boolean esIda) {
        this.esIda = esIda;
    }

    public List<Pasajero> getPasajerosABordo() {
        return pasajerosABordo;
    }

    public void setPasajerosABordo(List<Pasajero> pasajerosABordo) {
        this.pasajerosABordo = pasajerosABordo;
    }

    
}
