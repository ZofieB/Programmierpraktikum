package serverclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class MessageListener extends Thread{
    private BufferedWriter out;
    private BufferedReader in;

    public MessageListener(BufferedReader in, BufferedWriter out) {
        this.in = in;
        this.out = out;
    }

    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                if(input.equals("111")){
                    System.out.println(in.readLine());
                }
            }
        }catch(Exception e){}
    }
}