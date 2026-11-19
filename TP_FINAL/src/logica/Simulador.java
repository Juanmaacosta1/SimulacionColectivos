package logica;

import java.util.List;
import modelo.Colectivo;
import modelo.Parada;
import modelo.Pasajero;
import presentacion.SimulacionOutput;

public class Simulador {
    private List<Colectivo> colectivos;
    private SimulacionOutput vista;

    public Simulador(List<Colectivo> colectivos, SimulacionOutput vista) {
        this.colectivos = colectivos;
        this.vista = vista;
    }

    public void ejecutar() {
        for (Colectivo colectivo : colectivos) {
            vista.mostrarInicioRecorrido(colectivo);
            List<Parada> paradas = colectivo.getLinea().getParadas();

            for (int i = 0; i < paradas.size(); i++) {
                Parada parada = paradas.get(i);

                List<Pasajero> bajaron = colectivo.BajarPasajeroEn(parada);
                List<Pasajero> subieron = colectivo.SubirPasajerosEn(parada, i);

                vista.mostrarEstadoParada(colectivo, parada, subieron, bajaron);
            }

            vista.mostrarFinRecorrido(colectivo);
        }
    }
}