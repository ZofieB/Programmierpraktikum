package viergewinnt;

import absclasses.Spieler;
import absclasses.Spielzug;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import serverclient.ClientController;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;


public class VierGewinntController {


    @FXML
    private GridPane feld;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button startButton;

    private Button cancel;

    private VierGewinnt vierGewinnt;

    private VierGewinntFeld vierGewinntFeld;

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
    public VierGewinntController() {
    }

    public void setParameters(ClientController newClientController, int newFeldvertical, int newFeldhorizontal, String nutzername, String opponent) {
        System.out.println("Übergebener Parameter feldvertical: " + newFeldvertical);
        System.out.println("### setParameters invoked");
        feldvertical = newFeldvertical;
        System.out.println("feldvertical gesetzt auf: " + feldvertical);
        feldhorizontal = newFeldhorizontal;
        clientController = newClientController;
        spieler1Name = nutzername;
        spieler2Name = opponent;
        myTurn = firstPlayer;
    }
    @FXML
    private void initialize() throws IOException {
        if (!initialized) {
            System.out.println("### first Initialize invoked");
            initialized = true;
        } else {
            System.out.println("### second Initialize invoked");
            System.out.println("Wert von feldhorizontal: " + feldhorizontal + " Und Wert von feldvertical: " + feldvertical);
            spieler1 = new Spieler(spieler1Name, 1, POWDERBLUE);
            spieler2 = new Spieler(spieler2Name, 1, PINK);

            Spieler[] spielerarr = {spieler1, spieler2};

            //VierGewinnt Spielfeld + Spieler

            VierGewinntFeld spielfeld = new VierGewinntFeld();
            spielfeld.setHorizontal(feldhorizontal);
            spielfeld.setVertical(feldvertical);
            spielfeld.initializeSpielfeld();

            VierGewinnt vierGewinnt = new VierGewinnt(spielfeld, spielerarr, this);

            for (int i = 0; i < feldhorizontal; i++) {
                int j = 0;
                Polygon pol = new Polygon();
                pol.getPoints().addAll(0.0, 0.0, 45.0, 45.0, 90.0, 0.0);
                pol.setFill(BLACK);
                //pol.setOnMouseClicked()
                pol.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                try {
                                    if (myTurn) {
                                        spielzugAction(pol);
                                        event.consume();
                                    }
                                }catch(IOException e){}
                            }
                        });
                feld.add(pol, i, j);
            }

            for (int i = 0; i < feldhorizontal; i++) {
                for (int j = 1; j <= feldvertical; j++) {
                    Rectangle rec = new Rectangle();
                    rec.setX(0);
                    rec.setY(0);
                    rec.setWidth(90);
                    rec.setHeight(90);
                    rec.setArcWidth(5);
                    rec.setArcHeight(5);
                    rec.setFill(MINTCREAM);
                    feld.add(rec, i, j);
                }
            }
            for (int i = 0; i < feldhorizontal; i++) {
                for (int j = 1; j <= feldvertical; j++) {
                    Circle cir = new Circle();
                    cir.setCenterX(45.0);
                    cir.setCenterY(45.0);
                    cir.setRadius(45.0);
                    cir.setStroke(BLACK);
                    cir.setFill(WHITE);
                    feld.add(cir, i, j);
                }
            }
            feld.setGridLinesVisible(false);
        }
    }
    //TODO: VIERGEWINNTSPIEL STARTEN

    //TODO: Unterscheidung Spieler 1 und Spieler 2


    //Spielzug-Methode, die einen selbst gemachten Spielzug (durch klicken auf Dreieck) auslöst

    public void spielzugAction(Polygon clickedPol) throws IOException{
        //Nur wenn Dreieck schwarz ist, kann Spielzug durchgeführt werden
        if (clickedPol.getFill() == BLACK){
            ObservableList<Node> children = feld.getChildren();

            Integer polRowIndex = GridPane.getRowIndex(clickedPol);
            Integer polColumnIndex = GridPane.getColumnIndex(clickedPol);

            int polRow = polRowIndex == null ? 0 : polRowIndex;
            int polColumn = polColumnIndex == null ? 0 : polColumnIndex;

            //row = y, column = x
            boolean feldGesetzt = false;
            int controlRow = 0;
            while(!feldGesetzt) {
                    controlRow = controlRow + 1;
                    for (Node n : children) {
                        Integer rowIndex = GridPane.getRowIndex(n);
                        Integer columnIndex = GridPane.getColumnIndex(n);

                        int row = rowIndex == null ? 0 : rowIndex;
                        int column = columnIndex == null ? 0 : columnIndex;

                        if (controlRow == row && column == polColumn) {
                            Circle cir = (Circle) n;
                            if (cir.getFill() == WHITE && row == feldvertical) {
                                cir.setFill(spieler1.getFarbe());
                                clickedPol.setFill(LIGHTGREY);
                                feldGesetzt = true;
                            } else if (cir.getFill() == WHITE) {
                                cir.setFill(spieler1.getFarbe());
                                feldGesetzt = true;
                            }
                        }
                    }
            }
            //gemachten Spielzug an den Server schicken
            clientController.send_server_message(polColumn + "-" + controlRow, "555");
            //Spielzug im VierGewinntSpiel im Hintergrund ausführen
            vierGewinnt.spielzug(spieler1, polColumn, controlRow);
            //Zug an Gegner weitergeben
            myTurn = false;
        }
    }

    //Spielzug-Methode die einen vom Gegner ausgeführten Spielzug durchführt
    public void setSpielzug(int polColumn, int polRow){
        System.out.println("###SetSpielzug VierGewinntController");
        //MessageListener ruft diese Methode auf  wenn neuer Spielzug vorliegt
        //Ausführen des reinkommenden Spielzugs
        System.out.println("### children Liste erstellen");
        Task setZugTask = new Task<Void>(){
            @Override public Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ObservableList<Node> children = feld.getChildren();
                        System.out.println("Children Liste erstellt");
                        for (Node n : children) {
                            System.out.println("###For Schleife");
                            Integer rowIndex = GridPane.getRowIndex(n);
                            Integer columnIndex = GridPane.getColumnIndex(n);

                            int row = rowIndex == null ? 0 : rowIndex;
                            int column = columnIndex == null ? 0 : columnIndex;

                            if (row == polRow && column == polColumn) {
                                System.out.println("###If-Bedingung");
                                Circle cir = (Circle) n;
                                if (cir.getFill() == WHITE) {
                                    cir.setFill(spieler2.getFarbe());
                                }
                            }
                        }
                        vierGewinnt.spielzug(spieler2, polColumn, polRow);
                        System.out.println("###MyTurn ändern");
                        //Nach Ausführung des eingehenden Spielzugs sind wir wieder dran
                        myTurn = true;
                    }
                });
                return null;
            }
            //TODO prüfen ob gewonnen oder nicht
        };
        new Thread(setZugTask).start();
    }

    @FXML
    private void startTheGame() throws  IOException{
        Stage stage = (Stage) startButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("VierGewinntFeld.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cancelGame(){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void playerWon(Spieler spieler){

    }

    public void playDrawn(){

    }
}

