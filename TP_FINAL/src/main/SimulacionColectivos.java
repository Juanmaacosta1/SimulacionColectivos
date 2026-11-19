package main;

import java.util.List;
import java.util.Map;
import config.Configuracion;
import io.Datos;
import logica.Simulador;
import modelo.Colectivo;
import modelo.Linea;
import modelo.Parada;
import presentacion.SimulacionOutput;
import util.AdministracionColectivos;
import util.AdministracionPasajeros;

public class SimulacionColectivos {
    public static void main(String[] args) {
        Datos datos = new Datos();

        Map<Integer, Parada> paradas = datos.cargarParadas(Configuracion.getArchivoParadas());
        List<Linea> lineas = datos.cargarLineas(Configuracion.getArchivoLineas(), paradas);

        AdministracionPasajeros.generarPasajerosRANDOM(lineas);
        List<Colectivo> colectivos = AdministracionColectivos.generarColectivos(lineas);

        SimulacionOutput vista = new SimulacionOutput();
        Simulador simulador = new Simulador(colectivos, vista);
        simulador.ejecutar();
    }
}