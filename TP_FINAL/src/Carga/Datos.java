package Carga;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import Modelo.Linea;
import Modelo.Parada;
import net.datastructures.LinkedQueue;
import Modelo.Pasajero;

public class Datos {

    /**
     * Lee parada.txt y arma un mapa id -> Parada.
     * Formato de cada línea: id;dirección;
     */
    public static TreeMap<Integer, Parada> cargarParadas(InputStream input) {

        TreeMap<Integer, Parada> paradas = new TreeMap<>();

        try (Scanner sc = new Scanner(input)) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (linea.isBlank() || linea.startsWith("#"))
                    continue;

                String[] partes = linea.split(";");
                int id = Integer.parseInt(partes[0].trim());
                String direccion = partes[1].trim();

                // "nombre" lo dejamos igual que "direccion", como decidimos
                Parada parada = new Parada(id, direccion, direccion, new LinkedQueue<Pasajero>());
                paradas.put(id, parada);
            }
        }
        return paradas;
    }

    /**
     * Lee linea.txt y arma un mapa código -> Linea (ida y vuelta juntas).
     * Formato de cada línea: L1I;88;97;44;...  o  L1R;89;84;83;...
     */
    public static TreeMap<String, Linea> cargarLineas(InputStream input, TreeMap<Integer, Parada> paradas) {

        TreeMap<String, Linea> lineas = new TreeMap<>();

        try (Scanner sc = new Scanner(input)) {
            while (sc.hasNextLine()) {
                String renglon = sc.nextLine();
                if (renglon.isBlank() || renglon.startsWith("#"))
                    continue;

                String[] partes = renglon.split(";");
                String codigoCompleto = partes[0].trim();        // ej "L1I"
                String codigoBase = codigoCompleto.substring(0, codigoCompleto.length() - 1); // "L1"
                boolean esIda = codigoCompleto.endsWith("I");

                List<Parada> recorrido = new ArrayList<>();
                for (int i = 1; i < partes.length; i++) {
                    String idStr = partes[i].trim();
                    if (!idStr.isEmpty()) {
                        int idParada = Integer.parseInt(idStr);
                        recorrido.add(paradas.get(idParada));
                    }
                }

                Linea lineaObj = lineas.get(codigoBase);
                if (lineaObj == null) {
                    lineaObj = new Linea(codigoBase, new ArrayList<Parada>(), new ArrayList<Parada>());
                    lineas.put(codigoBase, lineaObj);
                }

                if (esIda)
                    lineaObj.getParadaIda().addAll(recorrido);
                else
                    lineaObj.getParadaVuelta().addAll(recorrido);
            }
        }
        return lineas;
    }
}