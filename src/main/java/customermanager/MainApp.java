package customermanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the screen design from the FXML file
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("CustomerManager.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Customer Manager");
        stage.setScene(scene);
        stage.setMinWidth(560);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
