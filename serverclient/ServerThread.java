package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

class ServerThread extends Thread{
    Socket client;
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
            String benutzername = in.readLine();
            String passwort = in.readLine();
            boolean success = session.login(benutzername, passwort);
            
            if(success){
                //Nachricht über Anmeldung an alle anderen Nutzer
                session.message_all_clients_except_self(benutzername + " hat sich angemeldet");
                session.update_all_active_clients();
                session.message_all_clients(benutzername, "099");
                //Zusendung aktuell angemeldeter Nutzer an Client
                //session.send_client_list();
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
                    session.message_all_clients(benutzername + " hat sich ausgeloggt!", "111");
                    session.message_all_clients(benutzername, "098");
                    logout = true;
                }
                else if(input[0].equals("100")){
                    session.message_all_clients(benutzername + ": " + input[1], "111");
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