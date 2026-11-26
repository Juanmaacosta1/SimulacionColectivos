package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent raiz = FXMLLoader.load(getClass().getResource("/vista/principal.fxml"));
        Scene escena = new Scene(raiz, 900, 600);
       escena.getStylesheets().add(getClass().getResource("/vista/styles.css").toExternalForm());

        stage.setTitle("Simulación de colectivos");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}