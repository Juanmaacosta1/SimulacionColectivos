package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
public class Colectivo {
    private int id;
    private Linea linea;
    private List<Pasajero> pasajeros = new ArrayList<>();
    private int capacidad;


    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
    }

//metodos
public List<Pasajero> SubirPasajerosEn(Parada parada, int posicionActual, int maxCapacidad) {
    int espacioDisponible = maxCapacidad - pasajeros.size();

    List<Pasajero> subieron = new ArrayList<>();
    Iterator<Pasajero> iterator = parada.getPasajerosEsperando().iterator();

    while (iterator.hasNext() && subieron.size() < espacioDisponible) {
        Pasajero pasajero = iterator.next();

        if (PuedeSubir(pasajero, parada, posicionActual)) {
            pasajeros.add(pasajero);
            subieron.add(pasajero);
            iterator.remove();
        }
    }
    return subieron;
}

public boolean PuedeSubir(Pasajero pasajero, Parada parada, int posicionActual) {
    return DestinoEstaEnRecorridoFuturo(pasajero.getDestino(), posicionActual);
}

public boolean DestinoEstaEnRecorridoFuturo(Parada destino, int posicionActual) {
    Integer indiceDestino = linea.getMapaIndicesParadas().get(destino);
    return indiceDestino != null && indiceDestino > posicionActual;
}

public List<Pasajero> BajarPasajeroEn(Parada parada) {
    List<Pasajero> bajan = new ArrayList<>();
    Iterator<Pasajero> iterator = pasajeros.iterator();

    while (iterator.hasNext()) {
        Pasajero pasajero = iterator.next();
        if (pasajero.getDestino().equals(parada)) {
            bajan.add(pasajero);
            iterator.remove();
        }
    }
    return bajan;
}

public int getId() {
        return id;
    }

    public Linea getLinea() {
        return linea;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public int getCantidadPasajeros() {
        return pasajeros.size();
    }

    
}       