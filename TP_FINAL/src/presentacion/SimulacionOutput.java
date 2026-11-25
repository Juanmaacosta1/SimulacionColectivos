package presentacion;

import java.io.PrintStream;
import java.util.List;
import modelo.Colectivo;
import modelo.Parada;
import modelo.Pasajero;

public class SimulacionOutput {
    private PrintStream archivoOut = null;

    public SimulacionOutput() {}

    public static SimulacionOutput crearConArchivo(String ruta) {
        SimulacionOutput o = new SimulacionOutput();
        try {
            o.archivoOut = new PrintStream(ruta, "UTF-8");
        } catch (Exception e) {
            System.err.println("No se pudo crear " + ruta + ", solo se muestra por consola.");
        }
        return o;
    }

    public void cerrarArchivo() { if (archivoOut != null) archivoOut.close(); }

    private void imprimir(String m) {
        System.out.println(m);
        if (archivoOut != null) archivoOut.println(m);
    }

    public void mostrarInicioParada(int turno) { imprimir("=== PARADA " + turno + " ==="); }

    public void mostrarLlegadaColectivo(Colectivo c, Parada p) {
        imprimir("[LLEGADA] Línea " + c.getLinea().getCodigo() + " llegó a " + p.getDireccion());
    }

    public void mostrarEventosPasajeros(List<Pasajero> bajaron, List<Pasajero> subieron) {
        for (Pasajero p : bajaron) imprimir("  [-] Pasajero " + p.getId() + " bajó");
        for (Pasajero p : subieron) imprimir("  [+] Pasajero " + p.getId() + " subió");
    }

    public void mostrarEstadoColectivo(Colectivo c, int bajaron, int subieron) {
        imprimir("  Bajaron: " + bajaron + " | Subieron: " + subieron
                + " | A bordo: " + c.getCantidadPasajeros());
    }

    public void mostrarFinRecorrido(Colectivo c) {
        imprimir("[FIN] Línea " + c.getLinea().getCodigo() + " terminó una vuelta");
    }

    public void mostrarFinSimulacion() { imprimir("\n=== SIMULACIÓN FINALIZADA ==="); }

    public void mostrarIndiceSatisfaccion(double indice) {
        imprimir(String.format("Índice de satisfacción: %.2f", indice));
    }

    public void mostrarOcupacionPromedio(Colectivo c, double prom) {
        imprimir(String.format("Colectivo %d (Línea %s) - Ocupación promedio: %.2f",
                c.getId(), c.getLinea().getCodigo(), prom));
    }

    public void mostrarAdvertenciaColectivoLleno(Colectivo colectivo, Parada parada, int esperando) {
        imprimir("  [!] Colectivo lleno en " + parada.getDireccion() + ". Quedan esperando: " + esperando);
    }
}