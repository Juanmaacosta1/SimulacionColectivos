package Modelo;
import java.util.ArrayList;
import java.util.List;

public class Linea {
    private String codigo;
    private List<Parada> paradaIda;
    private List<Parada> paradaVuelta;

    public Linea(String codigo, List<Parada> paradaIda, List<Parada> paradaVuelta) {
        this.codigo = codigo;
        this.paradaIda = paradaIda;
        this.paradaVuelta = paradaVuelta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Parada> getParadaIda() {
        return paradaIda;
    }

    public void setParadaIda(List<Parada> paradaIda) {
        this.paradaIda = paradaIda;
    }

    public List<Parada> getParadaVuelta() {
        return paradaVuelta;
    }

    public void setParadaVuelta(List<Parada> paradaVuelta) {
        this.paradaVuelta = paradaVuelta;
    }


    
}
