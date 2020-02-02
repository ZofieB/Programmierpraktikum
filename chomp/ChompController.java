package chomp;

import absclasses.Spieler;
import absclasses.Spielzug;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import serverclient.ClientController;

import java.io.FileInputStream;
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

    // Add a public no-args constructor
    public ChompController() {
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

            Chomp chomp = new Chomp(spielfeld, spielerarr, this);
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
                                    spielzugAction(rec);
                                    event.consume();
                                }
                            });
                    feld.add(rec, i, j);
                }
            }
        }
    }
    //TODO; CHOMPSPIEL STARTEN

    public void setFeld(Spieler spieler, int x, int y) throws IOException{
        ObservableList<Node> children = feld.getChildren();
        //cast von group zu rectangle nicht möglich! TODO
        for(Node n : children){
            if(feld.getRowIndex(n) == y && feld.getColumnIndex(n) == x){
                Rectangle rec = (Rectangle) n;
                rec.setFill(spieler.getFarbe());
                //gemachten Spielzug an den Server schicken
                clientController.send_server_message("", "");
            }
        }
    }

    public void spielzugAction(Rectangle rec) {
        //Koordinaten des angeklickten Rechtecks bekommen
        int x = feld.getColumnIndex(rec);
        int y = feld.getRowIndex(rec);
        //chomp.spielzug(spieler1, x, y);
        rec.setFill(BLUEVIOLET);
    }

    private void setSpielzug(Spielzug spielzug){
        //MessageListener ruft diese Methode auf  wenn neuer Spielzug vorliegt
    }

    public void setParameters(ClientController newClientController, int newFeldvertical, int newFeldhorizontal, String nutzername, String opponent){
        System.out.println("Übergebener Parameter feldvertical: " + newFeldvertical);
        System.out.println("### setParameters invoked");
        feldvertical = newFeldvertical;
        System.out.println("feldvertical gesetzt auf: " + feldvertical);
        feldhorizontal = newFeldhorizontal;
        clientController = newClientController;
        spieler1Name = nutzername;
        spieler2Name = opponent;
    }

    @FXML
    private void startTheGame() throws  IOException{
        Stage stage = (Stage) startButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ChompField.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
