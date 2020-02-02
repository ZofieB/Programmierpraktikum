package viergewinnt;

import absclasses.Spieler;
import absclasses.Spielzug;
import chomp.Chomp;
import chomp.ChompFeld;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
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

    // Add a public no-args constructor
    public VierGewinntController() {
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
                                spielzugAction(pol);
                                event.consume();
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
                    //Ausprobieren von Farben um zu schauen, ob man von denen Augenkrebs bekommt und welche am besten miteinander harmonieren
                    /*if (i == 0 && j == 2){
                        cir.setFill(LIGHTPINK);
                    }
                    else if (i == 0 && j == 1){
                        cir.setFill(LIGHTBLUE);
                    }
                    else if (i == 1 && j ==3){
                        cir.setFill(LIGHTGOLDENRODYELLOW);
                    }
                    else if (i == 1 && j == 1){
                        cir.setFill(PALEGREEN);
                    }
                    else if (i == 1 && j == 2){
                        cir.setFill(PALETURQUOISE);
                    }
                    else if (i == 5 && j == 3){
                        cir.setFill(POWDERBLUE);
                    }
                    else if (i == 5 && j == 1){
                        cir.setFill(PINK);
                    }
                    else if (i == 5 && j == 2){
                        cir.setFill(PLUM);
                    }*/
                    //else {}
                    cir.setFill(WHITE);
                    feld.add(cir, i, j);
                }
            }
        }
    }

    public void setFeld(Spieler spieler, int x, int y) throws IOException {
        ObservableList<Node> children = feld.getChildren();
        //cast von group zu rectangle nicht möglich! TODO
        for (Node n : children) {
            if (feld.getRowIndex(n) == y && feld.getColumnIndex(n) == x) {
                Circle cir = (Circle) n;
                cir.setFill(spieler.getFarbe());
                //gemachten Spielzug an den Server schicken
                clientController.send_server_message("", "");
            }
        }
    }

    public void spielzugAction(Polygon pol) {
        ObservableList<javafx.scene.Node> children = feld.getChildren();
        //Koordinaten des angeklickten Polygons bekommen
        int x = feld.getColumnIndex(pol);
        int y = feld.getRowIndex(pol);

        for (; y <= feldvertical; y++) {
            for (javafx.scene.Node node : children) {
                Integer rowIndex = GridPane.getRowIndex(node);

                // handle null values for index=0
                int r = rowIndex == null ? 0 : rowIndex;

                Integer colIndex = GridPane.getColumnIndex(node);

                // handle null values for index=0
                int c = colIndex == null ? 0 : colIndex;

                if (x == c) {
                    Circle newcir = new Circle();
                    newcir = (Circle) node;
                    if (newcir.getFill() == WHITE) {
                        newcir.setFill(spieler1.getFarbe());
                    }

                }
            }
        }
    }

    private void setSpielzug(Spielzug spielzug) {
        //MessageListener ruft diese Methode auf  wenn neuer Spielzug vorliegt
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
    }
}

