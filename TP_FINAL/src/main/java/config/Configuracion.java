package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion {
    private static final Properties properties = new Properties();

    private static final String ARHIVO_CONFIGURACION = "data/config.properties";
    private static final String LINEA_KEY = "linea";
    private static final String PARADA_KEY = "parada";
    private static final String MAX_PASAJERO_POR_PARADA_KEY = "maxPasajerosPorParada";
    private static final String CAPACIDAD_KEY = "maxPasajerosColectivo";
    private static final String CANTIDAD_DE_ASIENTOS_KEY = "cantidadAsientos";
    private static final String MAX_VUELTAS_KEY = "maxVueltas";
    private static final String SALIDA_KEY = "archivoSalidaSimulacion";

    static {
        try (FileInputStream fis = new FileInputStream(ARHIVO_CONFIGURACION)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuración:( " + ARHIVO_CONFIGURACION);
        }
    }

    public static String getArchivoLineas() {
        String value = properties.getProperty(LINEA_KEY);
        if (value == null) {
            System.err.println("No se encontró la clave de configuración: " + LINEA_KEY);
        }
        return value;
    }

    public static String getArchivoParadas() {
        String value = properties.getProperty(PARADA_KEY);
        if (value == null) {
            System.err.println("No se encontró la clave de la configuración: " + PARADA_KEY);
        }
        return value;
    }

    public static int getMaxPasajerosPorParada() {
        String value = properties.getProperty(MAX_PASAJERO_POR_PARADA_KEY);
        if (value == null) {
            System.err.println("No se encontró la clave de la configuración: " + MAX_PASAJERO_POR_PARADA_KEY);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("El valor de la clave " + MAX_PASAJERO_POR_PARADA_KEY + " no es un número válido: " + value);
            return 0;
        }   
    }
    public static int getCapacidadColectivo() {
        return getEntero(CAPACIDAD_KEY);
    }

    public static int getCantidadAsientos() {
        return getEntero(CANTIDAD_DE_ASIENTOS_KEY);
    }

    public static int getMaxVueltas() {
        return getEntero(MAX_VUELTAS_KEY);
    }

    public static String getArchivoSalida() {
        String value = properties.getProperty(SALIDA_KEY);
        if (value == null) {
            System.err.println("No se encontró la clave de la configuración: " + SALIDA_KEY);
        }
        return value;
    }

    private static int getEntero(String clave) {
        String value = properties.getProperty(clave);
        if (value == null) {
            System.err.println("No se encontró la clave de la configuración: " + clave);
            return 0;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("El valor de la clave " + clave + " no es un número válido: " + value);
            return 0;
        }
    }
    
}