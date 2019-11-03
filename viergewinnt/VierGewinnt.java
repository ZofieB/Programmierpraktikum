package viergewinnt;

import absclasses.*;
import java.util.*;

public class VierGewinnt extends Spiel implements Protokollierbar {
    protected VierGewinntFeld feld = new VierGewinntFeld();
    private boolean playerlost = false;

    public VierGewinnt(VierGewinntFeld feld){
        this.feld = feld;
    }
    
    @Override
    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }
    
    @Override
    public Spielzug removeSpielzug() {
        return szstack.pop();
    }

    public void setPlayerLost() {
        this.playerlost = true;
    }

    public void executeSpielzug(Spielzug spielzug) {
        feld.changeCoordinates(j, i, spielernummer); //muss unbedingt schauen, wie ich das machen kann mit Spielernummer, vllt durch Anzahl der Spielzüge
    }

    public void spielzug(Spieler spieler) {}

    public void spielzug(Spieler spieler, int spielernummer) { 
        int i = 0;  
        System.out.println(spieler.getSpielername() + " ist dran!");
        if(spieler.getSpielerart() == 1){ //Ein richtiger Spieler spielt
            Scanner scan = new Scanner(System.in);
            // Koordinaten einlesen
            int x = feld.getHorizontal() - 1;
            System.out.println("Gib bitte die x-Koordinate deines Zuges ein:");
            int neuerZug = 1;
            do{
                x = scan.nextInt();
                scan.nextLine();                
                for(; feld.checkBesetzt(i, x) && i < feld.getVertical(); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
                }       
                if (i == feld.getVertical()){
                    System.out.println("Du musst eine freie Spalte nehmen! Wiederhole die Eingabe:");
                    neuerZug = 1;
                    //an dieser Stelle nochmal Funktion aufrufen, mit neuem x Wert
                }
                else neuerZug = 0;
            } while(neuerZug == 1);
            //Spielzug daraus erstellen
            Spielzug spielzug = new Spielzug(x, i, spieler);
            //Spielzug ausführen
            executeSpielzug(spielzug);
            addSpielzug(spielzug);
            //ein Spieler hat verloren, muss noch gemacht werden
            if (feld.checkGewonnen(x, i) == true){
                setPlayerLost();
            }
        }
        else { //der Computer spielt
            if(szstack.empty()){
                Spielzug spielzug = new Spielzug((feld.getHorizontal() - 1), (feld.getVertical() - 1), spieler);
            }
            else{
                //Zug des letzten Spielers auslesen
                Spielzug letzterZug = removeSpielzug();
                //Zug wieder auf Stack bringen
                addSpielzug(letzterZug);
            }
        System.out.println();
        feld.printSpielfeld();
        }
    }

    @Override
    public void durchgang() {
        if(!playerlost) {
            spielzug(spieler[0], 1);
        }
        if(!playerlost) {
            spielzug(spieler[1], 2);
        }
    }


    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }
}

