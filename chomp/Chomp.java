package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{


    public void addSpielzug(Spielzug spielzug) {
        //hier entsteht ein spielzug
    }
    public void removeSpielzug(Spielzug spielzug) {

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
    }

}