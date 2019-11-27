package serverclient;

import java.io.*;
import java.net.*;

//Klasse als Datenstruktur zur Speicherung angemeldeter Clients
public class ClientNode{
    //Client auch als Variable speichern?
    private Socket client;
    private String name;
    private String password;

    public ClientNode(Socket client, String name, String password){
        this.client = client;
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}