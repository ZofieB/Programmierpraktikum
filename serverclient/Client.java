package serverclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;


public class Client extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();

        // Path to the FXML File
        //String fxmlDocPathLogin = "/home/sophie/Documents/Programmierpraktikum/serverclient/ClientLogin.fxml";
        String fxmlDocPathLogin = "C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/serverclient/ClientLogin.fxml";
        //String fxmlDocPathLogin = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\serverclient\\ClientLogin.fxml";
        //String fxmlDocPathLogin = "C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/serverclient/ClientLogin.fxml";
        //String fxmlDocPathLogin = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\serverclient\\ClientLogin.fxml";
        //String fxmlDocPathLogin = "/home/zo73qoh/IdeaProjects/Programmierpraktikum/serverclient/ClientLogin.fxml";
        FileInputStream fxmlLoginStream = new FileInputStream(fxmlDocPathLogin);

        // Create the Pane and all Details
        AnchorPane rootLogin = (AnchorPane) loader.load(fxmlLoginStream);

        // Create the Scene
        Scene loginScene = new Scene(rootLogin);

        // Set the Scene to the Stage
        stage.setScene(loginScene);

        // Set the Title to the Stage
        stage.setTitle("Blumunovs Spieleserver");

        // Display the Stage
        stage.show();
    }
}