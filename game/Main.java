package game;

import chomp.*;
import absclasses.*;

public class Main{
    public static void main(String[] args){
        System.out.println("Welches Spiel soll gespielt werden?");
        System.out.println("0 für Chomp");
        System.out.println("1 für VierGewinnt");
        
        int game = 0; //muss eingelesen werden!
        
        //hier kommt dann ein Input
        if(game == 0) { //hier wird Chomp gespielt

        }
        else if(game == 1) { //hier wird Vier Gewinnt gespielt

        }
        else {
            System.out.println("Das war keine gültige Eingabe!");
        }
        
        /* ChompFeld feld = new ChompFeld();
        Chomp chomp = new Chomp(feld);
        feld.setHorizontal(3);
        feld.setVertical(2);
        feld.initializeSpielfeld();
        feld.printSpielfeld();

        System.out.println("Jetzt wird ein Spielzug gemacht!");

        ChompSpielzug spielzug = new ChompSpielzug(1, 0);
        chomp.executeSpielzug(spielzug);
        feld.printSpielfeld();*/
    }
}