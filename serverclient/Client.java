package serverclient;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.OverlappingFileLockException;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
        String fxmlDocPathLogin = "/home/sophie/Documents/Programmierpraktikum/serverclient/ClientLogin.fxml";
        //String fxmlDocPath = "/home/sophie/Documents/Programmierpraktikum/serverclient/ClientWindow.fxml";

        //FileInputStream fxmlWindowStream = new FileInputStream(fxmlDocPath);
        FileInputStream fxmlLoginStream = new FileInputStream(fxmlDocPathLogin);

        // Create the Pane and all Details
        AnchorPane rootLogin = (AnchorPane) loader.load(fxmlLoginStream);
        //AnchorPane root = (AnchorPane) loader.load(fxmlWindowStream);

        // Create the Scene
        Scene loginScene = new Scene(rootLogin);
        //Scene scene = new Scene(root);

        // Set the Scene to the Stage
        stage.setScene(loginScene);
        //stage.setScene(scene);

        // Set the Title to the Stage
        stage.setTitle("Blumonovs Spieleserver");

        // Display the Stage
        stage.show();
    }

}