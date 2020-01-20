package viergewinnt;

import absclasses.Spieler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;


public class VierGewinntController{
    @FXML
    private GridPane feld;
    @FXML
    private URL location;
    @FXML
    private ResourceBundle resources;



    @FXML
    private void initialize() throws IOException {
        //Größe später parametisieren auf Eingabe
        for (int i = 0; i < 9; i++){
            int j = 0;
            Polygon pol = new Polygon();
            pol.getPoints().addAll(0.0, 0.0, 45.0, 45.0, 90.0, 0.0);
            pol.setFill(BLACK);
            feld.add(pol, i, j);
        }
        for (int i = 0; i < 9; i++){
            for (int j = 1; j < 6; j++){
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
        for (int i = 0; i < 9; i++){
            for (int j = 1; j < 6; j++){
                Circle cir = new Circle();
                cir.setCenterX(45.0);
                cir.setCenterY(45.0);
                cir.setRadius(45.0);
                cir.setStroke(BLACK);
                //Ausprobieren von Farben um zu schauen, ob man von denen Augenkrebs bekommt und welche am besten miteinander harmonieren
                if (i == 0 && j == 2){
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
                }
                else {cir.setFill(WHITE);}
                feld.add(cir, i, j);
            }
        }
    }

    public void setFeld(Spieler spieler, int x, int y){
        ObservableList<Node>children = feld.getChildren();
        for(Node n : children){
            if(feld.getRowIndex(n) == y && feld.getColumnIndex(n) == x){
                Circle cir = (Circle) n;
                cir.setFill(spieler.getFarbe());
            }
        }
    }
}
