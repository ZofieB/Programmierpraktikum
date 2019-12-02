package serverclient;

import java.net.*;


class SocketListener extends Thread {
    private ServerSocket server;

    public SocketListener(ServerSocket server){
        this.server = server;
    }

    public void run(){
        try{
            while(true) {
                ServerThread serverThread = new ServerThread(server.accept());
                serverThread.start();
            }
        }catch(Exception e){}
    }
}