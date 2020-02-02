package serverclient;

import chomp.ChompController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;


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

    private static ArrayList<String> clients;

    private static boolean initialized = false;

    private static boolean gameWindow = false;

    private static boolean inviteWindow = false;

    //public static ClientController thisController;

    private static String nutzername;

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
                //thisController = this;
                session = new Session();
            } else {
                if(!inviteWindow){
                    createMessageListener();
                    clients = new ArrayList<String>();
                }
                else{
                    //inviteWindow ist true
                }
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

        this.nutzername = username.getText();

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
    private TextField horizontal;

    @FXML
    private TextField vertical;

    private static String gameOpponent;

    private static int horizontalField;

    private static int verticalField;

    @FXML
    private void createNewGame(){
        gameWindow = true;
        Parent root;
        try {
            Stage stage = new Stage();
            // Create the FXMLLoader
            FXMLLoader loader = new FXMLLoader();

            // Path to the FXML File
            //String fxmlDocPathGame = "/home/sophie/Documents/Programmierpraktikum/serverclient/Game.fxml";
            String fxmlDocPathGame = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\serverclient\\Game.fxml";

            FileInputStream fxmlGameStream = new FileInputStream(fxmlDocPathGame);

            // Create the Pane and all Details
            AnchorPane rootGame = (AnchorPane) loader.load(fxmlGameStream);

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
        System.out.println("### OpponentCheck invoked");
        String opponent = opponentField.getText();
        if(clients.contains(opponent)){
            System.out.println("### Opponent found");
            Stage thisStage = (Stage) opponentField.getScene().getWindow();
            thisStage.close();
            gameOpponent = opponent;
            verticalField = Integer.parseInt(vertical.getText());
            horizontalField = Integer.parseInt(horizontal.getText());

            send_server_message(opponent, "500");
            System.out.println("### Chomp start invoked");
            startChomp();
        }
        else{
            System.out.println("### Opponent not found");
            Stage thisStage = (Stage) opponentField.getScene().getWindow();
            thisStage.close();
        }

    }

    @FXML
    private void cancel(){
        Stage thisStage = (Stage) opponentField.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    private void startChomp() throws IOException{
        Stage chompWindow = new Stage();
        FXMLLoader chompLoader = new FXMLLoader();
        //String fxmlDocPathChomp = "/home/sophie/Documents/Programmierpraktikum/chomp/StartGame.fxml";
        String fxmlDocPathChomp = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\chomp\\StartGame.fxml";
        FileInputStream fxmlChompStream = new FileInputStream(fxmlDocPathChomp);
        AnchorPane rootChomp = (AnchorPane) chompLoader.load(fxmlChompStream);

        ChompController chompController = chompLoader.getController();
        chompController.setParameters(this, verticalField, horizontalField, nutzername, gameOpponent);

        // Create the Scene
        Scene chompScene = new Scene(rootChomp);
        chompWindow.setScene(chompScene);

        chompWindow.setTitle("Chomp Game");

        /*chompWindow.initModality(Modality.NONE);

        chompWindow.initOwner(inputField.getScene().getWindow());*/

        System.out.println("### Show Window");
        chompWindow.show();
    }

    @FXML
    private Label inviteText;

    public void gotInvite(String opponent) throws IOException{
        inviteWindow = true;
        gameOpponent = opponent;
        //TODO : Popup für die Einladung öffnen
        Task popupTask = new Task<Void>(){
             @Override public Void call() {
                 /*Platform.runLater(new Runnable() {
                     @Override public void run() {
                         try {
                             System.out.println("### create Stage");
                             Stage inviteWindow = new Stage();
                             System.out.println("### create Loader");
                             FXMLLoader inviteLoader = new FXMLLoader();
                             System.out.println("### make Path");
                             //String fxmlDocPathChomp = "/home/sophie/Documents/Programmierpraktikum/chomp/InviteWindow.fxml";
                             String fxmlDocPathInvite = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\chomp\\InviteWindow.fxml";
                             System.out.println("### create Stream");
                             FileInputStream fxmlInviteStream = new FileInputStream(fxmlDocPathInvite);
                             System.out.println("### create pane");
                             AnchorPane rootInvite = (AnchorPane) inviteLoader.load(fxmlInviteStream);
                             System.out.println("### Start platform.runlater");
                             System.out.println("### create scene");
                             Scene inviteScene = new Scene(rootInvite);

                             inviteWindow.setScene(inviteScene);
                             inviteWindow.setTitle("Einladung");
                             System.out.println("### Show Window");
                             inviteWindow.show();
                         }catch(IOException e){}
                     }
                 });*/
                 Stage thisStage = (Stage) activeClients.getScene().getWindow();
                 Popup popup = new Popup();
                 popup.setX(300);
                 popup.setY(200);
                 Label text = new Label(opponent + " hat dich zum Spielen eingeladen!");
                 Button accept = new Button("Annehmen");
                 accept.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
                         try {
                             //Nachricht über Annahme an Server senden
                             send_server_message(opponent, "503");
                             popup.hide();
                         }catch(Exception e) {}
                     }
                 });
                 Button reject = new Button("Ablehnen");
                 reject.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent actionEvent){
                         try {
                             //Nachricht über Ablehnung an Server senden
                             send_server_message(opponent, "502");
                         }catch(Exception e) {}
                         popup.hide();
                     }
                 });
                 Platform.runLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           popup.getContent().addAll(text,reject, accept);
                                           popup.show(thisStage);
                                       }
                                   });
                 return null;
             }
        };
        System.out.println("### Task created");
        new Thread(popupTask).start();
        System.out.println("### Taskthread started");
    }
    private void acceptedInvite(){
        //Methode falls man zum Spielen aufgefordert wurde und die Herausforderung annimmt
    }


}