package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;


public class Client{
    public static void main(String[] args) {
        try{
            boolean login = false;
            Socket server = new Socket("localhost", 6666);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            Scanner scan = new Scanner(System.in);
            //while(!login == true) {
                System.out.println(in.readLine());
                out.write(scan.nextLine());
                out.flush();
                System.out.println(in.readLine());
                out.write(scan.nextLine());
                out.flush();
            //}
            server.close();
        }catch(UnknownHostException e) {
            System.out.println("Cannot find host.");
        }catch (IOException e) {
            System.out.println("Error connecting to host.");
        }

    }
}