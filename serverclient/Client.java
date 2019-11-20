package serverclient;

import java.io.*;
import java.net.*;


public class Client{
    public static void main(String[] args) {
        try{
            Socket server = new Socket("localhost", 6666);
            BufferedInputStream in = new BufferedInputStream(server.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(server.getOutputStream());
            server.close();
        }catch(UnknownHostException e) {
            System.out.println("Cannot find host.");
        }catch (IOException e) {
            System.out.println("Error connecting to host.");
        }

    }
}