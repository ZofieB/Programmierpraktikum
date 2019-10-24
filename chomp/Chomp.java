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

    public void chomp() {
        
    }
}