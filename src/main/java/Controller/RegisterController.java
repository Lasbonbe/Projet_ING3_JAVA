package Controller;

import DAO.AccesSQLDatabase;
import Vue.MainApp;
import Vue.Transition;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;

/**
 * Controller de la page d'enregistrement.
 * Permet de gérer l'enregistrement d'un nouvel utilisateur.
 */
public class RegisterController {

    private final AccesSQLDatabase db = new AccesSQLDatabase();

    @FXML
    ImageView return_icon;

    @FXML
    public TextField emailField;
    @FXML
    public TextField confirmEmailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPasswordField;
    @FXML
    public TextField prenomField;
    @FXML
    public TextField nomField;
    @FXML
    public DatePicker ageField;

    @FXML
    public Label passwordErrorLabel;
    @FXML
    public Label emailErrorLabel;
    @FXML
    public Label confirmEmailErrorLabel;
    @FXML
    public Label confirmPasswordErrorLabel;

    @FXML
    public Label errorLabel;
    @FXML
    public Label successLabel;
    @FXML
    public Label emailValidLabel;
    @FXML
    public Label passwordValidLabel;

    /**
     * Méthode pour gérer le clic sur le bouton d'enregistrement.
     */
    @FXML
    protected void onRegisterClick() {
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        LocalDate birthDate = ageField.getValue();
        String email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validations
        if (!email.contains("@")) {
            return;
        }
        if (!email.equals(confirmEmail)) {
            return;
        }
        if (password.length() < 8 || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            return;
        }

        // Enregistrement dans la base de données
        boolean registered = db.registerClient(prenom, nom, java.sql.Date.valueOf(birthDate), email, password);

        if (registered) {
            slideToLogin();
        } else {
        }
    }

    /**
     * Méthode pour l'afficage et la transition vers la vue de connexion.
     */
    private void slideToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginview = loader.load();

            Transition.slideTransition(MainApp.rootPane, loginview, 1000, "DOWN");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

    /**
     * Méthode d'initialisation de la vue.
     * Applique les styles et initialise les listeners pour la validation des champs.
     */
    @FXML
    public void initialize() {
        // Appliquer les classes de style sur les labels
        passwordErrorLabel.getStyleClass().add("error-label");
        emailErrorLabel.getStyleClass().add("error-label");
        confirmEmailErrorLabel.getStyleClass().add("error-label");
        confirmPasswordErrorLabel.getStyleClass().add("error-label");
        emailValidLabel.getStyleClass().add("success-label");
        passwordValidLabel.getStyleClass().add("success-label");

        // Validation dynamique pour le mot de passe et l'email
        passwordField.textProperty().addListener((obs, oldText, newText) -> validatePassword());
        confirmPasswordField.textProperty().addListener((obs, oldText, newText) -> validatePasswordMatch());
        emailField.textProperty().addListener((obs, oldText, newText) -> validateEmail());
        confirmEmailField.textProperty().addListener((obs, oldText, newText) -> validateEmailMatch());

        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/RETURN_BUTTON.png").toExternalForm());
        return_icon.setImage(image);
    }

    /**
     * Méthode de vérification du mot de passe (8 caractères minimum et au moins un symbole).
     */
    private void validatePassword() {
        String password = passwordField.getText();
        if (password.length() < 8 || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            passwordErrorLabel.setText("Le mot de passe doit contenir au moins 8 caractères et un symbole.");
            passwordErrorLabel.setVisible(true);
            passwordValidLabel.setVisible(false);
            // Appliquer le style d'erreur sur le champ
            if (!passwordField.getStyleClass().contains("text-field-error")) {
                passwordField.getStyleClass().remove("text-field-success");
                passwordField.getStyleClass().add("text-field-error");
            }
            playFadeTransition(passwordErrorLabel);
        } else {
            passwordErrorLabel.setVisible(false);
            passwordValidLabel.setText("Mot de passe valide.");
            passwordValidLabel.setVisible(true);
            if (!passwordField.getStyleClass().contains("text-field-success")) {
                passwordField.getStyleClass().remove("text-field-error");
                passwordField.getStyleClass().add("text-field-success");
            }
            playFadeTransition(passwordValidLabel);
        }
    }

    /**
     * Méthode de vérification de la correspondance des mots de passe.
     */
    private void validatePasswordMatch() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!password.equals(confirmPassword)) {
            confirmPasswordErrorLabel.setText("Les mots de passe ne correspondent pas.");
            confirmPasswordErrorLabel.setVisible(true);
            playFadeTransition(confirmPasswordErrorLabel);
        } else {
            confirmPasswordErrorLabel.setVisible(false);
        }
    }

    /**
     * Méthode de vérification de l'email (doit contenir un '@').
     */
    private void validateEmail() {
        String email = emailField.getText();
        if (!email.contains("@")) {
            emailErrorLabel.setText("L'email doit contenir un '@'.");
            emailErrorLabel.setVisible(true);
            emailValidLabel.setVisible(false);
            if (!emailField.getStyleClass().contains("text-field-error")) {
                emailField.getStyleClass().remove("text-field-success");
                emailField.getStyleClass().add("text-field-error");
            }
            playFadeTransition(emailErrorLabel);
        } else {
            emailErrorLabel.setVisible(false);
            emailValidLabel.setText("Email valide.");
            emailValidLabel.setVisible(true);
            if (!emailField.getStyleClass().contains("text-field-success")) {
                emailField.getStyleClass().remove("text-field-error");
                emailField.getStyleClass().add("text-field-success");
            }
            playFadeTransition(emailValidLabel);
        }
    }

    /**
     * Méthode de vérification de la correspondance des emails.
     */
    private void validateEmailMatch() {
        String email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();
        if (!email.equals(confirmEmail)) {
            confirmEmailErrorLabel.setText("Les emails ne correspondent pas.");
            confirmEmailErrorLabel.setVisible(true);
            playFadeTransition(confirmEmailErrorLabel);
        } else {
            confirmEmailErrorLabel.setVisible(false);
        }
    }

    /**
     * Méthode de transition de fondu pour les labels.
     * @param label Le label à animer
     */
    private void playFadeTransition(Label label) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), label);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    /**
     * Méthode pour gérer le clic sur l'icône de retour.
     * Charge la vue de connexion.
     */
    @FXML
    private void returnIconClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginview = loader.load();
            System.out.println("Slide to login");

            Transition.slideTransition(MainApp.rootPane, loginview, 1000, "UP");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }
}
