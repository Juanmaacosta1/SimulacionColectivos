package presentacion;

import java.util.List;
import modelo.Colectivo;
import modelo.Parada;
import modelo.Pasajero;

public class SimulacionOutput {

    public void mostrarInicioRecorrido(Colectivo colectivo) {
        System.out.println();
        System.out.println("=== Colectivo " + colectivo.getId() + " - Línea " + colectivo.getLinea().getCodigo() + " ===");
    }

    public void mostrarEstadoParada(Colectivo colectivo, Parada parada, List<Pasajero> subieron, List<Pasajero> bajaron) {
        System.out.println("Parada: " + parada.getDireccion());

        for (Pasajero p : bajaron) {
            System.out.println("  Bajó pasajero " + p.getId());
        }

        for (Pasajero p : subieron) {
            System.out.println("  Subió pasajero " + p.getId());
        }

        System.out.println("  A bordo: " + colectivo.getCantidadPasajeros());
    }

    public void mostrarFinRecorrido(Colectivo colectivo) {
        System.out.println("Colectivo " + colectivo.getId() + " terminó su recorrido");
    }
}