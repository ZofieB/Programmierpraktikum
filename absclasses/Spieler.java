package absclasses;

public class Spieler {
    private String spielername;
    private int spielerart; //0-Computer 1-richtiger Spieler

    public Spieler() {}
    public Spieler(String spielername, boolean spielerart) {
        this.spielerart = spielerart;
        this.spielername = spielername;
    }

    public String getSpielername() {
        return this.spielername;
    }
    public void setSpielername(String spielername) {
        this.spielername = spielername;
    }
    public int getSpielerart(){
        return this.spielerart;
    }
    public void setSpielerart(int spielerart) {
        this.spielerart = spielerart;
    }

}