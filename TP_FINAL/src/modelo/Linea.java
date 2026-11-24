package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Linea {
    private String codigo;
    private List<Parada> paradas = new ArrayList<>();
    private final Map<Parada, Integer> mapaIndicesParadas;

    public Linea(String codigo) {
        this.codigo = codigo;
        this.paradas = new ArrayList<>();
        this.mapaIndicesParadas = new HashMap<>();
    }
    
    
    public String getCodigo() {
        return codigo;
    }
    public List<Parada> getParadas() {
        return paradas;
    }

 public void agregarParada(Parada parada) {
    if (!paradas.contains(parada)) {
        paradas.add(parada);
        actualizarMapaIndices();
    }
}

private void actualizarMapaIndices() {
    mapaIndicesParadas.clear();
    for (int i = 0; i < paradas.size(); i++) {
        mapaIndicesParadas.put(paradas.get(i), i);
    }
}


    public Map<Parada, Integer> getMapaIndicesParadas(){
        return mapaIndicesParadas;
    }

    public boolean paradaEsranEnposicionFutura (Parada parada , int posicionActual){
        Integer indiceParada = mapaIndicesParadas.get(parada);
        return indiceParada != null && indiceParada >posicionActual;
    }

    public int getIndiceParada(Parada parada) {
        return paradas.indexOf(parada);
    }
}