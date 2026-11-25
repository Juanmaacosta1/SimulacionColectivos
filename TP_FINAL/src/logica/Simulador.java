package logica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.Configuracion;
import modelo.Colectivo;
import modelo.Parada;
import modelo.Pasajero;
import presentacion.SimulacionOutput;
import util.AdministracionPasajeros;
import util.EstadisticasSimulacion;

public class Simulador {
    private List<Colectivo> colectivos;
    private SimulacionOutput vista;
    private final int capacidad = Configuracion.getCapacidadColectivo();
    private final int maxVueltas = Configuracion.getMaxVueltas();
    private final Map<Colectivo, Integer> posiciones = new HashMap<>();
    private final Map<Colectivo, Integer> vueltas = new HashMap<>();

    public Simulador(List<Colectivo> colectivos, SimulacionOutput vista) {
        this.colectivos = colectivos;
        this.vista = vista;

        for (Colectivo colectivo : colectivos) {
            posiciones.put(colectivo, 0);
            vueltas.put(colectivo, 0);
        }
    }

    public void ejecutar() {
        int turno = 1;
        boolean hayColectivosEnCirculacion;

        do {
            vista.mostrarInicioParada(turno);
            hayColectivosEnCirculacion = false;

            for (Colectivo colectivo : colectivos) {
                if (procesarColectivoEnParada(colectivo)) {
                    hayColectivosEnCirculacion = true;
                }
            }
            turno++;
        } while (hayColectivosEnCirculacion);

        EstadisticasSimulacion.mostrarEstadisticasFinales(
                colectivos, AdministracionPasajeros.getTodosLosPasajeros(), capacidad, vista);
    }

    private boolean procesarColectivoEnParada(Colectivo colectivo) {
        int posicionActual = posiciones.get(colectivo);
        List<Parada> paradas = colectivo.getLinea().getParadas();

        if (vueltas.get(colectivo) < maxVueltas) {
            if (posicionActual < paradas.size()) {
                Parada parada = paradas.get(posicionActual);
                procesarParada(colectivo, parada, posicionActual);
                return true;
            } else {
                return procesarFinDeRecorrido(colectivo);
            }
        }
        return false;
    }

    private void procesarParada(Colectivo colectivo, Parada parada, int posicionActual) {
        vista.mostrarLlegadaColectivo(colectivo, parada);

        List<Pasajero> bajaron = colectivo.BajarPasajeroEn(parada);
        List<Pasajero> subieron = colectivo.SubirPasajerosEn(parada, posicionActual, capacidad);

        vista.mostrarEventosPasajeros(bajaron, subieron);
        vista.mostrarEstadoColectivo(colectivo, bajaron.size(), subieron.size());

        // la última parada no inicia ningún tramo (N paradas = N-1 tramos)
        if (posicionActual < colectivo.getLinea().getParadas().size() - 1) {
            colectivo.registrarOcupacion();
        }

        if (colectivo.getCantidadPasajeros() == capacidad) {
            mostrarAdvertenciaColectivoLleno(colectivo, parada, posicionActual);
        }

        posiciones.put(colectivo, posicionActual + 1);
    }

    private void mostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada parada, int posicionActual) {
        int esperando = 0;
        for (Pasajero pasajero : parada.getPasajerosEsperando()) {
            if (colectivo.PuedeSubir(pasajero, parada, posicionActual)) {
                esperando++;
            }
        }
        vista.mostrarAdvertenciaColectivoLleno(colectivo, parada, esperando);
    }

    private boolean procesarFinDeRecorrido(Colectivo colectivo) {
        vista.mostrarFinRecorrido(colectivo);

        int vueltasActuales = vueltas.get(colectivo) + 1;
        vueltas.put(colectivo, vueltasActuales);

        boolean sigueEnCirculacion = vueltasActuales < maxVueltas;
        if (sigueEnCirculacion) {
            posiciones.put(colectivo, 0);
        }
        return sigueEnCirculacion;
    }
}