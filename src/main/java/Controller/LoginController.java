package Controller;

import DAO.AdministratorDAO;
import DAO.ClientDAO;
import Modele.Administrator;
import Modele.Session;
import Modele.User;
import Vue.MainApp;
import Vue.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Controller de la page de connexion.
 * Permet de se connecter à l'application.
 */
public class LoginController {
    @FXML public ImageView img;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final ClientDAO clientDAO = new ClientDAO();
    private final AdministratorDAO administratorDAO = new AdministratorDAO();

    public void initialize() {
        Image image = new Image(getClass().getResource("/imgs/MENU.png").toExternalForm());
        img.setImage(image);
    }

    /**
     * Méthode appelée lors du clic sur le bouton de connexion.
     * Vérifie les identifiants et redirige vers la page d'accueil ou d'administration.
     */
    @FXML protected void onLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (clientDAO.loginClient(email, password)) {

            User loggedInUser = ClientDAO.findClientByEmail(email);
            Session.setUser(loggedInUser);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
                Parent homeView = loader.load();

                Transition.slideTransition(MainApp.rootPane, homeView, 1000, "LEFT");
            } catch (IOException exception) {
                System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
                System.out.println("Erreur lors du chargement de la vue : " + exception.getCause());
                //print stack trace
                exception.printStackTrace();
            }

        } else if (administratorDAO.loginAdmin(email, password)) {
            User loggedInUser = AdministratorDAO.findAdminByEmail(email);

            Session.setUser(loggedInUser);
            System.out.println(Session.getUser());
            System.out.println(Session.getUser().getEmail());
            System.out.println("isInstanceof Admin : " + (Session.getUser() instanceof Administrator));


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/admin-view.fxml"));
                Parent homeView = loader.load();

                Transition.slideTransition(MainApp.rootPane, homeView, 1000, "LEFT");
            } catch (IOException exception) {
                System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
                System.out.println("Erreur lors du chargement de la vue : " + exception.getCause());
                //print stack trace
                exception.printStackTrace();
            }


        } else {
            // Affichage d'une alerte en cas d'échec de connexion
            showAlert(AlertType.ERROR, "Échec de connexion", "Email ou mot de passe incorrect.");
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Mot de passe oublié ?".
     * Redirige vers la page de récupération de mot de passe.
     */
    @FXML protected void onRegisterButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/register-view.fxml"));
            Parent registerView = loader.load();

            Transition.slideTransition(MainApp.rootPane, registerView, 1000, "DOWN");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Code ?".
     * Redirige vers la page de code.
     */
    @FXML protected void onCodeLoginButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/code-view.fxml"));
            Parent codeView = loader.load();

            Transition.slideTransition(MainApp.rootPane, codeView, 1000, "DOWN");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

    /**
     * Méthode pour afficher une alerte.
     *
     * @param alertType Type de l'alerte (INFO, ERROR, etc.)
     * @param title     Titre de l'alerte
     * @param message   Message de l'alerte
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}