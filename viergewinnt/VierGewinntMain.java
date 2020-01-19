package viergewinnt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class VierGewinntMain extends Application {
    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }

    public void start(Stage stage) throws IOException {

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();

        // Path to the FXML File
        //String fxmlDocPathLogin = "/home/sophie/Documents/Programmierpraktikum/viergewinnt/VierGewinntFeld.fxml";
        String fxmlDocPathLogin = "C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/viergewinnt/VierGewinntFeld.fxml";

        FileInputStream fxmlLoginStream = new FileInputStream(fxmlDocPathLogin);

        // Create the Pane and all Details
        AnchorPane rootLogin = (AnchorPane) loader.load(fxmlLoginStream);

        // Create the Scene
        Scene loginScene = new Scene(rootLogin);

        // Set the Scene to the Stage
        stage.setScene(loginScene);

        // Set the Title to the Stage
        stage.setTitle("Server Log");

        // Display the Stage
        stage.show();
    }
}
