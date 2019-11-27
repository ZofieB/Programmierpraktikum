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
        if(trylogin == 1) {
            try{
                Socket server = new Socket("localhost", 6666);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
                //Login Versuch (Methode login disconnected client wenn login fehlgeschlagen)
                System.out.println("Benutzername:");
                out.write(scan.nextLine());
                out.newLine();
                out.flush();
                System.out.println("Passwort:");
                out.write(scan.nextLine());
                out.newLine();
                out.flush();
                boolean login = true;
                System.out.println(in.readLine());

                //Was passiert wenn Client eingeloggt ist
                while(login){
                    boolean logout = false;
                    if(logout == true){
                        break;
                    }
                }

                scan.close();
                server.close();
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
}