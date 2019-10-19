package absclasses;

public class Spielzug{
    private Spieler spieler;
    private int xkoordinate;
    private int ykoordinate;

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