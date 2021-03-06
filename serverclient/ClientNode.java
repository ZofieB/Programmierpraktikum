package serverclient;

import java.net.*;

//Klasse als Datenstruktur zur Speicherung angemeldeter Clients
public class ClientNode{

    private Socket client;
    private String name;
    private String password;
    private boolean loggedin;

    public ClientNode(Socket client, String name, String password, boolean loggedin){
        this.client = client;
        this.name = name;
        this.password = password;
        this.loggedin = loggedin;
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

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

}