package chomp;

import absclasses.*;
import java.util.*;

public class Chomp extends Spiel implements Protokollierbar{
    protected ChompFeld feld = new ChompFeld();

    public Chomp(ChompFeld feld, Spieler[] spieler) {
        this.feld = feld;
        this.spieler = spieler;
    }

    @Override
    public void addSpielzug(Spielzug spielzug) {
        szstack.push(spielzug);
    }
    @Override
    public void removeSpielzug() {
        szstack.pop();
    }

    public void executeSpielzug(ChompSpielzug spielzug) {
        //Koordinate in ArrayIndizes, vertical und horizontal sind absolute Längen
        
        if(feld.getValue(spielzug.getXkoordinate(), spielzug.getYkoordinate()) == 0) {
            for ( int i = spielzug.getYkoordinate(); i < feld.getVertical(); i++) {
                for ( int j = spielzug.getXkoordinate(); j < feld.getHorizontal(); j++) {
                    feld.changeCoordinates(i, j, 1);
                }
            }
        }
    }
    @Override
    public void spielzug() {
        Spieler spieler = new Spieler(); //Platzhalter für Spieler der am Zug ist
        Scanner scan = new Scanner(System.in);
        // Koordinaten einlesen
        System.out.println("Gib bitte nacheinander x und y Koordinate deines Zuges ein:");
        int x = scan.nextInt();
        int y = scan.nextInt();
        //Spielzug daraus erstellen
        ChompSpielzug spielzug = new ChompSpielzug(x, y, spieler);
        //Spielzug ausführen
        executeSpielzug(spielzug);
        szstack.push(spielzug);
        //Ein Spieler hat verloren ?
        if(feld.getValue(0, 0) == 1) {
            
        }
    }
    @Override
    public void durchgang() {

    }
    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }

    public void chomp() {
        //Startspieler
    }
}