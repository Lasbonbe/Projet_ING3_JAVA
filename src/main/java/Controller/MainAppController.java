package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainAppController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button loginButton;

    @FXML
    protected void onLoginButtonClick() {
        welcomeLabel.setText("Login button clicked!");
        // Add your login logic here
    }
}