package util;

import java.util.ArrayList;
import java.util.List;
import modelo.Colectivo;
import modelo.Linea;

public class AdministracionColectivos{


     public static List<Colectivo> generarColectivos(List<Linea> lineas) {
        List<Colectivo> colectivos = new ArrayList<>();
        int id = 0;

        for (Linea linea : lineas) {
               Colectivo colectivo = new Colectivo(id++, linea);
            colectivos.add(colectivo);

        }

        return colectivos;
    }
}
