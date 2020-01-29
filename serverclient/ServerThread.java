package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

class ServerThread extends Thread{
    Socket client;
    ServerController controller;
    String opponent;
    ServerThread(Socket client, ServerController controller) {
        this.client = client;
        this.controller = controller;
    }
    
    public void run() {
        controller.printOutput("VERBINDUNG\tEin neuer Client hat sich verbunden! Login wird gestartet!");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            Session session = new Session(client, controller);
            //Login bzw. Registrierung
            //Bei falscher Eingabe wird Client durch Methode aus Session direkt wieder ausgeloggt
            String benutzername = in.readLine();
            String passwort = in.readLine();
            boolean success = session.login(benutzername, passwort);
            
            if(success){
                session.send_message("", "002", client);
                controller.printOutput("USER\t" + benutzername + " hat sich angemeldet!");
                //Nachricht über Anmeldung an alle anderen Nutzer
                session.message_all_clients_except_self(benutzername + " hat sich angemeldet");
                //Update für GUI des eigenen Clients
                session.update_all_active_clients();
                //Nachricht an alle anderen Clients zum Update der GUI
                session.message_all_clients(benutzername, "099");
                //Update der ServerGUI
                controller.updateClientList(session.returnClientList());
            }
            else{
                session.send_message("", "001", client);
            }

            //Input vom Client empfangen (Message Listener)
            boolean logout = false;
            while(!logout){
                String[] input = session.get_message();
                System.out.println(input[0]);
                System.out.println(input[1]);
                if(input[0].equals("101")){
                    session.client_logout(client);
                    controller.printOutput("VERBINDUNG\t" + benutzername + " hat die Verbindung getrennt!");
                    session.message_all_clients(benutzername + " hat sich ausgeloggt!", "111");
                    //GUI Update an alle Nutzer schicken
                    session.message_all_clients(benutzername, "098");
                    //GUI Update des Servers
                    controller.updateClientList(session.returnClientList());
                    logout = true;
                }
                else if(input[0].equals("100")){
                    controller.printOutput("MESSAGE\t" + benutzername + ": " + input[1]);
                    session.message_all_clients(benutzername + ": " + input[1], "111");
                }
                else if(input[0].equals("500")){
                    //Message?
                    //Spieleanfrage weiterschicken an entsprechenden Gegner (input[1]) und Gegner in Variable speichern
                    System.out.println("### Code 500 erhalten und bearbeitet");
                    session.message_this_client(benutzername, input[1], "501");
                    opponent = input[1];
                }
                else if(input[0].equals("555")){
                    //Client hat Spielzug gemacht, Spielzug wird weitergegeben
                    session.message_this_client("Hier muss der Spielzug stehen", opponent, "505");
                }
            }
            client.close();

        } catch(IOException e){}
    }

    public void shutdown() throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        out.write("111");
        out.newLine();
        out.write("Der Server wurde geschlossen!");
        out.newLine();
        out.flush();
        out.write("222");
        out.newLine();
        out.flush();
        client.close();
    }
}