package absclasses;

public class Spieler {
    private String spielername;
    private boolean spielerart; //0-Computer 1-richtiger Spieler

    public String getSpielername() {
        return this.spielername;
    }
    public void setSpielername(String spielername) {
        this.spielername = spielername;
    }
    public boolean getSpielerart(){
        return this.spielerart;
    }
    public void setSpielerart(boolean spielerart) {
        this.spielerart = spielerart;
    }

}