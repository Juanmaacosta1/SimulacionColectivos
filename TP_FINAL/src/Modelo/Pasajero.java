package Modelo;
public class Pasajero {
    private int id ;
    private Parada paradaActual;
    private Parada destino;
    private Colectivo colectivo;
    private Linea lineaDeseada;
    private boolean subio;
    public Pasajero(int id, Parada paradaActual, Parada destino, Colectivo colectivo, Linea lineaDeseada,
            boolean subio) {
        this.id = id;
        this.paradaActual = paradaActual;
        this.destino = destino;
        this.colectivo = colectivo;
        this.lineaDeseada = lineaDeseada;
        this.subio = subio;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Parada getParadaActual() {
        return paradaActual;
    }
    public void setParadaActual(Parada paradaActual) {
        this.paradaActual = paradaActual;
    }
    public Parada getDestino() {
        return destino;
    }
    public void setDestino(Parada destino) {
        this.destino = destino;
    }
    public Colectivo getColectivo() {
        return colectivo;
    }
    public void setColectivo(Colectivo colectivo) {
        this.colectivo = colectivo;
    }
    public Linea getLineaDeseada() {
        return lineaDeseada;
    }
    public void setLineaDeseada(Linea lineaDeseada) {
        this.lineaDeseada = lineaDeseada;
    }
    public boolean isSubio() {
        return subio;
    }
    public void setSubio(boolean subio) {
        this.subio = subio;
    }


}
