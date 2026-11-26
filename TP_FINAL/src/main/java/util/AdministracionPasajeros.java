package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import config.Configuracion;
import modelo.*;

public class AdministracionPasajeros{
    private static Random random = new Random();
    private static final List<Pasajero> todosLosPasajeros = new ArrayList<>();
    private static int MAX_PASAJEROS_POR_PARADAS = Configuracion.getMaxPasajerosPorParada();
    private static int proximoId = 1; //NUEVO

    public static void generarPasajerosAleatorios(List<Linea> lineas){
        todosLosPasajeros.clear(); //NUEVO
        proximoId = 1; //NUEVO
        for (Linea linea : lineas) {
            generarPasajerosParaLinea(linea); //MODIFICADO
        }
    }

    public static void generarPasajerosParaLinea(Linea linea) {
        List<Parada> paradas = linea.getParadas();
        int cantidadParadas = paradas.size() - 1;

        for (int i = 0; i < cantidadParadas; i++) {
            Parada origen = paradas.get(i);

            /*MODIFICADO*/
            int lugarDisponible = MAX_PASAJEROS_POR_PARADAS - origen.getPasajerosEsperando().size();
            
            if (lugarDisponible <= 0){
                continue;
            }
            /*----------*/

            int cantidad = random.nextInt(lugarDisponible) + 1;

            for (int j = 0; j < cantidad; j++) {
                Parada destino = elegirDestinoAleatorio(paradas, i);
                Pasajero pasajero = new Pasajero(proximoId++, destino); //MODIFICADO
                origen.agregarPasajero(pasajero);
                todosLosPasajeros.add(pasajero);
            }
        }
    }

    private static Parada elegirDestinoAleatorio(List<Parada> paradas, int indiceOrigen) {
        int indiceDestino = indiceOrigen + 1 + random.nextInt(paradas.size() - indiceOrigen - 1);
        return paradas.get(indiceDestino);
    }
       
    public static List<Pasajero> getTodosLosPasajeros() {
        return todosLosPasajeros;
    }
}