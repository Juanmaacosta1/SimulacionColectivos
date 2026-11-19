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
            System.err.println("No se encontró la clave de configuración: " + PARADA_KEY);
        }
        return value;
    }

    public static int getMaxPasajerosPorParada() {
        String value = properties.getProperty(MAX_PASAJERO_POR_PARADA_KEY);
        if (value == null) {
            System.err.println("No se encontró la clave de configuración: " + MAX_PASAJERO_POR_PARADA_KEY);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("El valor de la clave " + MAX_PASAJERO_POR_PARADA_KEY + " no es un número válido: " + value);
            return 0;
        }
    }
}