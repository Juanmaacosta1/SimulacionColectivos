package modelo;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    private String codigo;
    private List<Parada> paradas = new ArrayList<>();

    public Linea(String codigo) {
        this.codigo = codigo;
    }
    
    public Linea(String codigo, List<Parada> paradas) {
        this.codigo = codigo;
        this.paradas = paradas;
    }

    public String getCodigo() {
        return codigo;
    }
    public List<Parada> getParadas() {
        return paradas;
    }

    public void agregarParada(Parada parada) {
        paradas.add(parada);
    }

    public int getIndiceParada(Parada parada) {
        return paradas.indexOf(parada);
    }
}