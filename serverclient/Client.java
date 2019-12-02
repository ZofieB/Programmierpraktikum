package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;


public class Client{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Soll eine Verbindung mit dem Server aufgebaut werden?");
        System.out.println("1 - Ja \t 0 - Nein");
        int trylogin = scan.nextInt();
        scan.nextLine();
        if(trylogin == 1) {
            try{
                Socket server = new Socket("localhost", 6666);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
                
                //Login Versuch (Methode login disconnected client wenn login fehlgeschlagen)
                System.out.println("Benutzername:");
                String client_benutzername = scan.nextLine();
                out.write(client_benutzername);
                out.newLine();
                out.flush();
                System.out.println("Passwort:");
                out.write(scan.nextLine());
                out.newLine();
                out.flush();
                boolean login = true;

                //Nachrichten empfangen
                MessageListener messages = new MessageListener(in);
                messages.start();

                //Abmeldung vom Server
                System.out.println("Zum Logout 101 eingeben!");
                while(login) {
                    String input = scan.nextLine();
                    if(input.equals("101")){
                        send_server_message(input, "101", server);
                        login = false;
                    }
                    else{
                        send_server_message(input, "100", server);
                    }
                }
                scan.close();
            }catch(UnknownHostException e) {
                System.out.println("Cannot find host.");
            }catch (IOException e) {
                System.out.println("Error connecting to host.");
            }
        }
        else if(trylogin == 0) {
            System.out.println("Verbindung wird nicht hergestellt!");
        }
        else{
            System.out.println("Eingabe war nicht gültig. Verbindung wird abgebrochen");
        }

    }

    static public void send_server_message(String message, String code, Socket server)throws IOException{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        out.write(code);
        out.newLine();
        out.write(message);
        out.newLine();
        out.flush();
    }
}