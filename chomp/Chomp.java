package chomp;

import absclasses.*;

import java.io.IOException;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    protected ChompFeld feld = new ChompFeld();
    private boolean playerlost = false;
    private ChompController controller;
    

    public Chomp(ChompFeld feld, Spieler[] spieler, ChompController controller) {
        this.feld = feld;
        this.spieler = spieler;
        this.controller = controller;
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
        try {
            controller.setFeld(spielzug.getSpieler(), spielzug.getXkoordinate(), spielzug.getYkoordinate());
        } catch(IOException e){}
    }
    @Override
    public void spielzug(Spieler spieler){
        //Methode mit falschen Eingabeparametern!
    }

    public void spielzug(Spieler spieler, int x, int y) {
        //System.out.println(spieler.getSpielername() + " ist dran!");
        if(spieler.getSpielerart() == 1){ //Ein richtiger Spieler spielt
            /*Scanner scan = new Scanner(System.in);
            // Koordinaten einlesen
            int x = feld.getHorizontal() - 1;
            int y = feld.getVertical() -1 ;*/
            //System.out.println("Gib bitte nacheinander x und y Koordinate deines Zuges ein:");
            do{
/*                System.out.println("Bitte die x-Koordinate eingeben:");
                x = scan.nextInt();
                scan.nextLine();
                System.out.println("Bitte die y-Koordinate eingeben:");
                y = scan.nextInt();*/
                if(feld.isInRange(x, y) == false || feld.getValue(x, y) == 1) {
                    //System.out.println("Du musst ein korrektes Feld nehmen! Wiederhole die Eingabe:");
                    //EINGABE DES RICHTIGEN FELDES BEHANDELN TODO
                }
            } while(feld.isInRange(x, y) == false || feld.getValue(x, y) != 0);

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