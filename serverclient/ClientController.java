package serverclient;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ClientController {
        @FXML
        // The reference of inputText will be injected by the FXML loader
        private TextField username;

        // The reference of outputText will be injected by the FXML loader
        @FXML
        private TextField password;

        @FXML
        private TextArea output;

        // location and resources will be automatically injected by the FXML loader
        @FXML
        private URL location;

        @FXML
        private ResourceBundle resources;

        // Add a public no-args constructor
        public ClientController()
        {
        }

        @FXML
        private void initialize()
        {
        }

        @FXML
        private void printOutput() {
            output.setText(username.getText() + password.getText());
        }
}
