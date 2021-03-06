package serverclient;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerController {
    @FXML
    private TextArea logField;

    @FXML
    private TextArea activeClients;

    @FXML
    private TextArea matches;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    private ServerSocket server;

    private SocketListener socketlistener;

    private CopyOnWriteArrayList<String> matchesList = new CopyOnWriteArrayList<>();



    @FXML
    private void initialize() throws IOException {
        //Serversocket initialisieren
        server = new ServerSocket(6666);
        socketlistener = new SocketListener(server, this);

        //SocketListener initialisieren
        socketlistener.start();
    }

    @FXML
    private void shutdown() throws Exception{
        Stage stage = (Stage) logField.getScene().getWindow();
        socketlistener.shutdown();
        server.close();
        //Thread.sleep(5000);
        stage.close();
    }

    /*public void serverStart(){
        logField.appendText("SERVERSTATUS\t Der Server wurde gestartet!\n");
    }*/

    public void printOutput(String output){
        logField.appendText(output + "\n");
    }

    public void updateClientList(ArrayList<String> clients){
        System.out.println("### Clients Update invoked");
        activeClients.clear();
        for (String s : clients) {
            activeClients.appendText(s + "\n");
        }
        System.out.println("### Clients Update finished");
    }
    public void addNewMatch(String newMatch){
        //String der Form spiel-nutzer-gegner kommt rein
        matchesList.add(newMatch);
        updateMatchList();
    }
    public void deleteMatch(String userInMatch){
        System.out.println("###Delete Match started");
        for(String s : matchesList) {
            String[] splitted = s.split("-");
            if(splitted[1].equals(userInMatch)  || splitted [2].equals(userInMatch)){
                matchesList.remove(s);
            }
        }
        updateMatchList();
    }
    public void updateMatchList(){
        System.out.println("###updateMatchList started");
        matches.clear();
        for (String s : matchesList) {
            String[] splittedString = s.split("-");
            matches.appendText( splittedString[1] + " - " + splittedString[2] + " (" + splittedString[0] + ")" + "\n");
        }
    }

 /*   public void deleteClient(String newClient){
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
    }*/
}
