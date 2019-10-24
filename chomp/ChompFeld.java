package chomp;

import absclasses.*;
//import java.util.Arrays;

public class ChompFeld extends Spielfeld{
    public int [][] feld; 

    public void initializeSpielfeld() {
        this.feld = new int[this.vertical][this.horizontal];

        for ( int i = 0; i < this.vertical; i++) {
            for ( int j = 0; j < this.horizontal; j++) {
                this.feld[i][j] = 0;
            }
        }
    }

    @Override
    public void printSpielfeld() {
        for ( int i = 0; i < this.vertical; i++) {
            for ( int j = 0; j < this.horizontal; j++) {
                System.out.print(feld[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public void changeCoordinates(int x, int y, int value) {
        this.feld[x][y] = value;
    }
}