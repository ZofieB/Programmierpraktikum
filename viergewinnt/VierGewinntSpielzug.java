package viergewinnt;

import absclasses.*;

public class VierGewinntSpielzug extends Spielzug{
    public VierGewinntSpielzug(int x, int y Spieler spieler) {
        this.xkoordinate = x;
        this.ykoordinate = y; 
        this.spieler = spieler;
    }

    public int getYkoordinate() {
        return this.ykoordinate;
    }
    public void setYkoordinate(int y) {
        this.ykoordinate = y;
    }
}