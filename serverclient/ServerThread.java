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
                session.message_all_clients(benutzername + " hat sich angemeldet");
                //Nachricht, das Nutzerliste aktualisiert werden muss
                /*out.write("099");
                out.newLine();
                out.flush();*/
                session.send_message("","099", client);

                //Zusendung aktuell angemeldeter Nutzer an Client
                //session.send_client_list();
            }

            //Input vom Client empfangen (Message Listener)
            boolean logout = false;
            while(!logout){
                String[] input = session.get_message();
                System.out.println(input[0]);
                System.out.println(input[1]);
                if(input[0].equals("101")){
                    session.client_logout(client);
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