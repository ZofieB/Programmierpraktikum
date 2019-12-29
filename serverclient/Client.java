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
        String fxmlDocPath = "/home/sophie/Documents/Programmierpraktikum/serverclient/ClientWindow.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        // Create the Pane and all Details
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);

        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("Connected to Server");
        // Display the Stage
        stage.show();
    }

    static public void send_server_message(String message, String code, Socket server)throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }
}

/*
        //Login Versuch (Methode login disconnected client wenn login fehlgeschlagen)
        System.out.println("Benutzername:");
        String client_benutzername = scan.nextLine();
        out.write(client_benutzername);
        out.newLine();
        out.flush();
        System.out.println("Passwort:");
        out.write(scan.nextLine());
        out.newLine();
        out.flush();
        boolean login = true;
        if(in.readLine().equals("111")){
        System.out.println(in.readLine());
        }

        //Nachrichten empfangen

*/
