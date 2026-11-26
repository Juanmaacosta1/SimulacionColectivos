package controlador;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import presentacion.SimulacionOutput;

public class PrincipalController {
    @FXML private TextArea areaSalida;
    @FXML private Button btnIniciar;
    @FXML private Label lblEstado;

    @FXML
    private void onIniciar() {
        areaSalida.clear();
        btnIniciar.setDisable(true);
        lblEstado.setText("Simulando...");

        Task<Void> tarea = new Task<>() {
            @Override
            protected Void call() {
                SimulacionOutput vista = new SimulacionOutput();
                vista.setListener(linea ->
                    Platform.runLater(() -> areaSalida.appendText(linea + "\n")));

                // TODO: acá va lo que hoy hace tu main:
                // cargar datos, generar pasajeros, crear colectivos y
                // llamar a new Simulador(colectivos, vista).ejecutar();
                return null;
            }
        };

        tarea.setOnSucceeded(e -> {
            lblEstado.setText("Simulación finalizada");
            btnIniciar.setDisable(false);
        });
        tarea.setOnFailed(e -> {
            lblEstado.setText("Error: " + tarea.getException().getMessage());
            btnIniciar.setDisable(false);
        });

        Thread hilo = new Thread(tarea, "simulacion");
        hilo.setDaemon(true);
        hilo.start();
    }
}