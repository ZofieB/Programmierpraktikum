package serverclient;

import java.io.IOException;
import java.net.*;
import java.util.*;

class SocketListener extends Thread {
    private ServerSocket server;
    ArrayList<ServerThread> currentThreads = new ArrayList<ServerThread>();

    public SocketListener(ServerSocket server){
        this.server = server;
    }
    //Annahme neuer Clients durch öffnen eines neuen Threads
    public void run(){
        try{
            while(true) {
                ServerThread serverThread = new ServerThread(server.accept());
                currentThreads.add(serverThread);
                serverThread.start();
            }
        }catch(Exception e){}
    }
    //Schließen des Servers
    public void shutdown() throws IOException{
        int size = currentThreads.size();
        for(int i = 0; i < size; i++){
            ServerThread thread = currentThreads.get(i);
            if (thread.getState() != State.TERMINATED){
                thread.shutdown();
            }
        }
    }
}