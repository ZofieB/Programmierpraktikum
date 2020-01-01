package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Session{
    private Socket client;
    //public static HashMap<String, String> users = new HashMap<String, String>();
    public static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();

    public Session(Socket client){
        this.client = client;
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
                    client.close();
                    return false;
                }
                //falsches passwort
                else{
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

    public void message_all_clients(String message) throws IOException{
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getClient() != this.client && current_client.isLoggedin() == true){
                send_message(message, "111", current_client.getClient());
            }
        }
    }

    public void send_client_list() throws IOException{
        send_message("Aktuell angemeldete Nutzer:", "111", this.client);
        int listsize = clients.size();
        boolean alone = true;
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getClient() != this.client && current_client.isLoggedin() == true){
                send_message(current_client.getName(), "111", this.client);
                alone = false;
            }
        }
        if(alone == true){
            send_message("Niemand, denn du bist ganz alleine hier!", "111", this.client);
        }
    }

    public String[] get_message()throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        return new String[]{in.readLine(), in.readLine()};
    }

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
}