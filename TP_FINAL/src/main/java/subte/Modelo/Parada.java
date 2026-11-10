package Modelo;
import net.datastructures.LinkedQueue;


public class Parada {
    private int id;
    private String nombre;
    private String direccion;
    private LinkedQueue<Pasajero> pasajeros;
    
    public Parada(int id, String nombre, String direccion, LinkedQueue<Pasajero> pasajeros) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.pasajeros = pasajeros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LinkedQueue<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(LinkedQueue<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

 
}
    