package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/login-view.fxml")));
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            stage.setScene(new Scene(root, 1920, 1080));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}