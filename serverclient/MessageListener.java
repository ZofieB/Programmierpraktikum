package serverclient;

import java.io.*;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class MessageListener extends Thread{
    private BufferedReader in;
    private ClientController controller;
    private boolean loggedIn = true;

    public MessageListener(BufferedReader in, ClientController controller) {
        this.in = in;
        this.controller = controller;
    }

    public boolean getLoggedIn(){
        return this.loggedIn;
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
                    System.out.println("### Message Listener add Client invoked");
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
            }
        }catch(Exception E){}
    }
}