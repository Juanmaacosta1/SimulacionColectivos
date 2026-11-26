package controlador;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import config.Configuracion;
import io.Datos;
import logica.Simulador;
import modelo.Linea;
import modelo.Parada;
import modelo.Colectivo;
import presentacion.SimulacionOutput;
import util.AdministracionColectivos;
import util.AdministracionPasajeros;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrincipalController {
    @FXML private TextArea areaSalida;
    @FXML private Button btnIniciar;
    @FXML private Label lblEstado;
    @FXML private VBox panelLineas;
    @FXML private Label lblLineaSeleccionada;
    @FXML private Label lblParadaActual;
    @FXML private Label lblEsperando;
    @FXML private Label lblAbordo;
    @FXML private HBox personasEsperando;
    @FXML private HBox personasAbordo;

    private final Map<String, List<Linea>> lineasAgrupadas = new LinkedHashMap<>();
    private final List<Button> botonesLinea = new ArrayList<>();
    private String numeroLineaSeleccionada;

    @FXML
    private void initialize() {
        Datos datos = new Datos();
        Map<Integer, Parada> paradas = datos.cargarParadas();
        List<Linea> lineas = datos.cargarLineas(Configuracion.getArchivoLineas(), paradas);

        for (Linea linea : lineas) {
            String numero = linea.getCodigo().split("-", 2)[0];
            lineasAgrupadas.computeIfAbsent(numero, clave -> new ArrayList<>()).add(linea);
        }

        for (Map.Entry<String, List<Linea>> entry : lineasAgrupadas.entrySet()) {
            String numero = entry.getKey();
            Button boton = new Button("Línea " + numero);
            boton.setMaxWidth(Double.MAX_VALUE);
            boton.getStyleClass().add("boton-linea");
            boton.setOnAction(e -> mostrarParadas(numero, entry.getValue(), boton));
            panelLineas.getChildren().add(boton);
            botonesLinea.add(boton);
        }

        if (lineasAgrupadas.containsKey("1") && !botonesLinea.isEmpty()) {
            mostrarParadas("1", lineasAgrupadas.get("1"), botonesLinea.get(0));
        }
    }

    private void mostrarParadas(String numero, List<Linea> recorridos, Button botonSeleccionado) {
        numeroLineaSeleccionada = numero;

        for (Button boton : botonesLinea) {
            boton.getStyleClass().remove("boton-linea-seleccionado");
        }
        botonSeleccionado.getStyleClass().add("boton-linea-seleccionado");

        lblLineaSeleccionada.setText("Línea " + numero + " - Ida y vuelta");
        areaSalida.clear();

        int numeroParada = 1;
        for (Linea recorrido : recorridos) {
            String sentido = recorrido.getCodigo().contains("-IDA") ? "IDA" : "VUELTA";
            areaSalida.appendText("== " + sentido + " ==\n");

            for (Parada parada : recorrido.getParadas()) {
                areaSalida.appendText(numeroParada + ". " + parada.getDireccion()
                        + " (ID " + parada.getId() + ")\n");
                numeroParada++;
            }
            areaSalida.appendText("\n");
        }

        lblEstado.setText("Línea " + numero + " seleccionada");
    }

    @FXML
    private void onIniciar() {
        areaSalida.clear();
        if (numeroLineaSeleccionada == null) {
            lblEstado.setText("Seleccione una línea");
            return;
        }

        String lineaSeleccionada = numeroLineaSeleccionada;
        List<Linea> recorridos = lineasAgrupadas.get(lineaSeleccionada);
        btnIniciar.setDisable(true);
        panelLineas.setDisable(true);
        lblEstado.setText("Simulando línea " + lineaSeleccionada + "...");
        lblParadaActual.setText("Preparando recorrido...");
        actualizarPersonas(personasEsperando, lblEsperando, 0, Color.web("#ef9a3c"), "esperando");
        actualizarPersonas(personasAbordo, lblAbordo, 0, Color.web("#318a67"), "a bordo");

        Task<Void> tarea = new Task<>() {
            @Override
            protected Void call() {
                SimulacionOutput vista = new SimulacionOutput();
                vista.setListener(linea ->
                    Platform.runLater(() -> areaSalida.appendText(linea + "\n")));
                vista.setListenerVisual(estado -> {
                    Platform.runLater(() -> mostrarEstadoVisual(estado));
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IllegalStateException("Se interrumpió la visualización", e);
                    }
                });

                for (Linea recorrido : recorridos) {
                    for (Parada parada : recorrido.getParadas()) {
                        parada.getPasajerosEsperando().clear();
                    }
                }
                AdministracionPasajeros.generarPasajerosAleatorios(recorridos);
                List<Colectivo> colectivos =
                        AdministracionColectivos.generarColectivos(recorridos);
                new Simulador(colectivos, vista).ejecutar();
                return null;
            }
        };

        tarea.setOnSucceeded(e -> {
            lblEstado.setText("Simulación finalizada");
            btnIniciar.setDisable(false);
            panelLineas.setDisable(false);
        });
        tarea.setOnFailed(e -> {
            lblEstado.setText("Error: " + tarea.getException().getMessage());
            btnIniciar.setDisable(false);
            panelLineas.setDisable(false);
        });

        Thread hilo = new Thread(tarea, "simulacion");
        hilo.setDaemon(true);
        hilo.start();
    }

    private void mostrarEstadoVisual(SimulacionOutput.EstadoVisual estado) {
        lblParadaActual.setText(estado.linea() + " · " + estado.parada());
        String personasEsperandoTexto = estado.esperando() + (estado.esperando() == 1
                ? " persona esperando" : " personas esperando");
        String cambios = "Subieron " + estado.subieron() + " · Bajaron " + estado.bajaron();
        lblEsperando.setText(personasEsperandoTexto + "  |  " + cambios);
        lblAbordo.setText(estado.aBordo() + " de " + estado.capacidad() + " pasajeros");
        actualizarPersonas(personasEsperando, null, estado.esperando(),
                Color.web("#ef9a3c"), "esperando");
        actualizarPersonas(personasAbordo, null, estado.aBordo(),
                Color.web("#318a67"), "a bordo");
    }

    private void actualizarPersonas(HBox contenedor, Label contador, int cantidad,
                                    Color color, String descripcion) {
        contenedor.getChildren().clear();
        if (contador != null) {
            contador.setText(cantidad + (cantidad == 1 ? " persona " : " personas ") + descripcion);
        }

        int iconosVisibles = Math.min(cantidad, 10);
        for (int i = 0; i < iconosVisibles; i++) {
            contenedor.getChildren().add(crearIconoPersona(color));
        }
        if (cantidad > iconosVisibles) {
            Label restantes = new Label("+" + (cantidad - iconosVisibles));
            restantes.getStyleClass().add("personas-extra");
            contenedor.getChildren().add(restantes);
        } else if (cantidad == 0) {
            contenedor.getChildren().add(new Label("Nadie " + descripcion));
        }
    }

    private Node crearIconoPersona(Color color) {
        Pane figura = new Pane();
        figura.setPrefSize(24, 38);
        figura.setMinSize(24, 38);
        figura.setMaxSize(24, 38);

        Circle cabeza = new Circle(12, 5, 4, color);
        Line cuerpo = linea(12, 10, 12, 22, color);
        Line brazos = linea(5, 15, 19, 15, color);
        Line piernaIzquierda = linea(12, 22, 6, 32, color);
        Line piernaDerecha = linea(12, 22, 18, 32, color);
        figura.getChildren().addAll(cabeza, cuerpo, brazos, piernaIzquierda, piernaDerecha);
        figura.setAccessibleText("Persona");
        return figura;
    }

    private Line linea(double inicioX, double inicioY, double finX, double finY, Color color) {
        Line linea = new Line(inicioX, inicioY, finX, finY);
        linea.setStroke(color);
        linea.setStrokeWidth(3);
        linea.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        return linea;
    }
}