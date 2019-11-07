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

    public boolean checkNumber(int x, int y){
        if (feld[y][x] == 1){
            return true;
        }
        else return false;
    }

    public boolean checkBesetzt (int x, int y){
        if (feld[y][x] == 0){
            return false;
        }
        else return true;
    }
    
    

    public boolean checkGewonnen(int x, int y){
        int counter = 0;
        //von unten testen
        if (y >= 3){
            for (int pruefunten =  y - 1; pruefunten >= y - 3 && feld[pruefunten][x] == feld[y][x]; pruefunten--){
                if (feld[pruefunten][x] == feld[y][x]){
                    counter = counter + 1;
                }
            }
            if (counter == 3){
                return true;
            }
        }
        counter = 0;

        // einmal von links und/oder rechts
        for (int prueflinks = x - 1; prueflinks >= 0 && prueflinks >= x - 3 && feld[y][prueflinks] == feld[y][x]; prueflinks--){
            if (feld[y][prueflinks] == feld [y][x]){
                counter = counter + 1;
                System.out.println(counter);
            }
        }

        for (int pruefrechts = x + 1; pruefrechts <= this.horizontal - 1 && pruefrechts <= x + 3 && feld[y][pruefrechts] == feld[y][x]; pruefrechts++){
            if (feld[y][pruefrechts] == feld [y][x]){
                counter = counter + 1;
            }
        }  
        if (counter == 3){
            return true;
        }
        else counter = 0;

        //von links unten nach rechts oben
        for (int pruefrunter1 = y - 1, prueflinksrueber1 = x - 1; pruefrunter1 >= 0 && prueflinksrueber1 >= 0 && pruefrunter1 >= y - 3 && prueflinksrueber1 >= x - 3 && feld[pruefrunter1][prueflinksrueber1] == feld[y][x]; pruefrunter1--, prueflinksrueber1--){
            if (feld[pruefrunter1][prueflinksrueber1] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch1 = y + 1, pruefrechtsrueber1 = x + 1; pruefhoch1 <= this.vertical - 1 && pruefrechtsrueber1 <= this.horizontal - 1 && pruefhoch1 <= y + 3 && pruefrechtsrueber1 <= x + 3 && feld[pruefhoch1][pruefrechtsrueber1] == feld[y][x]; pruefhoch1++, pruefrechtsrueber1++){
            if (feld[pruefhoch1][pruefrechtsrueber1] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        if (counter == 3){
            return true;
        }
        else counter = 0;

        //von rechts unten nach links oben
        for (int pruefrunter2 = y - 1, pruefrechtsrueber2 = x + 1; pruefrunter2 >= 0 && pruefrechtsrueber2 <= this.horizontal - 1 && pruefrunter2 >= y - 3 && pruefrechtsrueber2 <= x + 3 && feld[pruefrunter2][pruefrechtsrueber2] == feld[y][x]; pruefrunter2--, pruefrechtsrueber2++){
            if (feld[pruefrunter2][pruefrechtsrueber2] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch2 = y + 1, prueflinksrueber2 = x - 1; pruefhoch2 <= this.vertical - 1 && prueflinksrueber2 >= 0 && pruefhoch2 <= y + 3 && prueflinksrueber2 >= x - 3 && feld[pruefhoch2][prueflinksrueber2] == feld[y][x]; pruefhoch2++, prueflinksrueber2--){
            if (feld[pruefhoch2][prueflinksrueber2] == feld [y][x]){
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
    public int checkGefahren(int x, int y){
        int counter = 0;
        int safe = 0; 

        //von unten testen ob schon 3 Steine vom Gegner übereinander liegen
        if (y >= 2){
            int oben = 0;
            for (int pruefunten =  y - 1; pruefunten > y - 3 && feld[pruefunten][x] == feld[y][x]; pruefunten--){
                if (feld[pruefunten][x] == feld[y][x]){
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
        for (int prueflinks = x - 1; prueflinks >= x - 3 && feld[y][prueflinks] == feld[y][x] && prueflinks >= 0; prueflinks--){
            if (feld[y][prueflinks] == feld [y][x]){
                counter = counter + 1;
            }
        }
        for (int pruefrechts = x + 1; pruefrechts <= this.horizontal - 1 && pruefrechts <= x + 3 && feld[y][pruefrechts] == feld[y][x]; pruefrechts++){
            if (feld[y][pruefrechts] == feld [y][x]){
                counter = counter + 1;
            }
        }  
        if (counter == 2){
            // testen von welcher Seite aus Spielzug getätigt werden muss, um die Gefahr direkt beseitigen zu können
            int rechts = x + 1, links = x - 1;
            while (feld[y][x] == feld [y][rechts]){
                rechts++;
            }
            if (feld[y-1][rechts] != 0 && rechts < this.horizontal){
                return rechts = rechts + 1;  
            }
            while (feld[y][x] == feld [y][links]){
                links--;
            }
            if (feld[y-1][links] != 0 && links >= 0){
                return links = links + 1;  
            }
        }
        else counter = 0;

        //von links unten nach rechts oben
        for (int pruefrunter1 = y - 1, prueflinksrueber1 = x - 1;  pruefrunter1 >= 0 && prueflinksrueber1 >= 0 && pruefrunter1 >= y - 3 && prueflinksrueber1 >= x - 3 && feld[pruefrunter1][prueflinksrueber1] == feld[y][x]; pruefrunter1--, prueflinksrueber1--){
            if (feld[pruefrunter1][prueflinksrueber1] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch1 = y + 1, pruefrechtsrueber1 = x + 1;  pruefhoch1 <= this.vertical - 1 && pruefrechtsrueber1 <= this.horizontal - 1 && pruefhoch1 <= y + 3 && pruefrechtsrueber1 <= x + 3 && feld[pruefhoch1][pruefrechtsrueber1] == feld[y][x]; pruefhoch1++, pruefrechtsrueber1++){
            if (feld[pruefhoch1][pruefrechtsrueber1] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        if (counter == 2){
            int runter1 = y - 1;
            int linksrueber1 = x - 1;
            int hoch1 = y + 1; 
            int rechtsrueber1 = x + 1;
            while (feld[runter1][linksrueber1] == feld[y][x]){
                linksrueber1--;
                runter1--;
            }
            if (linksrueber1 >= 0 && runter1 >= 0 && feld[runter1 - 1][linksrueber1] != 0 ){
                return linksrueber1 + 1;
            }
            while (feld[hoch1][rechtsrueber1] == feld[y][x]){
                rechtsrueber1++;
                hoch1++;
            }
            if (rechtsrueber1 < this.horizontal && hoch1 < this.vertical && feld[hoch1 - 1][rechtsrueber1] != 0 ){
                return rechtsrueber1 + 1;
            }
        }
        else counter = 0;

        //von rechts unten nach links oben
        for (int pruefrunter2 = y - 1, pruefrechtsrueber2 = x + 1;  pruefrunter2 >= 0 && pruefrechtsrueber2 <= this.horizontal - 1 && pruefrunter2 >= y - 3 && pruefrechtsrueber2 <= x + 3 && feld[pruefrunter2][pruefrechtsrueber2] == feld[y][x]; pruefrunter2--, pruefrechtsrueber2++){
            if (feld[pruefrunter2][pruefrechtsrueber2] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        for (int pruefhoch2 = y + 1, prueflinksrueber2 = x - 1; pruefhoch2 <= this.vertical - 1 && prueflinksrueber2 >= 0 && pruefhoch2 <= y + 3 && prueflinksrueber2 >= x - 3 && feld[pruefhoch2][prueflinksrueber2] == feld[y][x]; pruefhoch2++, prueflinksrueber2--){
            if (feld[pruefhoch2][prueflinksrueber2] == feld [y][x]){
                counter = counter + 1; 
            }
        }
        if (counter == 2){
            int runter2 = y - 1;
            int linksrueber2 = x - 1;
            int hoch2 = y + 1; 
            int rechtsrueber2 = x + 1;
            while (feld[hoch2][linksrueber2] == feld[y][x]){
                linksrueber2--;
                hoch2++;
            }
            if (linksrueber2 >= 0 && hoch2 < this.vertical && feld[hoch2 - 1][linksrueber2] != 0 ){
                return linksrueber2 + 1;
            }
            while (feld[runter2][rechtsrueber2] == feld[y][x]){
                rechtsrueber2++;
                runter2--;
            }
            if (rechtsrueber2 < this.horizontal && runter2 >= 0 && feld[runter2 - 1][rechtsrueber2] != 0 ){
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