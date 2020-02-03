package serverclient;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ServerThread extends Thread{
    Socket client;
    ServerController controller;
    String opponent;
    ArrayList<String> matchesList = new ArrayList<>();
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
                    //Spieleanfrage weiterschicken an entsprechenden Gegner (input[1]) und Gegner in Variable speichern

                    System.out.println("### Code 500 erhalten und bearbeitet");
                    String[] splittedString = input[1].split("-");

                    session.message_this_client(input[1], splittedString[0], "501");
                    opponent = splittedString[0];
                }
                else if(input[0].equals("555")){
                    //Client hat Spielzug gemacht, Spielzug wird weitergegeben
                    session.message_this_client(input[1], opponent, "505");
                }
                else if(input[0].equals("502")){
                    //Eine eingehende Einladung wurde abgelehnt
                    session.message_this_client("", input[1], "502");
                }
                else if (input[0].equals("504")) {
                    //Ich bin schon in einem spiel, benachrichtie einladenden Spieler darüber
                    session.message_this_client("", input[1], "504");
                }
                else if (input[0].equals("503")) {
                    //Eine eingehende Einladung wurde angenommen und Nachricht hat Form : spiel-gegner
                    String[] splitted = input[1].split("-");
                    opponent = splitted[1];
                    session.message_this_client(splitted[0], opponent, "503");
                }
                else if (input[0].equals("560")) {
                    //Spieler hat aktuelles Spiel abgebrochen
                    session.message_this_client("", opponent, "560");
                }
                else if (input[0].equals("565")) {
                    //Ich habe das Spiel verloren
                    session.send_message("----Du hast leider verloren!----", "111", client);
                }
                else if (input[0].equals("566")) {
                    //Ich habe das Spiel gewonnen
                    session.send_message("----Du hast das Spiel gegen " + opponent + " gewonnen!----", "111", client);
                }
                else if (input[0].equals("599")) {
                    //Neues Spiel wurde begonnen
                    controller.updateMatchList(input[1]);
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