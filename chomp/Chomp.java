package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    ChompFeld feld = new ChompFeld(); 

    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }

    public void removeSpielzug() {
        szstack.pop();
    }

    public void executeSpielzug(Spielzug spielzug) {
        //Koordinate in ArrayIndizes, vertical und horizontal sind absolute Längen
        for ( int i = spielzug.getYkoordinate(); i < feld.getVertical(); i++) {
            for ( int j = spielzug.getXkoordinate(); j < feld.getHorizontal(); j++) {
                feld.changeCoordinates(i, j, 1);
            }
        }
    }
    public void spielzug() {

    }
    public void durchgang() {

    }
    public Stack getSzstack(){
        return this.szstack;
    }

    public static void main(String args[]) {
        ChompFeld feld = new ChompFeld();
        feld.setHorizontal(3);
        feld.setVertical(2);
        feld.initializeSpielfeld();
        feld.printSpielfeld();
        //Hier ist eine kleine Änderung
    }

}