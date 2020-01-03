package serverclient;

import java.io.*;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class MessageListener extends Thread{
    private BufferedReader in;
    private TextArea outputField;
    private ClientController controller;

    public MessageListener(BufferedReader in, TextArea outputField, ClientController controller) {
        this.in = in;
        this.outputField = outputField;
        this.controller = controller;
    }

    public void run(){
        try{
            while(true) {
                String input = in.readLine();
                System.out.println(input);
                if(input.equals("111")){
                    controller.updateTextArea(in.readLine());
                    //outputField.appendText(in.readLine());
                    //System.out.println(in.readLine());
                }
                else if(input.equals("222")){
                    System.exit(0);
                }
                else if(input.equals("099")) {
                    //controller.updateClientList();
                }
            }
        }catch(Exception E){}
    }
}