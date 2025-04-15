package Controller;

import DAO.AccesSQLDatabase;
import Vue.MainApp;
import Vue.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {
    @FXML
    public ImageView img;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private final AccesSQLDatabase db = new AccesSQLDatabase();

    public void initialize() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
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

                TranslateTransition slideOut = new TranslateTransition(Duration.millis(1500), MainApp.rootPane.getChildren().get(0));
                slideOut.setToX(-1920); // Slide vers la gauche
                slideOut.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Courbe personnalisée

                TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), homeView);
                slideIn.setToX(0);
                slideIn.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Courbe personnalisée

                // Suppression de l'ancienne vue après la transition
                slideIn.setOnFinished(event -> MainApp.rootPane.getChildren().remove(0));

                // Lancement des animations
                slideOut.play();
                slideIn.play();

            } catch (IOException exception) {
                System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
            }

        } else {
            // Affichage d'une alerte en cas d'échec de connexion
            showAlert(AlertType.ERROR, "Échec de connexion", "Email ou mot de passe incorrect.");
        }
    }

    @FXML
    protected void onRegisterButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/register-view.fxml"));
            Parent registerView = loader.load();

            Transition.slideTransition(MainApp.rootPane, registerView, 1000, "DOWN");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

    @FXML
    protected void onCodeLoginButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/code-view.fxml"));
            Parent codeView = loader.load();

            Transition.slideTransition(MainApp.rootPane, codeView, 1000, "DOWN");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
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