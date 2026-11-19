package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Linea;
import modelo.Parada;
import presentacion.SimulacionOutput;

public class Datos{
    private final String archivoParadas;
    private final String archivoLineas;
    private static final String SEPARADOR = ";";
    private static final String COMENTARIO = "#";
    private final SimulacionOutput vista;

    public Datos() {
        this(config.Configuracion.getArchivoParadas(),
             config.Configuracion.getArchivoLineas(),
             new SimulacionOutput());
    }

    public Datos(String archivoParadas, String archivoLineas, SimulacionOutput vista) {
        this.archivoParadas = archivoParadas;
        this.archivoLineas = archivoLineas;
        this.vista = vista;
    }

public Map<Integer, Parada> cargarParadas(){
    Map<Integer, Parada> paradas = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(archivoParadas))) {
        String lineaTexto;
        while ( (lineaTexto = br.readLine()) != null){
            Parada parada = parsearParada(lineaTexto);
            if (parada != null) {
                paradas.put(parada.getId(), parada);
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("Error al cargar las paradas: " + e.getMessage());
    }
    return paradas;
}

public Map<Integer, Parada> cargarParadas(String archivo) {
    if (archivo.equals(archivoParadas)) {
        return cargarParadas();
    }

    Map<Integer, Parada> paradas = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String lineaTexto;
        while ((lineaTexto = br.readLine()) != null) {
            Parada parada = parsearParada(lineaTexto);
            if (parada != null) {
                paradas.put(parada.getId(), parada);
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("Error al cargar las paradas: " + e.getMessage());
    }
    return paradas;
}


public List<Linea> cargarLineas(String archivo, Map<Integer, Parada> paradas) {
    List<Linea> lineas = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String lineaTexto;
        while ((lineaTexto = br.readLine()) != null) {
            Linea linea = parsearLinea(lineaTexto, paradas);
            if (linea != null) {
                lineas.add(linea);
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("Error al cargar las lineas: " + e.getMessage());
    }

    return lineas;
}

private Linea parsearLinea(String lineaTexto, Map<Integer, Parada> paradas) {
    if (lineaTexto.trim().isEmpty()) {
        return null;
    } else if (lineaTexto.startsWith(COMENTARIO)) {
        return null;
    }

    String[] partes = lineaTexto.split(SEPARADOR);
    if (partes.length < 2) {
        return null; // Línea inválida
    }

    String codigo = partes[0].trim();
    Linea linea = new Linea(codigo);

    for (int i = 1; i < partes.length; i++) {
        String idParadaStr = partes[i].trim();
        try {
            int idParada = Integer.parseInt(idParadaStr);
            Parada parada = paradas.get(idParada);
            if (parada != null) {
                linea.agregarParada(parada);
            } else {
                System.err.println("La parada con ID " + idParada + " no existe.");
            }
        } catch (NumberFormatException e) {
            System.err.println("ID de parada inválido: " + idParadaStr);
        }
    }

    return linea;
}

    private Parada parsearParada(String lineaTexto) {
        if (lineaTexto.trim().isEmpty() || lineaTexto.startsWith(COMENTARIO)) {
            return null;
        }

        String[] partes = lineaTexto.split(SEPARADOR);
        if (partes.length < 2) {
            return null;
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            return new Parada(id, partes[1].trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}