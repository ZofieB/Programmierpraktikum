package viergewinnt;

import absclasses.*;
import java.util.*;

public class VierGewinnt extends Spiel implements Protokollierbar {
    protected VierGewinntFeld feld = new VierGewinntFeld();
    private boolean playerwin = false;
    private boolean playunentschieden = false;

    public VierGewinnt(VierGewinntFeld feld, Spieler[] spieler) {
        this.feld = feld;
        this.spieler = spieler;
        
    }

    public void setPlayerwin() {
        this.playerwin = true;
    }

    public void setPlayunentschieden(){
        this.playunentschieden = true;
    }
    
    public boolean getPlayerwin() {
        return this.playerwin;
    }
    
    public boolean getPlayunentschieden() {
        return this.playunentschieden;
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
                x = -1;
                while (x < 0 || x > feld.getHorizontal()){
                    x = scan.nextInt();
                    scan.nextLine();      
                    if (x < 0 || x > feld.getVertical()){
                        System.out.println("Die x-Koorinate befindet sich außerhalb des Spielfeldes. Versuch es mit einer anderen x-Koordinate:");
                    }
                }          
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
            else if (feld.checkUnentschieden() == true){
                setPlayunentschieden();
            }
        }
        else { //der Computer spielt
            if(szstack.empty()){
                Spielzug spielzug = new Spielzug((feld.getHorizontal() / 2), 0, spieler);
                executeSpielzug(spieler, spielzug);
                addSpielzug(spielzug);
            }
            else{ 
                //Zug des letzten Spielers auslesen
                Spielzug letzterZug = removeSpielzug();
                //Zug wieder auf Stack bringen
                addSpielzug(letzterZug);
                int newX = 0;
                int newY = 0;
                int checker = 0;
                int gefahrda = 0;
                
                //Checken, ob Gefahr da ist, dass der Gegner 4 Steine übereinanderstapelt
                feld.checkGefahrenOben(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                if (feld.checkGefahrenOben(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) == true && letzterZug.getYkoordinate() < feld.getVertical() - 1){
                    newX = letzterZug.getXkoordinate();
                    newY = letzterZug.getYkoordinate() + 1;
                    gefahrda = gefahrda + 1;
                }
                
                //Checken, ob Gefahr da ist, dass der Gegner 4 Steine nebeneinander legt
                if (feld.checkGefahrenRechtsLinks(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) == true){
                    //Checken, ob Gefahr beseitigt werden kann, wenn man Stein ganz links legt
                    checker = feld.checkGegenzugGefahrLinks(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                    if (checker != -1){
                        if(feld.checkBesetzt(checker, letzterZug.getYkoordinate()) == false){
                            newX = checker;
                            newY = letzterZug.getYkoordinate();
                            gefahrda = gefahrda + 1;
                        }
                    }
                    //Checken, ob Gefahr beseitigt werden kann, wenn man Stein ganz rechts legt
                    checker = feld.checkGegenzugGefahrRechts(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                    if (checker >= 0){
                        if(feld.checkBesetzt(checker, letzterZug.getYkoordinate()) == false){
                            newX = checker;
                            newY = letzterZug.getYkoordinate();
                            gefahrda = gefahrda + 1;
                        }
                    }
                }
                //Checken ob Gegner in der Diagonale links unten bis rechts oben 4 Steine hinlegen kann
                /*if (feld.checkGefahrenLinksUntenRechtsOben(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) == true){
                    checker = feld.checkGegenzugGefahrLinksUnten(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                    if (checker >= 0){
                        if (feld.checkBesetzt(checker, letzterZug.getYkoordinate() - (letzterZug.getXkoordinate() - checker)) == false){
                            newX = checker;
                            newY = letzterZug.getYkoordinate() - (letzterZug.getXkoordinate() - newX);
                            gefahrda = gefahrda + 1;
                        }
                    } 
                    checker = feld.checkGegenzugGefahrRechtsOben(letzterZug.getXkoordinate(), letzterZug.getYkoordinate());
                    if(checker >= 0){
                        if (feld.checkBesetzt(checker, (checker - letzterZug.getXkoordinate() + letzterZug.getYkoordinate())) == false){
                            newX = checker;
                            newY = newX - letzterZug.getXkoordinate() + letzterZug.getYkoordinate();
                            gefahrda = gefahrda + 1;
                        }
                    }
                }

                //Checken ob Gegner in der Diagonale links oben bis rechts unten 4 Steine hinlegen kann
                if (feld.checkGefahrLinksObenRechtsUnten(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) == true){
                    checker = feld.checkGegenzugGefahrLinksOben(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) - 1;
                    if (checker >= 0){
                        if (feld.checkBesetzt(checker, letzterZug.getXkoordinate() - checker + (letzterZug.getXkoordinate())) == false){
                            newX = checker;
                            newY = letzterZug.getXkoordinate() - newX + (letzterZug.getXkoordinate());
                            gefahrda = gefahrda + 1;
                        }
                    }
                    checker = feld.checkGegenzugGefahrRechtsUnten(letzterZug.getXkoordinate(), letzterZug.getYkoordinate()) - 1;
                    if (checker >= 0){
                        if (feld.checkBesetzt(checker, letzterZug.getYkoordinate() - checker - letzterZug.getXkoordinate()) == false){
                            newX = checker;
                            newY = letzterZug.getYkoordinate() - newX - letzterZug.getXkoordinate();
                            gefahrda = gefahrda + 1;
                        }
                    }
                }
                */

                if(gefahrda == 0){
                    int neuerZug = 1;
                    if (letzterZug.getXkoordinate() == feld.getHorizontal() - 1){
                        newX = 0;
                    }
                    else newX = letzterZug.getXkoordinate() + 1;
                    do{      
                        i = 0;         
                        for(;i < feld.getVertical() && feld.checkBesetzt(newX, i); i++){ //von der untersten y-Koordinate Spielfeld beim eingegebenen x nach oben durchschauen, ob value drauf ist oder nicht
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
                if (feld.checkUnentschieden() == true){
                    setPlayunentschieden();
                }
            }
        System.out.println();
        feld.printSpielfeld();
        }
    }

    @Override
    public void durchgang() {
        if(!playerwin && !playunentschieden) {
            spielzug(spieler[0]);
        }
        if(!playerwin && !playunentschieden) {
            spielzug(spieler[1]);
        }
    }


    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }
}