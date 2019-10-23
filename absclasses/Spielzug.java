package absclasses;

public class Spielzug{
    protected Spieler spieler;
    protected int xkoordinate;

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
}