package serverclient;

import chomp.ChompController;

import java.io.BufferedReader;

public class MessageListener extends Thread{
    private BufferedReader in;
    private ClientController controller;

    public MessageListener(BufferedReader in, ClientController controller) {
        this.in = in;
        this.controller = controller;
    }


    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                System.out.println(input);
                if(input.equals("111")){
                    controller.updateTextArea(in.readLine());
                    //outputField.appendText(in.readLine());
                    //System.out.println(in.readLine());
                }
                else if(input.equals("222")){
                    System.exit(0);
                }
                else if(input.equals("099")) {
                    //Neuer Login wird in ClientListe aufgenommen
                    //System.out.println("### Message Listener add Client invoked");
                    controller.addClient(in.readLine());
                    controller.updateClientList();
                }
                else if(input.equals("098")) {
                    //Logout muss aus ClientListe entfernt werden
                    String username = in.readLine();
                    System.out.println(username);
                    controller.deleteClient(username);
                    controller.updateClientList();
                }
                else if(input.equals("001")){
                    //Fehlgeschlagener Login
                    controller.updateTextArea("Der Login ist fehlgeschlagen! Das Fenster kann geschlossen werden!");
                }
                else if(input.equals("501")){
                    //Methode mit eingelesenem Gegnernutzernamen aufrufen
                    //Nachricht enthält auch weitere Informationen: "eigeneruser-gegneruser-spiel-horizontal-vertikal"
                    System.out.println("### Eingehende Einladung");
                    String message = in.readLine();
                    String[] splitted = message.split("-");
                    int horizontal = Integer.parseInt(splitted[3]);
                    int vertical = Integer.parseInt(splitted[4]);
                    String game = splitted[2];
                    String opponent = splitted[1];
                    controller.gotInvite(opponent, game, horizontal, vertical);
                }
                else if(input.equals("505")){
                    //Spielzug kommt als String der Form "col-row" als Nachricht an und wir aufgeteilt in col und row Koordinate
                    System.out.println("###Eingehender Spielzug");
                    String spielzug = in.readLine();
                    String[] splittedString = spielzug.split("-");
                    int col = Integer.parseInt(splittedString[0]);
                    int row = Integer.parseInt(splittedString[1]);
                    controller.setSpielzug(col, row);
                }
                else if(input.equals("503")){
                    System.out.println("### Invite Accepted eingehend");
                    //Ausgehende Einladung wurde angenommen --> Ändern des Boolean Wertes
                    controller.isAccepted = true;
                }
                else if(input.equals("502")){
                    //Ausgehende Einladung wurde abgelehnt --> Boolean Wert muss nicht geändert werden
                    controller.changeCancelMessage("----Die Einladung wurde abgelehnt!----");
                }
                else if(input.equals("504")){
                    //Eingeladener Spieler ist schon in einem Spiel
                    controller.changeCancelMessage("----Dieser Spieler ist schon in einem Spiel!----");
                }
                else if(input.equals("560")){
                    //Spiel wurde von Gegner abgebrochen
                    controller.gameCancel();
                }
                else if(input.equals("600")){
                    //Status am Ende des Spiels wieder ändern
                    controller.finishedGame();
                }
            }
        }catch(Exception E){}
    }
}