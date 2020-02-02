package game;

import chomp.*;
import absclasses.*;
import java.util.*;
import viergewinnt.*;

/*public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        //Auswahl der Spieler
        //Spieler 1
        System.out.println("Auswahl der Spieler:");
        System.out.println("Spieler 1: \nSpielerart: \n0 für Computer\n1 für echter Spieler");
        int spielerart = scan.nextInt();
        scan.nextLine();
        Spieler spieler1 = new Spieler();
        if(spielerart == 1){
            spieler1.setSpielerart(spielerart);
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
        System.out.println("Spieler 2: \nSpielerart:\n0 für Computer\n1 für echter Spieler");
        spielerart = scan.nextInt();
        scan.nextLine();
        Spieler spieler2 = new Spieler();
        if(spielerart == 1){
            spieler2.setSpielerart(spielerart);
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
            spielfeld.initializeSpielfeld();
            spielfeld.printSpielfeld();

            Chomp chomp = new Chomp(spielfeld, spielerarr, chomp.ChompController);
            while(!chomp.getPlayerlost()) {
                chomp.durchgang();
            }
            //Verlierenden Spieler ausgeben
            Spielzug letzterZug = chomp.removeSpielzug();
            Spieler verlierer = letzterZug.getSpieler();
            System.out.println(verlierer.getSpielername() + " hat verloren!");
        }
        else if(game == 1) { //hier wird Vier Gewinnt gespielt
            VierGewinntFeld spielfeld = new VierGewinntFeld();
            System.out.println("Wie groß soll das Spielfeld sein?");
            //Horizontal
            int richtigeLänge = 0;
            do{
                System.out.println("Horizontale Länge:");
                int feldhorizontal = scan.nextInt();
                if (feldhorizontal < 4){
                    System.out.println("Die horizontale Länge darf nicht kleiner als 4 sein. Gebe eine mögliche Länge ein.");
                }
                else {
                    richtigeLänge = 1;
                    spielfeld.setHorizontal(feldhorizontal);
                }
            } while (richtigeLänge == 0);
            //Vertikal
            do{
                System.out.println("Vertikale Länge:");
                int feldvertical = scan.nextInt();
                if (feldvertical < 4){
                    System.out.println("Die vertikale Länge darf nicht kleiner als 4 sein. Gebe eine mögliche Länge ein.");
                }
                else {
                    richtigeLänge = 0;
                    spielfeld.setVertical(feldvertical);
                }
            } while (richtigeLänge == 1);
            
            spielfeld.initializeSpielfeld();
            spielfeld.printSpielfeld();

            VierGewinnt viergewinnt = new VierGewinnt(spielfeld, spielerarr, viergewinnt.VierGewinntController);
            while(!viergewinnt.getPlayerwin() && !viergewinnt.getPlayunentschieden()) {
                viergewinnt.durchgang();
            }
            //Gewinnenden Spieler ausgeben
            if (viergewinnt.getPlayerwin()){
                Spielzug letzterZug = viergewinnt.removeSpielzug();
                Spieler gewinner = letzterZug.getSpieler();
                System.out.println(gewinner.getSpielername() + " hat gewonnen!");
            }
            //Unentschieden ausgeben
            if (viergewinnt.getPlayunentschieden()){
                System.out.println("Es ist unentschieden!");
            }
        }
        else {
            System.out.println("Das war keine gültige Eingabe!");
        }
        scan.close();
    }
}*/