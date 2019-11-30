package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(6666);
        try{ //Listener ob Clients sich anmelden
            while(true) {
                ServerThread serverThread = new ServerThread(server.accept());
                serverThread.start();
            }
            //todo: Server schließen
        }catch (Exception e){}
    }
}

class ServerThread extends Thread{
    Socket client;
    static ArrayList<ClientNode> activeClients = new ArrayList<ClientNode>();
    ServerThread(Socket client) {
        this.client = client;
    }
    
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Session session = new Session(client);
            //Login bzw. Registrierung
            //Bei falscher Eingabe wird Client durch Methode aus Session direkt wieder ausgeloggt
            String benutzername = in.readLine();
            String passwort = in.readLine();
            session.login(benutzername, passwort);
            
            //Nachricht über Anmeldung an alle anderen Nutzer
            session.message_all_clients(benutzername + " hat sich angemeldet");

            //Zusendung aktuell angemeldeter Nutzer an Client
            session.send_client_list();

            boolean logout = false;
            while(!logout){
                String[] input = session.get_message();
                if(input[0].equals("101")){
                    session.client_logout(benutzername);
                    session.message_all_clients(benutzername + " hat sich ausgeloggt!");
                    logout = true;
                }
                else if(input[0].equals("100")){
                    session.message_all_clients(benutzername + ": " + input[1]);
                }
            }
            client.close();

        } catch(IOException e){}
    }
}