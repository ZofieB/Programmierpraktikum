package serverclient;

import java.io.*;

public class MessageListener extends Thread{
    private BufferedReader in;

    public MessageListener(BufferedReader in) {
        this.in = in;
    }

    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                if(input.equals("111")){
                    System.out.println(in.readLine());
                }
                else if(input.equals("222")){
                    System.exit(0);
                }
            }
        }catch(Exception e){}
    }
}