package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import config.Configuracion;
import modelo.Linea;
import modelo.Parada;
import modelo.Pasajero;

public class AdministracionPasajeros{
        private static Random random = new Random();
        private static final List<Pasajero> todosLosPasajeros = new ArrayList<>();
        private static final int MAX_PASAJERO_POR_PARADA = Configuracion.getMaxPasajerosPorParada();

        public static void generarPasajerosRANDOM(List<Linea> lineas){
            int idPasajero = 1;
            for (Linea linea : lineas) {
            idPasajero = generarPasajerosParaLinea(linea, idPasajero);
            }
        }




   private static int generarPasajerosParaLinea(Linea linea, int idInicial) {
    List<Parada> paradas = linea.getParadas();
    int idPasajero = idInicial;
    int cantidadParadas = paradas.size() - 1;

    for (int i = 0; i < cantidadParadas; i++) {
        Parada origen = paradas.get(i);
        int cantidad = random.nextInt(MAX_PASAJERO_POR_PARADA) + 1;

        for (int j = 0; j < cantidad; j++) {
            Parada destino = elegirDestinoAleatorio(paradas, i);
            Pasajero pasajero = new Pasajero(idPasajero++, destino);
            origen.agregarPasajero(pasajero);
            todosLosPasajeros.add(pasajero);
        }
    }
    return idPasajero;
}


private static Parada elegirDestinoAleatorio(List<Parada> paradas, int indiceOrigen) {
    int indiceDestino = indiceOrigen + 1 + random.nextInt(paradas.size() - indiceOrigen - 1);
    return paradas.get(indiceDestino);
}
       

    public static List<Pasajero> getTodosLosPasajeros() {
        return todosLosPasajeros;
    }
}