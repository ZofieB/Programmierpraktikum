package chomp;

import absclasses.*;

public class ChompSpielzug extends Spielzug{
    protected int ykoordinate;

    public ChompSpielzug(int x, int y, Spieler spieler) {
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