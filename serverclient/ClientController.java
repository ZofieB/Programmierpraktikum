package serverclient;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ClientController extends Thread{
    //Login Fenster
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    //Client Fenster
    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputField;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    private static Socket server;

    // Add a public no-args constructor
    public ClientController() {
    }

    @FXML
    private void initialize() throws IOException {
        createSocket();
        createMessageListener();
    }

    @FXML
    private void sendMessage() throws IOException{
        if(server != null) {
            send_server_message(inputField.getText(), "100");
        }
    }
    @FXML
    private void logout() throws IOException{
        send_server_message("", "101");
        server.close();
        //Client disconnecten
    }

    @FXML
    private void login() throws  IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(username.getText());
        out.newLine();
        out.flush();
        out.write(password.getText());
        out.newLine();
        out.flush();
    }
    private void createSocket(){
        try {
            server = new Socket("localhost", 6666);
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

    public void send_server_message(String message, String code)throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }
}