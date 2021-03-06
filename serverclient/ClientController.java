package serverclient;

import chomp.ChompController;
import viergewinnt.VierGewinntController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class ClientController {
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

    //private FXMLLoader loader;

    private static ArrayList<String> clients;

    //Initialisierung Variablen

    private static boolean initialized = false;

    private static boolean gameWindow = false;

    private static boolean inviteWindow = false;

    public static ClientController chatWindowController;

    private static String nutzername;

    // Add a public no-args constructor
    public ClientController() {
    }


    @FXML
    private void initialize() throws IOException {
        System.out.println("### Initialize invoked");
        if (!gameWindow) {
            if (!initialized) {
                initialized = true;
                createSocket();
            } else {
                if (!inviteWindow) {
                    createMessageListener();
                    chatWindowController = this;
                    clients = new ArrayList<String>();
                }
            }
        } else {
            choiceGames.setItems(FXCollections.observableArrayList("Chomp", "Vier Gewinnt"));
        }
    }

    @FXML
    private void sendMessage() throws IOException {
        if (server != null) {
            send_server_message(inputField.getText(), "100");
        }
        inputField.clear();
    }

    @FXML
    public void logout() throws IOException {
        Stage stage = (Stage) inputField.getScene().getWindow();
        if (server != null) {
            send_server_message("101", "101");
            server.close();
        }
        stage.close();

    }

    @FXML
    private void login() throws Exception {
        System.out.println("### Login invoked");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(username.getText());
        out.newLine();
        out.flush();
        out.write(password.getText());
        out.newLine();
        out.flush();

        nutzername = username.getText();

        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ClientWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void createSocket() {
        try {
            server = new Socket("localhost", 6666);
        } catch (UnknownHostException e) {
            System.out.println("Cannot find host.");
        } catch (IOException e) {
            System.out.println("Error connecting to host.");
        }
    }

    private void createMessageListener() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        MessageListener messages = new MessageListener(in, this);
        messages.start();
    }

    public void send_server_message(String message, String code) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }

    public void updateTextArea(String message) {
        outputField.appendText(message + "\n");
    }

    public void updateClientList() {
        System.out.println("### Clients Update invoked");
        activeClients.clear();
        for (String s : clients) {
            activeClients.appendText(s + "\n");
        }
    }

    public void deleteClient(String newClient) {
        System.out.println("### DeleteClient invoked");
        int clients_size = clients.size();
        for (int i = 0; i < clients_size; i++) {
            if (clients.get(i).equals(newClient)) {
                clients.remove(i);
                break;
            }
        }
        System.out.println("### DeleteClient finished");
    }

    public void addClient(String newClient) {
        System.out.println("### AddClient invoked");
        clients.add(newClient); //Programm bleibt stehen!
        System.out.println("### AddClient finished");
    }

    //
    // GAME SECTION
    //

    @FXML
    private ChoiceBox<String> choiceGames;

    @FXML
    private TextField opponentField;

    @FXML
    private TextField horizontal;

    @FXML
    private TextField vertical;

    private static String gameOpponent;

    private static int horizontalField;

    private static int verticalField;

    public static boolean isAccepted = false;

    private static boolean firstPlayer = true;

    private static ChompController chompController;

    private static VierGewinntController vierGewinntController;

    private static boolean inGame = false;

    private static String selectedGame;

    public static String cancelMessage = "----Der Gegner hat nicht geantwortet. Das Spiel wird nicht gestartet!----";

    @FXML
    private void createNewGame() {
        gameWindow = true;
        try {
            if(!inGame) {
                Stage stage = new Stage();
                // Create the FXMLLoader
                FXMLLoader loader = new FXMLLoader();

                // Path to the FXML File
                //String fxmlDocPathGame = "/home/sophie/Documents/Programmierpraktikum/serverclient/Game.fxml";
                //String fxmlDocPathGame = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\serverclient\\Game.fxml";
                //String fxmlDocPathGame = "/home/zo73qoh/IdeaProjects/Programmierpraktikum/serverclient/Game.fxml";
                String fxmlDocPathGame = "C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/serverclient/Game.fxml";

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
            else {
                updateTextArea("----Du bist schon in einem Spiel! Versuche es später erneut!----");
                gameWindow = false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkOpponent() throws Exception{
        System.out.println("### OpponentCheck invoked");
        String opponent = opponentField.getText();
        if(clients.contains(opponent)){
            selectedGame = choiceGames.getValue();
            System.out.println("### Opponent found");
            Stage thisStage = (Stage) opponentField.getScene().getWindow();
            thisStage.close();
            gameOpponent = opponent;
            verticalField = Integer.parseInt(vertical.getText());
            horizontalField = Integer.parseInt(horizontal.getText());

            //versende Nachricht der Form "gegner-spiel-horizontal-vertikal"
            send_server_message(opponent + "-" + nutzername + "-" + selectedGame + "-" + horizontalField + "-" + verticalField, "500");

            chatWindowController.updateTextArea("----Es wird auf eine Antwort des Gegners gewartet...----");

            //Gegner Zeit geben, zu antworten
            Thread wait = new Thread(){
                public void run(){
                    try {
                        Thread.sleep(10000);
                        waitedForInvite();
                    }catch(Exception e) {}
                }
            };
            wait.start();

        }
        else{
            System.out.println("### Opponent not found");
            Stage thisStage = (Stage) opponentField.getScene().getWindow();
            thisStage.close();
            gameWindow = false;
        }

    }

    private void waitedForInvite() throws IOException{
        //Aufgerufen von Nutzer, der die Einladung geschickt hat
        System.out.println("### waitedForInvite invoked");
        if(isAccepted){
            if(selectedGame.equals("Chomp")){
                inGame = true;
                firstPlayer = true;
                startChomp();
            }
            else if(selectedGame.equals("Vier Gewinnt")){
                System.out.println("### VierGewinnt in waitedForInvite");
                inGame = true;
                firstPlayer = true;
                startVierGewinnt();
            }
        }
        else{
            //Cancel Message wird je nach Ablehnungsart gesetzt
            chatWindowController.updateTextArea(cancelMessage);
        }
    }

    @FXML
    private void cancel() {
        Stage thisStage = (Stage) opponentField.getScene().getWindow();
        thisStage.close();
        gameWindow = false;
    }

    @FXML
    private void startChomp() throws IOException{
        ClientController thisClientController = this;
        Task chompTask = new Task<Void>() {
            @Override
            public Void call(){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            Stage chompWindow = new Stage();
                            FXMLLoader chompLoader = new FXMLLoader();
                            //String fxmlDocPathChomp = "/home/sophie/Documents/Programmierpraktikum/chomp/StartGame.fxml";
                            //String fxmlDocPathChomp = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\chomp\\StartGame.fxml";
                            //String fxmlDocPathChomp = "/home/zo73qoh/IdeaProjects/Programmierpraktikum/chomp/StartGame.fxml";
                            String fxmlDocPathChomp = "/C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/chomp/StartGame.fxml";
                            FileInputStream fxmlChompStream = new FileInputStream(fxmlDocPathChomp);
                            AnchorPane rootChomp = (AnchorPane) chompLoader.load(fxmlChompStream);

                            chompController = chompLoader.getController();
                            chompController.setParameters(thisClientController, verticalField, horizontalField, nutzername, gameOpponent, firstPlayer);

                            // Create the Scene
                            Scene chompScene = new Scene(rootChomp);
                            chompWindow.setScene(chompScene);

                            chompWindow.setTitle("Chomp Game");
                            chompWindow.show();
                        }catch(IOException e) {}
                    }
                });
                return null;
            }
        };
        new Thread(chompTask).start();
    }

    @FXML
    private void startVierGewinnt(){
        System.out.println("###Aufruf start VierGewinnt");
        ClientController thisClientController = this;
        Task vierGewinntTask = new Task<Void>() {
            @Override
            public Void call(){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        try {
                            Stage vierGewinntWindow = new Stage();

                            // Create the FXMLLoader
                            FXMLLoader vierGewinntLoader = new FXMLLoader();

                            // Path to the FXML File
                            String fxmlDocPathVierGewinnt = "/C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum/viergewinnt/StartGameVierGewinnt.fxml";
                            //String fxmlDocPathVierGewinnt = "/home/zo73qoh/IdeaProjects/Programmierpraktikum/viergewinnt/StartGameVierGewinnt.fxml";

                            FileInputStream fxmlVierGewinntStream = new FileInputStream(fxmlDocPathVierGewinnt);

                            AnchorPane rootVierGewinnt = (AnchorPane) vierGewinntLoader.load(fxmlVierGewinntStream);

                            VierGewinntController vierGewinntController = vierGewinntLoader.getController();
                            vierGewinntController.setParameters(thisClientController, verticalField, horizontalField, nutzername, gameOpponent, firstPlayer);

                            // Create the Scene
                            Scene vierGewinntScene = new Scene(rootVierGewinnt);
                            vierGewinntWindow.setScene(vierGewinntScene);

                            vierGewinntWindow.setTitle("Vier Gewinnt Game");

                            System.out.println("### Show Window");
                            vierGewinntWindow.show();
                        }catch(IOException e) {}
                    }
                });
                return null;
            }
        };
        new Thread(vierGewinntTask).start();
    }


    public void gotInvite (String opponent, String game,int horizontal, int vertical) throws IOException {
        inviteWindow = true;
        if(inGame == false) {
            gameOpponent = opponent;
            horizontalField = horizontal;
            verticalField = vertical;
            Task popupTask = new Task<Void>() {
                @Override
                public Void call() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String accept = "Annehmen";
                            String reject = "Ablehnen";

                            ChoiceDialog<String> dialog = new ChoiceDialog<String>(accept, accept, reject);

                            dialog.setTitle("Einladung");
                            dialog.setHeaderText("Du wurdest von " + opponent + " zu einer Runde " + game + " eingeladen!");
                            dialog.setContentText("Einladung annehmen?");

                            Optional<String> result = dialog.showAndWait();

                            if (result.isPresent()) {
                                String res = result.get();
                                inviteWindow = false;
                                try {
                                    if (res.equals("Annehmen")) {
                                        //Einladung wurde angenommen
                                        send_server_message(game + "-" + opponent, "503");
                                        acceptedInvite(game);
                                    } else if (res.equals("Ablehnen")) {
                                        //Einladung wurde abgelehnt --> nichts tun
                                        send_server_message(opponent, "502");
                                        inviteWindow = false;
                                    }
                                } catch (IOException e) {
                                }
                            }
                        }
                    });
                    return null;
                }
            };
            new Thread(popupTask).start();
        }
        else{
            send_server_message(opponent, "504");
            inviteWindow = false;
        }
    }
    private void acceptedInvite(String game) throws IOException{
        firstPlayer = false;
        if(game.equals("Chomp")){
            //Server gestartetes Spiel in der Form spiel-nutzer-gegner schicken
            send_server_message("Chomp-" + nutzername + "-" + gameOpponent, "599");
            inGame = true;
            startChomp();
        }
        else if(game.equals("Vier Gewinnt")){
            System.out.println("### VierGewinnt in acceptedInvite");
            send_server_message("Vier Gewinnt-" + nutzername + "-" + gameOpponent, "599");
            inGame = true;
            startVierGewinnt();
        }

    }


    public void setSpielzugChomp(int col, int row) {
        System.out.println("### SetSpielzug Chomp invoked");
        chompController.setSpielzug(col, row);
    }

    public void setSpielzugVierGewinnt(int col, int row) {
        System.out.println("### SetSpielzug VierGewinnt invoked");
        vierGewinntController.setSpielzug(col, row);
    }

    public void setChompController(ChompController newChompController){
        chompController = newChompController;
    }

    public void setVierGewinntController(VierGewinntController newVierGewinntController){
        vierGewinntController = newVierGewinntController;
    }

    public void gameCancelChomp(){
        chompController.gameGotCanceled();
        updateTextArea("Dein Spiel wurde abgebrochen!");
        inGame = false;
        inviteWindow = false;
        firstPlayer = true;
        gameWindow = false;
        isAccepted = false;
    }

    public void finishedGame(){
        //Flags zurücksetzen
        System.out.println("Finished Game");
        inGame = false;
        gameWindow = false;
        inviteWindow = false;
        firstPlayer = true;
        isAccepted = false;
    }

    public void gameCancelVierGewinnt() {
        vierGewinntController.gameGotCanceled();
        updateTextArea("Dein Spiel wurde abgebrochen!");
        inGame = false;
        gameWindow = false;
        inviteWindow = false;
        firstPlayer = true;
        isAccepted = false;
    }

    public void changeCancelMessage(String message){
        cancelMessage = message;
    }
}
