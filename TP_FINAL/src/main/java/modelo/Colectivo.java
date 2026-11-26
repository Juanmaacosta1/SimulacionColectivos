package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import config.Configuracion;

public class Colectivo {
    private int id;
    private Linea linea;
    private List<Pasajero> pasajeros = new ArrayList<>();
    private final List<Integer> ocupacionPorTramo = new ArrayList<>();
    private int capacidad;

    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
    }

//metodos
public List<Pasajero> subirPasajerosEn(Parada parada, int posicionActual, int maxCapacidad) {
    int espacioDisponible = maxCapacidad - pasajeros.size();

    List<Pasajero> subieron = new ArrayList<>();
    Iterator<Pasajero> iterator = parada.getPasajerosEsperando().iterator();

    while (iterator.hasNext() && subieron.size() < espacioDisponible) {
        Pasajero pasajero = iterator.next();

        if (puedeSubir(pasajero, parada, posicionActual)) {
            pasajeros.add(pasajero);
            subieron.add(pasajero);
            iterator.remove();
            pasajero.califiacionAlsubir(pasajeros.size() - 1, Configuracion.getCantidadAsientos());
        }
    }
    marcarPasajerosQueNoSubieron(parada, posicionActual);
    return subieron;
}

private void marcarPasajerosQueNoSubieron(Parada parada, int posicionActual) {
    for (Pasajero pasajero : parada.getPasajerosEsperando()) {
        if (puedeSubir(pasajero, parada, posicionActual)) {
            pasajero.perderColectivo();
        }
    }
}

public void registrarOcupacion() {
    ocupacionPorTramo.add(pasajeros.size());
}

public List<Integer> getOcupacionPorTramo() {
    return ocupacionPorTramo;
}

public boolean puedeSubir(Pasajero pasajero, Parada parada, int posicionActual) {
    return destinoEstaEnRecorridoFuturo(pasajero.getDestino(), posicionActual);
}

public boolean destinoEstaEnRecorridoFuturo(Parada destino, int posicionActual) {
    Integer indiceDestino = linea.getMapaIndicesParadas().get(destino);
    return indiceDestino != null && indiceDestino > posicionActual;
}

public List<Pasajero> bajarPasajeroEn(Parada parada) {
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