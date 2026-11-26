package modelo;


public class Pasajero {
    private final int id;
    private final  Parada destino;
private int colectivoPerdidos=0;
private int calificacion =1;


    public Pasajero(int id, Parada destino) {
        this.id = id;
        this.destino = destino;
    }


    //metodos 
    public void califiacionAlsubir (int posicion, int cantidadAsientos){
        if (colectivoPerdidos ==0){
    calificacion = (posicion < cantidadAsientos) ? 5 : 4;
        } else if ( colectivoPerdidos ==1){
            calificacion = 3;
        }else{
            calificacion=2;
        }
    }

    public int getId() {
        return id;
    }

    public Parada getDestino() {
        return destino;
    }   

        public int getCalificacion() {
            return calificacion;
        }

        public int getColectivoPerdidos() {
            return colectivoPerdidos;
        }

        public void perderColectivo() {
            colectivoPerdidos++;
        }
}