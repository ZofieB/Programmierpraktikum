public abstract class Spiel { //Datenkapselung evtl. ändern!
    private Spieler[] spieler;
    private Spielfeld spielfeld;

    abstract public void spielzug();
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