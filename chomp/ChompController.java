package chomp;

import absclasses.Spieler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

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

    // Add a public no-args constructor
    public ChompController() {
    }
    @FXML
    private void initialize() throws IOException {
        //Größe Rectangles Parametrisieren auf Eingabe!!
        for(int k = 0; k < 9; k++){
            Polygon rec = new Polygon();
            rec.getPoints().addAll(0.0, 0.0,
                    45.0, 45.0,
                    90.0, 0.0);
            rec.setFill(DARKGREEN);
            feld.add(rec, k, 0);
        }
        for(int i = 0; i < /*chompFeld.getHorizontal()*/9; i++){
            for(int j = 1; j < /*chompFeld.getVertical()*/6; j++) {
                Rectangle rec = new Rectangle();
                rec.setX(0);
                rec.setY(0);
                rec.setWidth(90);
                rec.setHeight(90);
                rec.setArcWidth(5);
                rec.setArcHeight(5);
                rec.setFill(MINTCREAM);
                //rec.setOnMouseClicked(); <-- noch zu machen
                feld.add(rec, i, j);
            }
        }
    }

    public void setFeld(Spieler spieler, int x, int y){
        ObservableList<Node> children = feld.getChildren();
        for(Node n : children){
            if(feld.getRowIndex(n) == y && feld.getColumnIndex(n) == x){
                Rectangle rec = (Rectangle) n;
                rec.setFill(spieler.getFarbe());
            }
        }
    }

    public void changeColor(){
        //testmethode
    }



}
