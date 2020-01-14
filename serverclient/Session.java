package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Session{
    private Socket client;
    private boolean failed;
    private ServerController controller;
    //public static HashMap<String, String> users = new HashMap<String, String>();
    public static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();

    public Session(Socket client, ServerController controller){
        this.client = client;
        this.controller = controller;
    }

    public Session(){}

    public boolean login(String benutzername, String passwort) throws IOException {
        System.out.println("Server login invoked");
        int listsize = clients.size();
        boolean stop = false;
        int i = 0;
        while(!stop && i < listsize){
            ClientNode current_client = clients.get(i);
            //Suche nach bekanntem Nutzernamen
            if(benutzername.equals(current_client.getName())){
                stop = true;
                //Richtiges Passwort und Nutzer war nicht eingeloggt
                if(passwort.equals(current_client.getPassword()) && current_client.isLoggedin() == false){
                    send_message("Willkommen zurück " + benutzername, "111", this.client);
                    //Socket aktualisieren
                    current_client.setClient(this.client);
                    current_client.setLoggedin(true);
                    return true;
                }
                //richtiges passwort aber nutzer war schon eingeloggt
                else if(passwort.equals(current_client.getPassword()) && current_client.isLoggedin() == true){
                    controller.printOutput("VERBINDUNG\tAnmeldung fehlgeschlagen! Verbindung wird getrennt!");
                    client.close();
                    return false;
                }
                //falsches passwort
                else{
                    controller.printOutput("VERBINDUNG\tAnmeldung fehlgeschlagen! Verbindung wird getrennt!");
                    client.close();
                    return false;
                }
            }
            i++;
        }
        //Schleife ist vollständig durchgelaufen und es gab keinen Benutzernamenmatch oder es ist noch niemand registriert--> Registrierung
        if((i == listsize  && stop == false) || clients.isEmpty() == true){
            send_message("Willkommen auf dem Server!", "111", this.client);
            ClientNode newclient = new ClientNode(this.client, benutzername, passwort, true);
            clients.add(newclient);
            failed = false;
            controller.printOutput("USER\tNeue Registrierung von " + benutzername );
            return true;
        }
        return false;
    }
    
    public void client_logout(Socket logout_client) {
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getClient() == logout_client) {
                current_client.setLoggedin(false);
            }
        }
    }

    public void send_message(String message, String code, Socket curr_client) throws IOException {
        System.out.println(code + ' ' + message);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(curr_client.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }

    public void message_all_clients(String message, String code) throws IOException{
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.isLoggedin() == true){
                send_message(message, code, current_client.getClient());
            }
        }
    }

    public void message_all_clients_except_self(String message) throws IOException{
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getClient() != this.client && current_client.isLoggedin() == true){
                send_message(message, "111", current_client.getClient());
            }
        }
    }

    /*public void send_client_list() throws IOException{
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.isLoggedin() == true){
                send_message("", "099", current_client.getClient());
            }
        }
    }*/

    public String[] get_message()throws IOException{
        if(client != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            return new String[]{in.readLine(), in.readLine()};
        }
        else{
            client_logout(client);
            return null;
        }
    }

    //neue Methode
    public ArrayList<String> returnClientList() {
        int listsize = clients.size();
        ArrayList<String> clientsArray = new ArrayList<String>();
        int x = 0;
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.isLoggedin() == true) {
                clientsArray.add(current_client.getName());
            }
        }
        return clientsArray;
    }

    //neue Methode
    public void update_all_active_clients()throws IOException{
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getClient() != this.client && current_client.isLoggedin() == true){
                send_message(current_client.getName(), "099", this.client);
            }
        }
    }

    public boolean getFailed(){
        return this.failed;
    }
}