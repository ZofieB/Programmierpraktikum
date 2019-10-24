package Game;

import chomp.*;
import absclasses.*;

public class Main{
    public static void main(String[] args){
        ChompFeld feld = new ChompFeld();
        Chomp chomp = new Chomp(feld);
        feld.setHorizontal(3);
        feld.setVertical(2);
        feld.initializeSpielfeld();
        feld.printSpielfeld();

        System.out.println("Jetzt wird ein Spielzug gemacht!");

        ChompSpielzug spielzug = new ChompSpielzug(1, 0);
        chomp.executeSpielzug(spielzug);
        feld.printSpielfeld();
    }
}