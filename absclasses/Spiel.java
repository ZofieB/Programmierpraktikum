package absclasses;

public abstract class Spiel {
    protected Spieler[] spieler;
    protected Spielfeld spielfeld;

    abstract public void spielzug(Spieler spieler);
    abstract public void durchgang();
    public Spieler[] getSpieler() {
        return this.spieler;
    }
    public void setSpieler(Spieler[] spieler) {
        this.spieler = spieler;
    }
    public Spielfeld getSpielfeld() {
        return this.spielfeld;
    }
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }
}