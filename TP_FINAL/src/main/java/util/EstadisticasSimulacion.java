package util;

import modelo.*;
import java.util.List;
import presentacion.SimulacionOutput;


public class EstadisticasSimulacion{
    public static void mostrarEstadisticasFinales(List<Colectivo> colectivos,
                                                  List<Pasajero> todos,
                                                  int maxCapacidad,
                                                  SimulacionOutput vista) {
        vista.mostrarFinSimulacion();
        vista.mostrarIndiceSatisfaccion(calcularIndiceSatisfaccion(todos));
        for (Colectivo c : colectivos) {
            vista.mostrarOcupacionPromedio(c, calcularOcupacionPromedio(c, maxCapacidad));
        }
    }

    // suma de calificaciones / (cantidad de pasajeros * 5)
    public static double calcularIndiceSatisfaccion(List<Pasajero> todos) {
        if (todos == null || todos.isEmpty()) return 0;
        int suma = 0;
        for (Pasajero p : todos) suma += p.getCalificacion();
        return (double) suma / (todos.size() * 5);
    }

    // promedio de (pasajeros en el tramo / capacidad)
    public static double calcularOcupacionPromedio(Colectivo c, int maxCapacidad) {
        List<Integer> tramos = c.getOcupacionPorTramo();
        if (tramos.isEmpty()) return 0;
        double suma = 0;
        for (int ocupacion : tramos) suma += (double) ocupacion / maxCapacidad;
        return suma / tramos.size();
    }
}

