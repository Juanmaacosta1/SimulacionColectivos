package modelo;

import java.util.ArrayList;
import java.util.List;

public class Parada {
    private int id;
    private String direccion;
    private List<Pasajero> pasajerosEsperando = new ArrayList<>();


public Parada( int id, String direccion){
this.id = id;
this.direccion = direccion;
}

public int getId() {
    return id;
}

public String getDireccion() {
    return direccion;
}

public List<Pasajero> getPasajerosEsperando() {
    return pasajerosEsperando;
}



public void agregarPasajero(Pasajero pasajero){
    pasajerosEsperando.add(pasajero);
}


public void quitarPasajero(Pasajero pasajero){
    pasajerosEsperando.remove(pasajero);
}
}