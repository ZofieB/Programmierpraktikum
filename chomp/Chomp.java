package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    protected ChompFeld feld = new ChompFeld();

    public Chomp(ChompFeld feld) {
        this.feld = feld;
    }

    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }

    public void removeSpielzug() {
        szstack.pop();
    }

    public void executeSpielzug(ChompSpielzug spielzug) {
        //Koordinate in ArrayIndizes, vertical und horizontal sind absolute Längen
        
        if(feld.getValue(spielzug.getXkoordinate(), spielzug.getYkoordinate()) == 0) {
            for ( int i = spielzug.getYkoordinate(); i < feld.getVertical(); i++) {
                for ( int j = spielzug.getXkoordinate(); j < feld.getHorizontal(); j++) {
                    feld.changeCoordinates(i, j, 1);
                }
            }
        }
    }
    public void spielzug() {

        //Ein Spieler hat verloren
        if(feld.getValue(0, 0) == 1) {
            
        }
    }
    public void durchgang() {

    }
    public Stack getSzstack(){
        return this.szstack;
    }

    public void chomp() {

    }
}