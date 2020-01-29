package absclasses;

import javafx.scene.paint.Color;

public class Spieler {
    protected String spielername;
    protected int spielerart; //0-Computer 1-richtiger Spieler
    private Color farbe;

    public Spieler() {}
    public Spieler(String spielername, int spielerart, Color farbe) {
        this.spielerart = spielerart;
        this.spielername = spielername;
        this.farbe = farbe;
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
    public void setFarbe(Color farbe){
        this.farbe = farbe;
    }
    public Color getFarbe(){
        return this.farbe;
    }

}