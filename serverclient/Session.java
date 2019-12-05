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

    public boolean login(String benutzername, String passwort) throws IOException {
        //Es gab keine registrierten Nutzer
        //Name noch nicht vergeben
        int listsize = clients.size();
        boolean stop = false;
        int i = 0;
        while(!stop && i < listsize){
            ClientNode current_client = clients.get(i);
            //Suche nach bekanntem Nutzernamen
            if(benutzername.equals(current_client.getName())){
                stop = true;
                if(passwort.equals(current_client.getPassword()) && current_client.isLoggedin() == false){
                    send_message("Login erfolgreich! Willkommen zurück " + benutzername, "111", this.client);
                    //Socket aktualisieren
                    current_client.setClient(this.client);
                    current_client.setLoggedin(true);
                    return true;
                }
                else if(passwort.equals(current_client.getPassword()) && current_client.isLoggedin() == true){
                    send_message("Dieser Nutzer ist schon eingeloggt! Die Verbindung wird getrennt!", "111", this.client);
                    client.close();
                    return false;
                }
                else{
                    send_message("Login fehlgeschlagen! Die Verbindung wird getrennt!", "111", this.client);
                    client.close();
                    return false;
                }
            }
            i++;
        }
        //Schleife ist vollständig durchgelaufen und es gab keinen Benutzernamenmatch oder es ist noch niemand registriert--> Registrierung
        if((i == listsize  && stop == false) || clients.isEmpty() == true){
            send_message("Sie werden jetzt registriert und eingeloggt!", "111", this.client);
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
}