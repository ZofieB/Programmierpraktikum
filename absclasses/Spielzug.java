package absclasses;

public class Spielzug{
    protected Spieler spieler;
    protected int xkoordinate;
    protected int ykoordinate;

    public Spieler getSpieler() {
        return this.spieler;
    }
    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }
    public int getXkoordinate() {
        return this.xkoordinate;
    }
    public void setXkoordinate(int x) {
        this.xkoordinate = x;
    }
    public int getYkoordinate() {
        return this.ykoordinate;
    }
    public void setYkoordinate(int y) {
        this.ykoordinate = y;
    }
}