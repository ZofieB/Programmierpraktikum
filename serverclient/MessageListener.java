package serverclient;

import java.io.*;
import javafx.scene.control.TextArea;

public class MessageListener extends Thread{
    private BufferedReader in;
    private TextArea outputField;

    public MessageListener(BufferedReader in, TextArea outputField) {
        this.in = in;
        this.outputField = outputField;
    }

    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                if(input.equals("111")){
                    outputField.setText(in.readLine());
                    //System.out.println(in.readLine());
                }
                else if(input.equals("222")){
                    System.exit(0);
                }
            }
        }catch(Exception e){}
    }
}