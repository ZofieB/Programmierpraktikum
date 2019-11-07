package viergewinnt;

import absclasses.*;
//import java.util.Arrays;

public class VierGewinntFeld extends Spielfeld {
    private int [][] feld;

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

    public boolean checkNumber(int i, int j){
        if (feld[i][j] == 1){
            return true;
        }
        else return false;
    }

    public boolean checkBesetzt (int i, int j){
        if (feld[i][j] == 0){
            return false;
        }
        else return true;
    }
    
    

    public boolean checkGewonnen(int i, int j){
        int counter = 0;
        //von unten testen
        if (j >= 3){
            for (int pruefunten =  j - 1; pruefunten >= j - 3 || feld[i][pruefunten] != feld[i][j]; pruefunten--){
                if (feld[i][pruefunten] == feld[i][j]){
                    counter = counter + 1;
                }
            }
            if (counter == 3){
                return true;
            }
        }
        counter = 0;

        // einmal von links und/oder rechts
        for (int prueflinks = i - 1; prueflinks >= i - 3 || feld[prueflinks][j] != feld[i][j] || i < 0; prueflinks--){
            if (feld[prueflinks][j] == feld [i][j]){
                counter = counter + 1;
            }
        }
        for (int pruefrechts = i + 1; pruefrechts <= i + 3 || feld[pruefrechts][j] != feld[i][j] || i > this.horizontal - 1; pruefrechts++){
            if (feld[pruefrechts][j] == feld [i][j]){
                counter = counter + 1;
            }
        }  
        if (counter == 3){
            return true;
        }
        else counter = 0;

        //von links unten nach rechts oben
        for (int pruefrunter1 = j - 1, prueflinksrueber1 = i - 1; pruefrunter1 >= j - 3 || prueflinksrueber1 >= i - 3 ||  pruefrunter1 < 0 || prueflinksrueber1 < 0 || feld[prueflinksrueber1][pruefrunter1] != feld[i][j]; pruefrunter1--, prueflinksrueber1--){
            if (feld[prueflinksrueber1][pruefrunter1] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch1 = j + 1, pruefrechtsrueber1 = i + 1; pruefhoch1 <= j + 3 || pruefrechtsrueber1 <= i + 3 || pruefhoch1 > this.vertical - 1 || pruefrechtsrueber1 > this.horizontal - 1 || feld[pruefrechtsrueber1][pruefhoch1] != feld[i][j]; pruefhoch1++, pruefrechtsrueber1++){
            if (feld[pruefrechtsrueber1][pruefhoch1] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        if (counter == 3){
            return true;
        }
        else counter = 0;

        //von rechts unten nach links oben
        for (int pruefrunter2 = j - 1, pruefrechtsrueber2 = i + 1; pruefrunter2 >= j - 3 || pruefrechtsrueber2 <= i + 3 ||  pruefrunter2 < 0 || pruefrechtsrueber2 > this.horizontal - 1 || feld[pruefrechtsrueber2][pruefrunter2] != feld[i][j]; pruefrunter2--, pruefrechtsrueber2++){
            if (feld[pruefrechtsrueber2][pruefrunter2] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch2 = j + 1, prueflinksrueber2 = i - 1; pruefhoch2 <= j + 3 || prueflinksrueber2 >= i - 3 || pruefhoch2 > this.vertical - 1 || prueflinksrueber2 < 0 || feld[prueflinksrueber2][pruefhoch2] != feld[i][j]; pruefhoch2++, prueflinksrueber2--){
            if (feld[prueflinksrueber2][pruefhoch2] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        if (counter == 3){
            return true;
        }
        else {
            counter = 0;
            return false;
        }
    }
    
    // Methode für Computer um potentielle Gefahren zu erkennen und zu beseitigen
    public int checkGefahren(int i, int j){
        int counter = 0;
        int safe = 0; 

        //von unten testen ob schon 3 Steine vom Gegner übereinander liegen
        if (j >= 2){
            int oben = 0;
            for (int pruefunten =  j - 1; pruefunten >= j - 3 || feld[i][pruefunten] != feld[i][j]; pruefunten--){
                if (feld[i][pruefunten] == feld[i][j]){
                    counter = counter + 1;
                }
            }
            if (counter == 2){
                oben = oben + 1;
                return oben;
            }
        }
        counter = 0;

        // einmal von links und/oder rechts
        for (int prueflinks = i - 1; prueflinks >= i - 3 || feld[prueflinks][j] != feld[i][j] || i < 0; prueflinks--){
            if (feld[prueflinks][j] == feld [i][j]){
                counter = counter + 1;
            }
        }
        for (int pruefrechts = i + 1; pruefrechts <= i + 3 || feld[pruefrechts][j] != feld[i][j] || i > this.horizontal - 1; pruefrechts++){
            if (feld[pruefrechts][j] == feld [i][j]){
                counter = counter + 1;
            }
        }  
        if (counter == 2){
            // testen von welcher Seite aus Spielzug getätigt werden muss, um die Gefahr direkt beseitigen zu können
            int rechts = i + 1, links = i - 1;
            while (feld[i][j] == feld [rechts][j]){
                rechts++;
            }
            if (feld[rechts][j-1] != 0 && rechts < this.horizontal){
                return rechts = rechts + 1;  
            }
            while (feld[i][j] == feld [links][j]){
                links--;
            }
            if (feld[links][j-1] != 0 && links >= 0){
                return links = links + 1;  
            }
        }
        else counter = 0;

        //von links unten nach rechts oben
        for (int pruefrunter1 = j - 1, prueflinksrueber1 = i - 1; pruefrunter1 >= j - 3 || prueflinksrueber1 >= i - 3 ||  pruefrunter1 < 0 || prueflinksrueber1 < 0 || feld[prueflinksrueber1][pruefrunter1] != feld[i][j]; pruefrunter1--, prueflinksrueber1--){
            if (feld[prueflinksrueber1][pruefrunter1] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch1 = j + 1, pruefrechtsrueber1 = i + 1; pruefhoch1 <= j + 3 || pruefrechtsrueber1 <= i + 3 || pruefhoch1 > this.vertical - 1 || pruefrechtsrueber1 > this.horizontal - 1 || feld[pruefrechtsrueber1][pruefhoch1] != feld[i][j]; pruefhoch1++, pruefrechtsrueber1++){
            if (feld[pruefrechtsrueber1][pruefhoch1] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        if (counter == 2){
            int runter1 = j - 1;
            int linksrueber1 = i - 1;
            int hoch1 = j + 1; 
            int rechtsrueber1 = i + 1;
            while (feld[linksrueber1][runter1] == feld[i][j]){
                linksrueber1--;
                runter1--;
            }
            if (feld[linksrueber1][runter1 - 1] != 0 && linksrueber1 >= 0 && runter1 >= 0){
                return linksrueber1 + 1;
            }
            while (feld[rechtsrueber1][hoch1] == feld[i][j]){
                rechtsrueber1++;
                hoch1++;
            }
            if (feld[rechtsrueber1][hoch1 - 1] != 0 && rechtsrueber1 < this.horizontal && hoch1 < this.vertical){
                return rechtsrueber1 + 1;
            }
        }
        else counter = 0;

        //von rechts unten nach links oben
        for (int pruefrunter2 = j - 1, pruefrechtsrueber2 = i + 1; pruefrunter2 >= j - 3 || pruefrechtsrueber2 <= i + 3 ||  pruefrunter2 < 0 || pruefrechtsrueber2 > this.horizontal - 1 || feld[pruefrechtsrueber2][pruefrunter2] != feld[i][j]; pruefrunter2--, pruefrechtsrueber2++){
            if (feld[pruefrechtsrueber2][pruefrunter2] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch2 = j + 1, prueflinksrueber2 = i - 1; pruefhoch2 <= j + 3 || prueflinksrueber2 >= i - 3 || pruefhoch2 > this.vertical - 1 || prueflinksrueber2 < 0 || feld[prueflinksrueber2][pruefhoch2] != feld[i][j]; pruefhoch2++, prueflinksrueber2--){
            if (feld[prueflinksrueber2][pruefhoch2] == feld [i][j]){
                counter = counter + 1; 
            }
        }
        if (counter == 2){
            int runter2 = j - 1;
            int linksrueber2 = i - 1;
            int hoch2 = j + 1; 
            int rechtsrueber2 = i + 1;
            while (feld[linksrueber2][hoch2] == feld[i][j]){
                linksrueber2--;
                hoch2++;
            }
            if (feld[linksrueber2][hoch2 - 1] != 0 && linksrueber2 >= 0 && hoch2 < this.vertical){
                return linksrueber2 + 1;
            }
            while (feld[rechtsrueber2][runter2] == feld[i][j]){
                rechtsrueber2++;
                runter2--;
            }
            if (feld[rechtsrueber2][runter2 - 1] != 0 && rechtsrueber2 < this.horizontal && runter2 >= 0){
                return rechtsrueber2 + 1;
            }
        }
        else {
            counter = 0;
            safe = 1;
            }
        return safe;
    }

    public void changeCoordinates(int x, int y, int number) { //wobei number = 1 am Anfang festgelegt wird und sich die restlichen number vom Vorgänger unterscheiden - switch zwischen Spieler 1 und 2
        this.feld[y][x] = number;
    }
}