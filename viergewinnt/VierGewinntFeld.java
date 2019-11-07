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
        System.out.println();
        for (int l = 1; l <= this.horizontal; l++){
            System.out.print(l + "\t");
        }
        System.out.println();
        for (int l = 1; l <= this.horizontal; l++){
            System.out.print("--------");
        }
        System.out.println();
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
    
    public boolean checkUnentschieden(){
        for ( int i = 0; i < this.vertical; i++) {
            for ( int j = 0; j < this.horizontal; j++) {
                if (feld[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
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
    public boolean checkGefahrenOben(int x, int y){ 
        int counter = 0;  //von unten testen ob schon 3 Steine vom Gegner übereinander liegen
        if (y >= 2){
            for (int pruefunten =  y - 1; pruefunten > y - 3 && feld[pruefunten][x] == feld[y][x]; pruefunten--){
                if (feld[pruefunten][x] == feld[y][x]){
                    counter = counter + 1;
                }
            }
            if (counter == 2){
                return true;
            } 
        }
        return false;
    }
        
    public boolean checkGefahrenRechtsLinks(int x, int y){
        int counter = 0; 
        // einmal von links und/oder rechts
        if (x > 0){
            for (int prueflinks = x - 1; prueflinks >= 0 && prueflinks >= x - 3 && feld[y][prueflinks] == feld[y][x]; prueflinks--){
                if (feld[y][prueflinks] == feld [y][x]){
                    counter = counter + 1;
                }
            }
        }
        if (x < this.horizontal - 1){
            for (int pruefrechts = x + 1; pruefrechts <= this.horizontal - 1 && pruefrechts <= x + 3 && feld[y][pruefrechts] == feld[y][x]; pruefrechts++){
                if (feld[y][pruefrechts] == feld [y][x]){
                    counter = counter + 1;
                }
            } 
        } 
        if (counter == 2){
            return true;
        }
        else return false;
    }

    public int checkGegenzugGefahrLinks(int x, int y){
        int links = x;   
        int nichtmöglich = -1;   
        if (links != 0){
            while (links > 0 && feld[y][x] == feld [y][links] ){
                links--;
            }
            if (y > 0 && links >= 0){
                if (links >= 0 && feld[y-1][links] != 0){
                    return links;
                }
            }
            else if (y == 0 && links >= 0){
                if (feld[y][links] == 0){
                    return links;
                }
            }
        }
        return nichtmöglich;
    }

    public int checkGegenzugGefahrRechts(int x, int y){
        int rechts = x;  
        int nichtmöglich = - 1;    
        if (rechts < this.horizontal - 1){
            while (rechts < this.horizontal - 1 && feld[y][x] == feld [y][rechts] ){
                rechts++;
            }
            if (y > 0){
                if (rechts < this.horizontal && feld[y-1][rechts] != 0){
                    return rechts;
                }
            }
            else if (y == 0){
                if (feld[y][rechts] == 0){
                    return rechts;
                }
            }
        }
        return nichtmöglich;
    }

    /*
    public boolean checkGefahrenLinksUntenRechtsOben(int x, int y){
        int counter = 0;
        //von links unten nach rechts oben
        if (y > 0 && x > 0){
            for (int pruefrunter1 = y - 1, prueflinksrueber1 = x - 1;  pruefrunter1 >= 0 && prueflinksrueber1 >= 0 && pruefrunter1 >= y - 3 && prueflinksrueber1 >= x - 3 && feld[pruefrunter1][prueflinksrueber1] == feld[y][x]; pruefrunter1--, prueflinksrueber1--){
                if (feld[pruefrunter1][prueflinksrueber1] == feld [y][x]){
                    counter = counter + 1; 
                }
            }
        }
        if (y < this.vertical - 1 && x < this.horizontal - 1){
            for (int pruefhoch1 = y + 1, pruefrechtsrueber1 = x + 1;  pruefhoch1 <= this.vertical - 1 && pruefrechtsrueber1 <= this.horizontal - 1 && pruefhoch1 <= y + 3 && pruefrechtsrueber1 <= x + 3 && feld[pruefhoch1][pruefrechtsrueber1] == feld[y][x]; pruefhoch1++, pruefrechtsrueber1++){
                if (feld[pruefhoch1][pruefrechtsrueber1] == feld [y][x]){
                    counter = counter + 1; 
                }
            }
        }
        if (counter == 2){
            return true;
        }
        else return false;
    }

    public int checkGegenzugGefahrLinksUnten(int x, int y){
        int nichtmöglich = -1;
        int runter1 = y - 1;
        int linksrueber1 = x - 1;
        
        if (runter1 >= 0 && linksrueber1 >= 0){
            while (linksrueber1 >= 0 && runter1 >= 0 && feld[runter1][linksrueber1] == feld[y][x]){
                linksrueber1--;
                runter1--;
            }
            if (runter1 > 0){
                if (linksrueber1 >= 0 && runter1 >= 0 && feld[runter1 - 1][linksrueber1] != 0 ){
                    return linksrueber1;
                } 
            }
        }
        return nichtmöglich;
    }

    public int checkGegenzugGefahrRechtsOben(int x, int y){
        int nichtmöglich = -1;
        int hoch1 = y + 1; 
        int rechtsrueber1 = x + 1;

        if (hoch1 < this.vertical && rechtsrueber1 < this.vertical){
            while (rechtsrueber1 < this.horizontal && hoch1 < this.horizontal && feld[hoch1][rechtsrueber1] == feld[y][x]){
                rechtsrueber1++;
                hoch1++;
            }
            if (rechtsrueber1 < this.horizontal && hoch1 < this.vertical && feld[hoch1 - 1][rechtsrueber1] != 0 ){
                return rechtsrueber1;
            }
        }
        return nichtmöglich;
    }

    public boolean checkGefahrLinksObenRechtsUnten(int x, int y){
        //von rechts unten nach links oben
        int counter = 0;
        if (x < this.horizontal - 1 && y > 0){
            for (int pruefrunter2 = y - 1, pruefrechtsrueber2 = x + 1;  pruefrunter2 >= 0 && pruefrechtsrueber2 <= this.horizontal - 1 && pruefrunter2 >= y - 3 && pruefrechtsrueber2 <= x + 3 && feld[pruefrunter2][pruefrechtsrueber2] == feld[y][x]; pruefrunter2--, pruefrechtsrueber2++){
                if (feld[pruefrunter2][pruefrechtsrueber2] == feld [y][x]){
                    counter = counter + 1; 
                }
            }
        }
        if (x > 0 && y < this.vertical - 1){
            for (int pruefhoch2 = y + 1, prueflinksrueber2 = x - 1; pruefhoch2 <= this.vertical - 1 && prueflinksrueber2 >= 0 && pruefhoch2 <= y + 3 && prueflinksrueber2 >= x - 3 && feld[pruefhoch2][prueflinksrueber2] == feld[y][x]; pruefhoch2++, prueflinksrueber2--){
                if (feld[pruefhoch2][prueflinksrueber2] == feld [y][x]){
                    counter = counter + 1; 
                }
            }
        }
        if (counter == 2){
           return true;
        }
        else return false;
    }

    public int checkGegenzugGefahrLinksOben(int x, int y){
        int nichtmöglich = -1 ;
        int linksrueber2 = x - 1;
        int hoch2 = y + 1; 

        if (linksrueber2 >= 0 && hoch2 < this.vertical){
            while (linksrueber2 >= 0 && hoch2 < this.vertical && feld[hoch2][linksrueber2] == feld[y][x]){
                linksrueber2--;
                hoch2++;
            }
            if (hoch2 >= 1){
                if (linksrueber2 >= 0 && hoch2 < this.vertical && feld[hoch2 - 1][linksrueber2] != 0 ){
                    return linksrueber2;
                }
            }   
        }
        return nichtmöglich;
    }
    
    public int checkGegenzugGefahrRechtsUnten(int x, int y){
        int nichtmöglich = -1;
        int rechtsrueber2 = x + 1;
        int runter2 = y - 1;
        if (rechtsrueber2 < this.horizontal - 1 && runter2 >= 0){
            while (runter2 >= 0 && rechtsrueber2 < this.horizontal && feld[runter2][rechtsrueber2] == feld[y][x]){
                rechtsrueber2++;
                runter2--;
            }
            if (runter2 >= 1){
                if (rechtsrueber2 < this.horizontal && runter2 >= 0 && feld[runter2 - 1][rechtsrueber2] != 0 ){
                    return rechtsrueber2;
                }
            }
        }
        return nichtmöglich;
    }
    */


    public void changeCoordinates(int x, int y, int number) { //wobei number = 1 am Anfang festgelegt wird und sich die restlichen number vom Vorgänger unterscheiden - switch zwischen Spieler 1 und 2
        this.feld[y][x] = number;
    }
}
    