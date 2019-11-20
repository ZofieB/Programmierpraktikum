package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Session{
    private Socket client;
    public static HashMap<String, String> users = new HashMap<String, String>();
    public Session(Socket client){
        this.client = client;
    }

    public void login(String benutzername, String passwort) throws IOException {
        //neuer Name
        if(users.isEmpty()){
            users.put(benutzername, passwort);
        }
        else{
            if(! users.containsKey(benutzername)){
                users.put(benutzername, passwort);
            }
            else{
                //vergeben Name mit richtigem Passowort
                if(users.get(benutzername) == passwort){
                    System.out.println("Login erfolgreich!");
                    //muss hier noch was gemacht werden?
                }
                //Wenn Benutzername schon vergeben und falsches Passwort
                else{
                    //Client wird nicht verbunden (entfernt)
                    client.close();
                }

            }
        }
    }
    //public void 
}