package chomp;

import absclasses.*;

public class ChompSpielzug extends Spielzug{
    protected int ykoordinate;

    public int getYkoordinate() {
        return this.ykoordinate;
    }
    public void setYkoordinate(int y) {
        this.ykoordinate = y;
    }
}