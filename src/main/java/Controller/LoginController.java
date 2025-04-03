package Controller;

import DAO.AccesSQLDatabase;
import Vue.MainApp;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


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

        if (db.LoginSuccess(email, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
                Parent homeView = loader.load();

                homeView.translateXProperty().set(MainApp.rootPane.getWidth());
                MainApp.rootPane.getChildren().add(homeView);

                TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), MainApp.rootPane.getChildren().get(0));
                slideOut.setToX(-1920); // Slide vers la gauche
                TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), homeView);
                slideIn.setToX(0);

                slideIn.setOnFinished(event -> {
                    MainApp.rootPane.getChildren().remove(0);
                });

                slideOut.play();
                slideIn.play();

            } catch (JavaFXPaneException | IOException exception) {
                System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
            }

        } else {
            showAlert(AlertType.ERROR, "Échec de connexion", "Email ou mot de passe incorrect.");
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