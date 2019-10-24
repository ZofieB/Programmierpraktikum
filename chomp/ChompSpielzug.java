package chomp;

import absclasses.*;

public class ChompSpielzug extends Spielzug{
    protected int ykoordinate;

    public ChompSpielzug(int x, int y) {
        this.xkoordinate = x;
        this.ykoordinate = y;
    }

    public int getYkoordinate() {
        return this.ykoordinate;
    }
    public void setYkoordinate(int y) {
        this.ykoordinate = y;
    }
}