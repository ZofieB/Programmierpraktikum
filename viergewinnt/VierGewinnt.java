package viergewinnt;

import absclasses.*;
import java.util.*;

public class VierGewinnt extends Spiel implements Protokollierbar {
    protected VierGewinntFeld feld = new VierGewinntFeld();

    public VierGewinnt(VierGewinntFeld feld){
        this.feld = feld;
    }

    
    @Override
    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }
    
    @Override
    public void removeSpielzug(Spielzug spielzug) {
        return szstack.pop(spielzug);
    }

    public void executeSpielzug(Spielzug spielzug) {
        int i = 0;
        for(; feld.checkBesetzt(i, x) && i < feld.getVertical(); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
        }       
        if (i == feld.getVertical()){
            System.out.println("In dieser Spalte gibt es kein Platz mehr, versuch es nochmal an einer anderen Stelle!")
            //an dieser Stelle nochmal Funktion aufrufen, mit neuem x Wert
        }
        else if (i < feld.getVertical()){
            //Anhand von der Anzahl der bereits getanen Spielzüge abschätzen, ob es sich um Spieler 1 handelt oder um Spieler 2, je nachdem Wert von feld(i,x) = Spieler
        }
        
    }

    public void spielzug() { 
        int i = 0;  
        System.out.println(spieler.getSpielername() + " ist dran!");
        if(spieler.getSpielerart() == 1){ //Ein richtiger Spieler spielt
            Scanner scan = new Scanner(System.in);
            // Koordinaten einlesen
            int x = feld.getHorizontal() - 1;
            int y = feld.getVertical() -1 ;
            System.out.println("Gib bitte die x Koordinate deines Zuges ein:");
            do{
                x = scan.nextInt();
                scan.nextLine();
               
                
                for(; feld.checkBesetzt(i, x) && i < feld.getVertical(); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
                }       
                int neuerZug = 0;
                if (i == feld.getVertical()){
                    System.out.println("Du musst eine freie Spalte nehmen! Wiederhole die Eingabe:");
                    neuerZug = 1;
                    //an dieser Stelle nochmal Funktion aufrufen, mit neuem x Wert
                }
            } while(neuerZug == 1);
        //Spielzug daraus erstellen
        VierGewinntSpielzug spielzug = new VierGewinntSpielzug(x, i, spieler);
        //Spielzug ausführen
        executeSpielzug(spielzug);
        addSpielzug(spielzug);
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

