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
    public void spielzug(Spieler spieler) {
        Scanner scan = new Scanner(System.in);
        // Koordinaten einlesen
        System.out.println("Gib bitte nacheinander x und y Koordinate deines Zuges ein:");
        int x = scan.nextInt();
        scan.nextLine();
        int y = scan.nextInt();
        //Spielzug daraus erstellen
        ChompSpielzug spielzug = new ChompSpielzug(x, y, spieler);
        //Spielzug ausführen
        executeSpielzug(spielzug);
        szstack.push(spielzug);
        //Ein Spieler hat verloren ?
        if(feld.getValue(0, 0) == 1) {
            setPlayerlost();
        }
        System.out.println();
        feld.printSpielfeld();

    }    
    @Override
    public void durchgang() {
        spielzug(spieler[0]);
        spielzug(spieler[1]);
    }
    public Stack<Spielzug> getSzstack(){
        return this.szstack;
    }
}