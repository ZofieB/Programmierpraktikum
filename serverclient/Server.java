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
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            Session session = new Session(client);
            //Login bzw. Registrierung
            //Bei falscher Eingabe wird Client durch Methode aus Session direkt wieder ausgeloggt
            out.write("Name eingeben:");
            out.flush(); //BufferedWriter
            String benutzername = in.readLine();
            out.write("Passwort eingeben:");
            out.flush();  //BufferedWriter
            String passwort = in.readLine();
            session.login(benutzername, passwort);


        } catch(IOException e){}
    }
}