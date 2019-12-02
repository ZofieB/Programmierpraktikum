package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    public static void main(String[] args) throws IOException{
        boolean serverstatus = true;

        System.out.println("Der Server wird jetzt gestartet! Zum schließen des Servers bitte \"shutdown\" eingeben!");
        ServerSocket server = new ServerSocket(6666);

        SocketListener socketlistener = new SocketListener(server);
        socketlistener.start();

        Scanner scan = new Scanner(System.in);
        while(serverstatus == true){
            String input = scan.nextLine();
            if(input.equals("shutdown")){
                serverstatus = false;
                socketlistener.shutdown();
            }
        }
        scan.close();
        server.close();
    }
}