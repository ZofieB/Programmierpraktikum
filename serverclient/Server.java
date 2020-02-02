package serverclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Server extends Application {
    public static void main(String[] args) throws IOException{
        Application.launch(args);
    }

    public void start(Stage stage) throws IOException{

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader();

        // Path to the FXML File
        //String fxmlDocPathLogin = "/home/sophie/Documents/Programmierpraktikum/serverclient/Server.fxml";
        //String fxmlDocPathLogin = "C:/Users/erika/OneDrive/Dokumente/GitHub/Programmierpraktikum//serverclient/Server.fxml";
        String fxmlDocPathLogin = "C:\\Users\\Sophie\\IdeaProjects\\Programmierpraktikum\\serverclient\\Server.fxml";

        FileInputStream fxmlLoginStream = new FileInputStream(fxmlDocPathLogin);

        // Create the Pane and all Details
        AnchorPane rootLogin = (AnchorPane) loader.load(fxmlLoginStream);

        // Create the Scene
        Scene loginScene = new Scene(rootLogin);

        // Set the Scene to the Stage
        stage.setScene(loginScene);

        // Set the Title to the Stage
        stage.setTitle("Server Log");

        // Display the Stage
        stage.show();

/*        boolean serverstatus = true;

        //Socket starten
        System.out.println("Der Server wird jetzt gestartet! Zum schließen des Servers bitte \"shutdown\" eingeben!");
        ServerSocket server = new ServerSocket(6666);

        //SocketListener starten
        SocketListener socketlistener = new SocketListener(server);
        socketlistener.start();

        //Input einlesen um Server schließen zu können
        Scanner scan = new Scanner(System.in);
        while(serverstatus == true){
            String input = scan.nextLine();
            if(input.equals("shutdown")){
                serverstatus = false;
                socketlistener.shutdown();
            }
        }
        scan.close();
        server.close();*/
    }
}