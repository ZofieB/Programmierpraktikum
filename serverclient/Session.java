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
        //neuer Name
        if(users.isEmpty()){
            users.put(benutzername, passwort);
            ClientNode clientnode = new ClientNode(client, benutzername, passwort);
            clients.add(clientnode);
        }
        else{
            if(! users.containsKey(benutzername)){
                users.put(benutzername, passwort);
                ClientNode clientnode = new ClientNode(client, benutzername, passwort);
                clients.add(clientnode);
            }
            else{
                //vergeben Name mit richtigem Passowort
                if(users.get(benutzername) == passwort){
                    System.out.println("Login erfolgreich!");
                }
                //Wenn Benutzername schon vergeben und falsches Passwort
                else{
                    //Client wird nicht verbunden (entfernt)
                    client.close();
                }

            }
        }
    }

    public void message_all_clients(String message) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        //muss noch an alle Clients aktualisiert werden!!
        out.write(message);
        out.newLine();
        out.flush();
    }

    public void send_client_list() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

       //über alle Hashmapobjekte iterieren
    }
}