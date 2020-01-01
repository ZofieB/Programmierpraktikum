package serverclient;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class ClientController{
    private static boolean initialized = false;
    //Login Fenster
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private TextArea textOutput;

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

    private Session session;

    private FXMLLoader loader;

    private static MessageListener messages;

    // Add a public no-args constructor
    public ClientController() {
    }

    @FXML
    private void initialize() throws IOException {
        System.out.println("### Initialize invoked");
        if (!initialized)
        {
            initialized = true;
            createSocket();
            createMessageListener();
            session = new Session();
        }
    }

    @FXML
    private void sendMessage() throws IOException{
        if(server != null) {
            send_server_message(inputField.getText(), "100");
        }
    }
    @FXML
    private void logout() throws IOException{
        Stage stage = (Stage) inputField.getScene().getWindow();
        send_server_message("101", "101");
        server.close();
        stage.close();

    }

    @FXML
    private void login() throws  IOException{
        System.out.println("### Login invoked");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(username.getText());
        out.newLine();
        out.flush();
        out.write(password.getText());
        out.newLine();
        out.flush();

        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ClientWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
        messages = new MessageListener(in, outputField);
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

    public void getClients(){
        ArrayList<String> clients = session.returnClientList();
    }
}