package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Session{
    private Socket client;
    public static HashMap<String, String> users = new HashMap<String, String>();
    public static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();

    public Session(Socket client){
        this.client = client;
    }

    public void login(String benutzername, String passwort) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
       
        //Es gab keine registrierten Nutzer
        if(users.isEmpty()){
            users.put(benutzername, passwort);
            ClientNode clientnode = new ClientNode(client, benutzername, passwort);
            clients.add(clientnode);
        }
        //Es gibt schon registrierte Nutzer
        else{ 
            //Name noch nicht vergeben
            if(! users.containsKey(benutzername)){ 
                users.put(benutzername, passwort);
                ClientNode clientnode = new ClientNode(client, benutzername, passwort);
                clients.add(clientnode);
            }
            //Name vergeben und richtiges Passwort
            else{
                if(users.get(benutzername) == passwort){
                    System.out.println("Login erfolgreich!");
                }
            //Name vergeben und falsches Passwort
                else{
                    //Client wird nicht verbunden (entfernt)
                    client.close();
                }

            }
        }
        send_message("Login erfolgreich!", "111", this.client);
    }
    
    public void client_logout(String benutzername) {
        users.remove(benutzername);
        int listsize = clients.size();
        for(int i = 0; i < listsize; i++){
            ClientNode current_client = clients.get(i);
            if(current_client.getName().equals(benutzername)) {
                clients.remove(i);
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
            if(current_client.getClient() != this.client){
                send_message(message, "111", current_client.getClient());
            }
        }
    }

    public void send_client_list() throws IOException{
        send_message("Aktuell angemeldete Nutzer:", "111", this.client);

        for ( String key : users.keySet() ) {
            send_message(key, "111", this.client);
        }
    }

    public String[] get_message()throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        return new String[]{in.readLine(), in.readLine()};
    }
}