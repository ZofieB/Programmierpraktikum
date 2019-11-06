package viergewinnt;

import absclasses.*;
import java.util.*;

public class VierGewinnt extends Spiel implements Protokollierbar {
    protected VierGewinntFeld feld = new VierGewinntFeld();
    private boolean playerwin = false;

    public VierGewinnt(VierGewinntFeld feld, Spieler[] spieler) {
        this.feld = feld;
        this.spieler = spieler;
        
    }

    public void setPlayerwin() {
        this.playerwin = true;
    }

    public boolean getPlayerwin() {
        return this.playerwin;
    }
    
    @Override
    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }
    
    @Override
    public Spielzug removeSpielzug() {
        return szstack.pop();
    }

    public void executeSpielzug(Spieler spieler, Spielzug spielzug) {
        int number;
        if(szstack.empty()){
            number = 1;
        }
        else{
            //Zug des letzten Spielers auslesen
            Spielzug letzterZug = removeSpielzug();
            //Zug wieder auf Stack bringen
            addSpielzug(letzterZug);
            if (feld.checkNumber(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) == true){
                number = 2;
            }
            else number = 1;
        }
        feld.changeCoordinates(spielzug.getXkoordinate(), spielzug.getYkoordinate(), number); //muss unbedingt schauen, wie ich das machen kann mit Spielernummer, vllt durch Anzahl der Spielzüge
    }

    

    public void spielzug(Spieler spieler) { 
        int i = 0;  
        System.out.println(spieler.getSpielername() + " ist dran!");
        if(spieler.getSpielerart() == 1){ //Ein richtiger Spieler spielt
            Scanner scan = new Scanner(System.in);
            // Koordinaten einlesen
            int x = feld.getHorizontal() - 1;
            System.out.println("Gib bitte die x-Koordinate deines Zuges ein:");
            int neuerZug = 1;
            do{
                i = 0;
                x = scan.nextInt();
                scan.nextLine();                
                for(; i < feld.getVertical() && feld.checkBesetzt(x - 1, i); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
                }       
                if (i == feld.getVertical()){
                    System.out.println("Du musst eine freie Spalte nehmen! Wiederhole die Eingabe:");
                    neuerZug = 1;
                    //an dieser Stelle nochmal Funktion aufrufen, mit neuem x Wert
                }
                else neuerZug = 0;
            } while(neuerZug == 1);
            //scan.close();
            //Spielzug daraus erstellen
            Spielzug spielzug = new Spielzug(x - 1, i, spieler);
            //Spielzug ausführen
            executeSpielzug(spieler, spielzug);
            addSpielzug(spielzug);
            feld.printSpielfeld();
            //ein Spieler hat verloren
            if (feld.checkGewonnen(x - 1, i) == true){
                setPlayerwin();
            }
        }
        else { //der Computer spielt
            if(szstack.empty()){
                Spielzug spielzug = new Spielzug((feld.getHorizontal() / 2) - 1, 0, spieler);
                executeSpielzug(spieler, spielzug);
                addSpielzug(spielzug);
            }
            else{
                int oben = 0;
                int rechts = 0;
                int links = 0;
                int linksrueber1 = 0;
                int rechtsrueber1 = 0;
                int linksrueber2 = 0;
                int rechtsrueber2 = 0;
                int safe = 0;
                int newX = 0;
                int newY = 0;
                //Zug des letzten Spielers auslesen
                Spielzug letzterZug = removeSpielzug();
                //Zug wieder auf Stack bringen
                addSpielzug(letzterZug);
                feld.checkGefahren(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                if (oben != 0){
                    newX = letzterZug.getXkoordinate();
                    newY = letzterZug.getYkoordinate() + 1;
                }
                if (rechts != 0){
                    newX = rechts - 1;
                    newY = letzterZug.getYkoordinate();
                }
                if (links != 0){
                    newX = links - 1;
                    newY = letzterZug.getYkoordinate();
                }
                if (linksrueber1 != 0){
                    newX = linksrueber1 - 1;
                    newY = letzterZug.getYkoordinate() - (letzterZug.getXkoordinate() - linksrueber1 - 1);
                }
                if (rechtsrueber1 != 0){
                    newX = rechtsrueber1 - 1;
                    newY = rechtsrueber1 - 1 - letzterZug.getXkoordinate() + letzterZug.getYkoordinate();
                }
                if (linksrueber2 != 0){
                    newX = linksrueber2 - 1;
                    newY = letzterZug.getXkoordinate() - linksrueber2 - 1 + letzterZug.getYkoordinate();
                }
                if (rechtsrueber2 != 0){
                    newX = rechtsrueber2 - 1;
                    newY = letzterZug.getYkoordinate() - (rechtsrueber2 - 1 - letzterZug.getXkoordinate());
                }
                if (safe == 1){
                    int neuerZug = 1;
                    newX = letzterZug.getXkoordinate() + 1;
                    do{               
                        for(; feld.checkBesetzt(newX, i) && i < feld.getVertical(); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
                        }       
                        if (i == feld.getVertical()){
                            neuerZug = 1;
                            newX = newX + 1;
                            if (newX == feld.getHorizontal()){
                                newX = 0;
                            }
                            //an dieser Stelle nochmal Funktion aufrufen, mit neuem x Wert, falls Spalte voll ist
                        }
                        else {
                            neuerZug = 0;
                            newY = i;
                        }
                    } while(neuerZug == 1);
                }
                Spielzug spielzug = new Spielzug(newX, newY, spieler);
                executeSpielzug(spieler, spielzug);
                addSpielzug(spielzug);
                if (feld.checkGewonnen(newX, newY) == true){
                    setPlayerwin();
                }
            }
        System.out.println();
        feld.printSpielfeld();
        }
    }

    @Override
    public void durchgang() {
        if(!playerwin) {
            spielzug(spieler[0]);
        }
        if(!playerwin) {
            spielzug(spieler[1]);
        }
    }


    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }
}

