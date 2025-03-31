package Controller;

import DAO.AccesSQLDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public ImageView img;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private AccesSQLDatabase db = new AccesSQLDatabase();

    public void initialize() {
        Image image = new Image(getClass().getResource("/imgs/raf.jpg").toExternalForm());
        img.setImage(image);
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