package Controller;

import DAO.AccesSQLDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private AccesSQLDatabase db = new AccesSQLDatabase();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Add your login logic here
        if (db.LoginSuccess(email, password)) {
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + email + "!");
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid email or password.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}