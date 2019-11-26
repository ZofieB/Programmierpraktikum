package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;


public class Client{
    public static void main(String[] args) {
        try{
            Socket server = new Socket("localhost", 6666);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            Scanner scan = new Scanner(System.in);
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

            }

            scan.close();
            server.close();
        }catch(UnknownHostException e) {
            System.out.println("Cannot find host.");
        }catch (IOException e) {
            System.out.println("Error connecting to host.");
        }

    }
}