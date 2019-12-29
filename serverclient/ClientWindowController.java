package serverclient;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ClientWindowController extends Thread{
    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField inputField;

    @FXML
    private TextArea outputField;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    private static Socket server;

    // Add a public no-args constructor
    public ClientWindowController() {
    }

    @FXML
    private void initialize() throws IOException {
        createSocket();
        createMessageListener();
    }

    @FXML
    private void printOutput(String input) {
        outputField.setText(input);
    }

    @FXML
    private void sendMessage() throws IOException{
        Client.send_server_message(inputField.getText(), "100", server);
    }
    @FXML
    private void logout() throws IOException{
        Client.send_server_message("", "101", server);
        server.close();
        //Client disconnecten
    }

    private void createSocket(){
        try {
            server = new Socket("localhost", 6666);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        }catch(UnknownHostException e) {
            System.out.println("Cannot find host.");
        }catch (IOException e) {
            System.out.println("Error connecting to host.");
        }
    }

    private void createMessageListener() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        MessageListener messages = new MessageListener(in, outputField);
        messages.start();
    }

}