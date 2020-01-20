package serverclient;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class ClientController{
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
    private TextArea activeClients;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    private static Socket server;

    private Session session;

    private FXMLLoader loader;

    private static MessageListener messages;

    private ArrayList<String> clients;

    private static boolean initialized = false;

    private static boolean gameWindow = false;

    // Add a public no-args constructor
    public ClientController() {
    }

    @FXML
    private void initialize() throws IOException {
        System.out.println("### Initialize invoked");
        if(gameWindow == false) {
            if (!initialized) {
                initialized = true;
                createSocket();
                session = new Session();
            } else {
                createMessageListener();
                clients = new ArrayList<String>();
            }
        }
        else{
            choiceGames.setItems(FXCollections.observableArrayList("Chomp", "Vier Gewinnt"));
        }
    }

    @FXML
    private void sendMessage() throws IOException{
        if(server != null) {
            send_server_message(inputField.getText(), "100");
        }
        inputField.clear();
    }
    @FXML
    public void logout() throws IOException{
        Stage stage = (Stage) inputField.getScene().getWindow();
        if(server!= null){
            send_server_message("101", "101");
            server.close();
        }
        stage.close();

    }

    @FXML
    private void login() throws  Exception{
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
        messages = new MessageListener(in, this);
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

    public void updateTextArea(String message){
        outputField.appendText(message + "\n");
    }

    public void updateClientList(){
        System.out.println("### Clients Update invoked");
        activeClients.clear();
        for (String s : clients) {
            activeClients.appendText(s + "\n");
        }
        System.out.println("### Clients Update finished");
    }

    public void deleteClient(String newClient){
        System.out.println("### DeleteClient invoked");
        int clients_size = clients.size();
        for(int i = 0; i < clients_size; i++){
            if(clients.get(i).equals(newClient)){
                clients.remove(i);
                break;
            }
        }
        System.out.println("### DelteClient finished");
    }

    public void addClient(String newClient){
        System.out.println("### AddClient invoked");
        clients.add(newClient); //Programm bleibt stehen!
        System.out.println("### AddClient finished");
    }

    //
    // GAME SECTION
    //

    @FXML
    private ChoiceBox choiceGames;

    @FXML
    private TextField opponentField;

    @FXML
    private void createNewGame(){
        gameWindow = true;
        Parent root;
        try {
            Stage stage = new Stage();
            // Create the FXMLLoader
            FXMLLoader loader = new FXMLLoader();

            // Path to the FXML File
            String fxmlDocPathGame = "/home/sophie/Documents/Programmierpraktikum/serverclient/Game.fxml";

            FileInputStream fxmlLoginStream = new FileInputStream(fxmlDocPathGame);

            // Create the Pane and all Details
            AnchorPane rootGame = (AnchorPane) loader.load(fxmlLoginStream);

            // Create the Scene
            Scene gameScene = new Scene(rootGame);

            // Set the Scene to the Stage
            stage.setScene(gameScene);

            // Set the Title to the Stage
            stage.setTitle("New Game");

            // Display the Stage
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkOpponent() throws IOException{
        String opponent = opponentField.getText();
        if(clients.contains(opponent)){
            //Eröffne entsprechendes Spiel TODO!
            send_server_message("opponent", "500");
        }
        else{
            Stage thisStage = (Stage) opponentField.getScene().getWindow();
            thisStage.close();
        }

    }

    @FXML
    private void cancel(){
        Stage thisStage = (Stage) opponentField.getScene().getWindow();
        thisStage.close();
    }


}