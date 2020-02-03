package chomp;

import absclasses.Spieler;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import serverclient.ClientController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;


public class ChompController {
    @FXML
    private GridPane feld;

    @FXML
    private Button startButton;

    @FXML
    private Button cancel;

    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    private Chomp chomp;

    private ChompFeld chompFeld;

    private static ClientController clientController;

    private static int feldhorizontal;

    private static int feldvertical;

    private Spieler spieler1;

    private Spieler spieler2;

    private static boolean initialized = false;

    private static String spieler1Name;

    private static String spieler2Name;

    private static boolean firstPlayer;

    private static boolean myTurn;

    // Add a public no-args constructor
    public ChompController() {
    }
    //Erste Methode, die vom ClientController zur Initialisierung aufgerufen wird
    public void setParameters(ClientController newClientController, int newFeldvertical, int newFeldhorizontal, String nutzername, String opponent, boolean startSpieler){
        System.out.println("Übergebener Parameter feldvertical: " + newFeldvertical);
        System.out.println("### setParameters invoked");
        feldvertical = newFeldvertical;
        System.out.println("feldvertical gesetzt auf: " + feldvertical);
        feldhorizontal = newFeldhorizontal;
        clientController = newClientController;
        spieler1Name = nutzername;
        spieler2Name = opponent;
        firstPlayer = startSpieler;
        myTurn = firstPlayer;
    }

    @FXML
    private void initialize() throws IOException {
        if(!initialized) {
            System.out.println("### first Initialize invoked");
            initialized = true;
        }
        else {
            System.out.println("### second Initialize invoked");
            System.out.println("Wert von feldhorizontal: " + feldhorizontal + " Und wert von feldvertical: " + feldvertical);
            spieler1 = new Spieler(spieler1Name, 1, BLUE);
            spieler2 = new Spieler(spieler2Name, 1, GREEN);

            Spieler[] spielerarr = {spieler1, spieler2};

            //Create Chomp game field and spieler

            ChompFeld spielfeld = new ChompFeld();
            spielfeld.setHorizontal(feldhorizontal);
            spielfeld.setVertical(feldvertical);
            spielfeld.initializeSpielfeld();

            chomp = new Chomp(spielfeld, spielerarr, this);
            //Größe Rectangles Parametrisieren auf Eingabe!!
            for (int i = 0; i < feldhorizontal; i++) {
                for (int j = 0; j < feldvertical; j++) {
                    Rectangle rec = new Rectangle();
                    rec.setX(0);
                    rec.setY(0);
                    rec.setWidth(90);
                    rec.setHeight(90);
                    rec.setArcWidth(5);
                    rec.setArcHeight(5);
                    rec.setFill(LIGHTGREY);
                    //rec.setOnMouseClicked();
                    rec.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    try {
                                        if(myTurn) {
                                            spielzugAction(rec);
                                            event.consume();
                                        }
                                    }catch(IOException e){}
                                }
                            });
                    feld.add(rec, i, j);
                }
            }
        }
    }

    //Spielzug-Methode, die einen selbst gemachten Spielzug (durch klicken auf Viereck) auslöst
    public void spielzugAction(Rectangle clickedRec) throws IOException{
        //Nur wenn das angeklickte Feld auch grau ist, wird die Spielmaschinerie in Bewegung gesetzt
        if(clickedRec.getFill() == LIGHTGREY) {
            ObservableList<Node> children = feld.getChildren();

            Integer recRowIndex = GridPane.getRowIndex(clickedRec);
            Integer recColumnIndex = GridPane.getColumnIndex(clickedRec);

            int recRow = recRowIndex == null ? 0 : recRowIndex;
            int recColumn = recColumnIndex == null ? 0 : recColumnIndex;

            for (Node n : children) {
                Integer rowIndex = GridPane.getRowIndex(n);
                Integer columnIndex = GridPane.getColumnIndex(n);

                int row = rowIndex == null ? 0 : rowIndex;
                int column = columnIndex == null ? 0 : columnIndex;

                if (row == recRow && column == recColumn) {
                    Rectangle rec = (Rectangle) n;
                    if (rec.getFill() == LIGHTGREY) {
                        rec.setFill(spieler1.getFarbe());
                    }
                    //gemachten Spielzug an den Server schicken
                    clientController.send_server_message(recColumn + "-" + recRow, "555");
                    //Spielzug im ChompSpiel im Hintergrund ausführen
                    chomp.spielzug(spieler1, recColumn, recRow);
                    //Zug an Gegner weitergeben
                    myTurn = false;
                }
            }
        }
    }

    //Spielzug-Methode die einen vom Gegner ausgeführten Spielzug durchführt
    public void setSpielzug(int recColumn, int recRow){
        //MessageListener ruft diese Methode auf  wenn neuer Spielzug vorliegt
        //Ausführen des reinkommenden Spielzugs
        ObservableList<Node> children = feld.getChildren();
        for(Node n : children){
            Integer rowIndex = GridPane.getRowIndex(n);
            Integer columnIndex = GridPane.getColumnIndex(n);

            int row = rowIndex == null? 0 : rowIndex;
            int column = columnIndex == null? 0 : columnIndex;

            if(row == recRow && column == recColumn){
                Rectangle rec = (Rectangle) n;
                if(rec.getFill() == LIGHTGREY) {
                    rec.setFill(spieler2.getFarbe());
                    chomp.spielzug(spieler2, recColumn, recRow);
                }
            }
        }
        //Nach Ausführung des eingehenden Spielzugs sind wir wieder dran
        myTurn = true;
    }

    @FXML
    private void startTheGame() throws  IOException{
        Stage stage = (Stage) startButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ChompField.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancelGame(){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void playerLost(Spieler spieler){

    }


}
