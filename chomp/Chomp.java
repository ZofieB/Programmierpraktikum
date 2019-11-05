package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    protected ChompFeld feld = new ChompFeld();
    private boolean playerlost = false;
    

    public Chomp(ChompFeld feld, Spieler[] spieler) {
        this.feld = feld;
        this.spieler = spieler;
    }

    public void setPlayerlost() {
        this.playerlost = true;
    }
    public boolean getPlayerlost() {
        return this.playerlost;
    }

    @Override
    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }
    @Override
    public Spielzug removeSpielzug() {
        return szstack.pop();
    }

    public void executeSpielzug(Spielzug spielzug) {
        //Koordinate in ArrayIndizes, vertical und horizontal sind absolute Längen
        for ( int i = spielzug.getYkoordinate(); i < feld.getVertical(); i++) {
            for ( int j = spielzug.getXkoordinate(); j < feld.getHorizontal(); j++) {
                feld.changeCoordinates(j, i, 1);
                
            }
        }
    }
    @Override
    public void spielzug(Spieler spieler) {
        System.out.println(spieler.getSpielername() + " ist dran!");
        if(spieler.getSpielerart() == 1){ //Ein richtiger Spieler spielt
            Scanner scan = new Scanner(System.in);
            // Koordinaten einlesen
            int x = feld.getHorizontal() - 1;
            int y = feld.getVertical() -1 ;
            System.out.println("Gib bitte nacheinander x und y Koordinate deines Zuges ein:");
            do{
                x = scan.nextInt();
                scan.nextLine();
                y = scan.nextInt();
                if(feld.getValue(x, y) == 1 || feld.isInRange(x, y) == false) {
                    System.out.println("Du musst ein korrektes Feld nehmen! Wiederhole die Eingabe:");
                }
            } while(feld.getValue(x, y) != 0);

            //Spielzug daraus erstellen
            Spielzug spielzug = new Spielzug(x, y, spieler);
            //Spielzug ausführen
            executeSpielzug(spielzug);
            addSpielzug(spielzug);
            //Ein Spieler hat verloren ?
            if(feld.getValue(0, 0) == 1) {
                setPlayerlost();
            }
        }
        else{ //der Computer spielt
            if(szstack.empty()){
                Spielzug spielzug = new Spielzug((feld.getHorizontal() - 1), (feld.getVertical() - 1), spieler);
                executeSpielzug(spielzug);
                addSpielzug(spielzug);
                if(feld.getValue(0, 0) == 1) {
                    setPlayerlost();
                }
            }
            else{
                //Zug des letzten Spielers auslesen
                Spielzug letzterZug = removeSpielzug();
                //Zug wieder auf Stack bringen
                addSpielzug(letzterZug);
                //Zuerst unten Links probieren
                int newX = letzterZug.getXkoordinate() - 1;
                int newY = letzterZug.getYkoordinate() + 1;
                //Wenn unten links nicht geht dann oben rechts
                if(!feld.isInRange(newX, newY) || feld.getValue(newX, newY) == 1) {
                    newX = letzterZug.getXkoordinate() + 1;
                    newY = letzterZug.getYkoordinate() -1;
                }
                //wenn oben rechts nicht geht dann links
                if(!feld.isInRange(newX, newY) || feld.getValue(newX, newY) == 1) {
                    newX = letzterZug.getXkoordinate() - 1;
                    newY = letzterZug.getYkoordinate();
                }
                //wenn links auch nicht geht dann oben
                if(!feld.isInRange(newX, newY) || feld.getValue(newX, newY) == 1) {
                    newX = letzterZug.getXkoordinate();
                    newY = letzterZug.getYkoordinate() - 1;
                }
                Spielzug spielzug = new Spielzug(newX, newY, spieler);
                executeSpielzug(spielzug);
                addSpielzug(spielzug);
                if(feld.getValue(0, 0) == 1) {
                    setPlayerlost();
                }
            }
        }
        System.out.println();
        feld.printSpielfeld();

    }    
    @Override
    public void durchgang() {
        if(!playerlost) {
            spielzug(spieler[0]);
        }
        if(!playerlost) {
            spielzug(spieler[1]);
        }
    }
    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }
}