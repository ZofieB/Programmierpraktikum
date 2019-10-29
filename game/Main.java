package game;

import chomp.*;
import absclasses.*;
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        //Auswahl der Spieler
        //Spieler 1
        System.out.println("Auswahl der Spieler:");
        System.out.println("Spieler 1: \nSpielerart: 0 für Computer\n1 für echter Spieler");
        int spielerart = scan.nextInt();
        scan.nextLine();
        Spieler spieler1 = new Spieler();
        spieler1.setSpielerart(spielerart);
        if(spielerart == 1){
            System.out.println("Wie soll der Spieler heißen?");
            String spieler = scan.nextLine();
            spieler1.setSpielername(spieler);
        }
        else if (spielerart == 0){
            spieler1.setSpielername("Spieler 1 AI");
        }
        else{
            System.out.println("Diese Eingabe war nicht gültig!");
        }

        //Spieler 2
        System.out.println("Spieler 2: \nSpielerart: 0 für Computer\n1 für echter Spieler");
        spielerart = scan.nextInt();
        scan.nextLine();
        Spieler spieler2 = new Spieler();
        spieler2.setSpielerart(spielerart);
        if(spielerart == 1){
            System.out.println("Wie soll der Spieler heißen?");
            String spieler = scan.nextLine();
            spieler2.setSpielername(spieler);
        }
        else if (spielerart == 0){
            spieler2.setSpielername("Spieler 2 AI");
        }
        else {
            System.out.println("Diese Eingabe war nicht gültig!");
        }

        //Erstellung des Spielerarrays
        Spieler[] spielerarr = {spieler1, spieler2};


        //Auswahl des Spiels
        System.out.println("Welches Spiel soll gespielt werden?");
        System.out.println("0 für Chomp");
        System.out.println("1 für VierGewinnt");
        int game = scan.nextInt();

        if(game == 0) { 
            ChompFeld spielfeld = new ChompFeld();
            System.out.println("Wie groß soll das Spielfeld sein?");
            //Horizontal
            System.out.println("Horizontale Länge:");
            int feldhorizontal = scan.nextInt();
            //Vertikal
            System.out.println("Vertikale Länge:");
            int feldvertical = scan.nextInt();

            spielfeld.setHorizontal(feldhorizontal);
            spielfeld.setVertical(feldvertical);

            Chomp chomp = new Chomp(spielfeld, spielerarr);
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