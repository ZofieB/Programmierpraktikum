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
import javafx.scene.layout.AnchorPane;
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
    public AnchorPane window;
    @FXML
    private GridPane feld;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button startButton;

    @FXML
    private Button cancel;

    private VierGewinnt vierGewinnt;

    private VierGewinntFeld vierGewinntFeld;

    private static ClientController clientController;

    private static int feldhorizontal;

    private static int feldvertical;

    private static double paramGroesse;

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

    public void setParameters(ClientController newClientController, int newFeldvertical, int newFeldhorizontal, String nutzername, String opponent,boolean startSpieler) {
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
        if (!initialized) {
            System.out.println("### first Initialize invoked");
            initialized = true;
        } else {
            System.out.println("### second Initialize invoked");
            //Set the Reference for the new Controller in ClientController
            clientController.setVierGewinntController(this);

            System.out.println("Wert von feldhorizontal: " + feldhorizontal + " Und Wert von feldvertical: " + feldvertical);

            spieler1 = new Spieler(spieler1Name, 1, POWDERBLUE);
            spieler2 = new Spieler(spieler2Name, 1, PINK);

            Spieler[] spielerarr = {spieler1, spieler2};

            //VierGewinnt Spielfeld + Spieler

            VierGewinntFeld spielfeld = new VierGewinntFeld();
            spielfeld.setHorizontal(feldhorizontal);
            spielfeld.setVertical(feldvertical);
            spielfeld.initializeSpielfeld();
            spielfeld.printSpielfeld();

            if (feldvertical * 2 < feldhorizontal){
                paramGroesse = 790.00 / feldhorizontal;
            }
            else paramGroesse = 540.00 / (feldvertical + 0.50);
            //}
            window.setPrefHeight(paramGroesse * feldvertical + 10.0);
            window.setPrefWidth(paramGroesse * feldhorizontal + 10.0);
            feld.setPrefHeight(paramGroesse * feldvertical + paramGroesse / 2);
            feld.setPrefWidth(paramGroesse * feldhorizontal);
            cancel.setLayoutX(paramGroesse * feldhorizontal + 10.0);
            cancel.setLayoutY(paramGroesse * feldvertical + paramGroesse / 2 + 10.0);

            vierGewinnt = new VierGewinnt(spielfeld, spielerarr, this);




            /*for (int i = 0; i < feldhorizontal; i++) {
                for (int j = 1; j <= feldvertical; j++) {
                    Rectangle rec = new Rectangle();
                    rec.setX(0);
                    rec.setY(0);
                    rec.setWidth(paramGroesse);
                    rec.setHeight(paramGroesse);
                    rec.setArcWidth(5);
                    rec.setArcHeight(5);
                    rec.setFill(MINTCREAM);
                    feld.add(rec, i, j);
                }
            }*/
            for (int i = 0; i < feldhorizontal; i++) {
                for (int j = 1; j <= feldvertical; j++) {
                    Circle cir = new Circle();
                    cir.setCenterX(paramGroesse/2);
                    cir.setCenterY(paramGroesse/2);
                    cir.setRadius((paramGroesse/2)-0.5);
                    cir.setStroke(BLACK);
                    cir.setFill(WHITE);
                    feld.add(cir, i, j);
                }
            }


            for (int i = 0; i < feldhorizontal; i++) {
                int j = 0;
                Polygon pol = new Polygon();
                pol.getPoints().addAll(0.0, 0.0, paramGroesse/2, paramGroesse/2, paramGroesse, 0.0);
                pol.setFill(BLACK);
                //pol.setOnMouseClicked();
                pol.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                try {
                                    if(myTurn) {
                                        spielzugAction(pol);
                                        event.consume();
                                    }
                                }catch(IOException e){}
                            }
                        });
                feld.add(pol, i, j);
            }
            feld.setGridLinesVisible(false);
        }
    }


    //Spielzug-Methode, die einen selbst gemachten Spielzug (durch klicken auf Dreieck) auslöst

    public void spielzugAction(Polygon clickedPol) throws IOException {
        int game = 2;
        System.out.println("### da wird was angeklickt (Vier Gewinnt)");
        //Nur wenn Dreieck schwarz ist, kann Spielzug durchgeführt werden
        if (clickedPol.getFill() == BLACK) {
            ObservableList<Node> children = feld.getChildren();

            Integer polRowIndex = GridPane.getRowIndex(clickedPol);
            Integer polColumnIndex = GridPane.getColumnIndex(clickedPol);

            int polRow = polRowIndex == null ? 0 : polRowIndex;
            int polColumn = polColumnIndex == null ? 0 : polColumnIndex;

            //row = y, column = x
            boolean feldGesetzt = false;
            int controlRow = feldvertical+1;
            while (!feldGesetzt) {
                controlRow = controlRow - 1;
                for (Node n : children) {
                    Integer rowIndex = GridPane.getRowIndex(n);
                    Integer columnIndex = GridPane.getColumnIndex(n);

                    int row = rowIndex == null ? 0 : rowIndex;
                    int column = columnIndex == null ? 0 : columnIndex;

                    if (controlRow == row && column == polColumn) {
                        //Rectangle rec = (Rectangle) n;
                        //rec.getFill();
                        Circle cir = (Circle) n;
                        if (cir.getFill() == WHITE && row == 1) {
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
            clientController.send_server_message(polColumn + "-" + controlRow + "-" + game, "555");
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
                            //System.out.println("###For Schleife");
                            Integer rowIndex = GridPane.getRowIndex(n);
                            Integer columnIndex = GridPane.getColumnIndex(n);

                            int row = rowIndex == null ? 0 : rowIndex;
                            int column = columnIndex == null ? 0 : columnIndex;

                            if (row == polRow && column == polColumn) {
                                //System.out.println("###If-Bedingung");
                                Circle cir = (Circle) n;
                                if (cir.getFill() == WHITE && row == 1) {
                                    cir.setFill(spieler2.getFarbe());
                                    for (Node x : children) {
                                        //System.out.println("###For Schleife");
                                        Integer rowIndexPol = GridPane.getRowIndex(x);
                                        Integer columnIndexPol = GridPane.getColumnIndex(x);

                                        int rowPol = rowIndex == null ? 0 : rowIndexPol;
                                        int columnPol = columnIndex == null ? 0 : columnIndexPol;

                                        if (rowPol == 0 && columnPol == column) {
                                            Polygon pol = (Polygon) x;
                                            pol.setFill(LIGHTGREY);
                                        }
                                    }
                                }
                                else if (cir.getFill() == WHITE) {
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
    private void cancelGame() throws IOException{
        clientController.send_server_message("Vier Gewinnt", "560");
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void playerWon(Spieler spieler){
        Task cancelGame = new Task<Void>(){
            @Override public Void call(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        try {
                            if (spieler.getSpielername().equals(spieler1.getSpielername())) {
                                //Dieser Spieler ist derjenige der gewonnen hat
                                Stage stage = (Stage) cancel.getScene().getWindow();
                                stage.close();
                                clientController.send_server_message("", "566");
                            } else {
                                //Der Gegner hat gewonnen
                                Stage stage = (Stage) cancel.getScene().getWindow();
                                stage.close();
                                clientController.send_server_message("", "565");
                            }
                        }catch(Exception e){}
                    }
                });
                return null;
            }
        };
        new Thread(cancelGame).start();
    }



    public void playDrawn(){
        Task cancelGame = new Task<Void>(){
            @Override public Void call(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        try {
                                Stage stage = (Stage) cancel.getScene().getWindow();
                                stage.close();
                                clientController.send_server_message("", "567");
                        }catch(Exception e){}
                    }
                });
                return null;
            }
        };
        new Thread(cancelGame).start();
    }



    public void gameGotCanceled(){
        Task cancelGame = new Task<Void>(){
            @Override public Void call(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        Stage stage = (Stage) cancel.getScene().getWindow();
                        stage.close();
                    }
                });
                return null;
            }
        };
        new Thread(cancelGame).start();
    }

}

