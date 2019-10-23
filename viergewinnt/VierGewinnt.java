package viergewinnt;

import absclasses.*;
import java.util.*;

public class VierGewinnt extends Spiel implements Protokollierbar {

    public void addSpielzug(Spielzug spielzug) {
        //push Befehl
    }

    public void removeSpielzug(Spielzug spielzug) {
        //pop Befehl
    }

    public void executeSpielzug(Spielzug spielzug) {
        for(i == 1; i <= veritcal-1; i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
            //if (feld [i][xkoordinate])
        }
    }

    public void spielzug() {

    }

    public void durchgang() {

    }

    public Stack getSzstack() {
        return this.szstack;
    }

    public static void main(String args[]) {
        VierGewinntFeld feld = new VierGewinntFeld();
        feld.setHorizontal(3);
        feld.setVertical(2);
        feld.initializeSpielfeld();
        feld.printSpielfeld();
    }
}

