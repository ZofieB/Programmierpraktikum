package chomp;

import absclasses.Spieler;
import absclasses.Spielzug;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import serverclient.ClientController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;


public class ChompController {
    @FXML
    private GridPane feld;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;

    private Chomp chomp;

    private ChompFeld chompFeld;

    private ClientController clientController;

    private int feldhorizontal;

    private int feldvertical;

    private Spieler spieler1;

    private Spieler spieler2;

    // Add a public no-args constructor
    public ChompController() {
    }
    @FXML
    private void initialize() throws IOException {
        //Referenz für clientcontroller herstellen und Eingaben holen
        clientController = ClientController.thisController;
        feldhorizontal = clientController.getHorizontalField();
        feldvertical = clientController.getVerticalField();

        spieler1 = new Spieler(clientController.getNutzername(), 1, BLUE);
        spieler2 = new Spieler(clientController.getGameOpponent(), 1, GREEN);

        Spieler[] spielerarr = {spieler1, spieler2};

        //Create Chomp game field and spieler

        ChompFeld spielfeld = new ChompFeld();
        spielfeld.setHorizontal(feldhorizontal);
        spielfeld.setVertical(feldvertical);
        spielfeld.initializeSpielfeld();

        Chomp chomp = new Chomp(spielfeld, spielerarr, this);
        //Größe Rectangles Parametrisieren auf Eingabe!!
        for(int i = 0; i < feldhorizontal; i++){
            for(int j = 1; j < feldvertical; j++) {
                Rectangle rec = new Rectangle();
                rec.setX(0);
                rec.setY(0);
                rec.setWidth(90);
                rec.setHeight(90);
                rec.setArcWidth(5);
                rec.setArcHeight(5);
                rec.setFill(MINTCREAM);
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
        while(!chomp.getPlayerlost()) {
            chomp.durchgang();
        }
    }

    public void setFeld(Spieler spieler, int x, int y) throws IOException{
        ObservableList<Node> children = feld.getChildren();
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
        chomp.spielzug(spieler1, x, y);
    }

    private void setSpielzug(Spielzug spielzug){
        //MessageListener ruft diese Methode auf  wenn neuer Spielzug vorliegt
    }



}
