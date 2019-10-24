package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    protected ChompFeld feld = new ChompFeld(); 

    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }

    public void removeSpielzug() {
        szstack.pop();
    }

    public void executeSpielzug(ChompSpielzug spielzug) {
        //Koordinate in ArrayIndizes, vertical und horizontal sind absolute Längen
        for ( int i = 0/*spielzug.getYkoordinate()*/; i < feld.getVertical(); i++) {
            for ( int j = 0/*spielzug.getXkoordinate()*/; j < feld.getHorizontal(); j++) {
                feld.changeCoordinates(i, j, 1);
                System.out.println("Schleifendurchlauf");
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
        Chomp chomp = new Chomp();
        ChompFeld feld = new ChompFeld();
        feld.setHorizontal(3);
        feld.setVertical(2);
        feld.initializeSpielfeld();
        feld.printSpielfeld();

        System.out.println("Jetzt wird ein Spielzug gemacht!");

        ChompSpielzug spielzug = new ChompSpielzug(1, 1);
        //System.out.println(spielzug.getXkoordinate());
        chomp.executeSpielzug(spielzug);
    }

}