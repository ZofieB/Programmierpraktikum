package chomp;

import absclasses.*;
//import java.util.Arrays;

public class ChompFeld extends Spielfeld{
    private int [][] feld; 

    public void initializeSpielfeld() {
        java.util.Arrays.fill(feld, 0);
    }

    @Override
    public void printSpielfeld() {
        for ( int i = 0; i < this.vertical; i++) {
            for ( int j = 0; j < this.horizontal; j++)
            System.out.print(feld[i][j] + "\t");
            System.out.print("\n");
        }
    }
}