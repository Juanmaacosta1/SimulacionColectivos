package main;

import java.util.List;
import java.util.Map;
import config.Configuracion;
import io.Datos;
import logica.Simulador;
import modelo.*;
import presentacion.SimulacionOutput;
import util.*;

public class SimulacionColectivos {
    public static void main(String[] args) {
        SimulacionOutput vista = SimulacionOutput.crearConArchivo(Configuracion.getArchivoSalida());
        Datos datos = new Datos(Configuracion.getArchivoParadas(), Configuracion.getArchivoLineas(), vista);

        Map<Integer, Parada> paradas = datos.cargarParadas();
        List<Linea> lineas = datos.cargarLineas(Configuracion.getArchivoLineas(), paradas);

        AdministracionPasajeros.generarPasajerosAleatorios(lineas);
        List<Colectivo> colectivos = AdministracionColectivos.generarColectivos(lineas);

        Simulador simulador = new Simulador(colectivos, vista);
        simulador.ejecutar();

        vista.cerrarArchivo();
    }
}       