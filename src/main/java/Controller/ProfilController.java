package Controller;

import DAO.ClientDAO;
import Modele.Attraction;
import Modele.Session;
import Modele.Client;
import Modele.User;
import Vue.MainApp;
import Vue.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class ProfilController {

    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;

    @FXML public Label clientLName;
    @FXML public Label clientFName;
    @FXML public Label clientBDate;
    @FXML public Label clientEmail;
    @FXML public PasswordField clientPassword;
    @FXML public Button ordersButton;

    @FXML public void initialize(User user) {
        try {
            previousButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
        Client client = ClientDAO.findClientByEmail(user.getEmail());
        setupProfil(client);
    }

    public void setupProfil (Client client) {
        clientLName.setText(client.getLastName());
        clientFName.setText(client.getFirstName());
        clientBDate.setText(client.getBirthDate().toString());
        clientEmail.setText(client.getEmail());
        clientPassword.setText(client.getPassword());
    }

    @FXML
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
            Parent homeView = loader.load();


            Transition.slideTransition(MainApp.rootPane, homeView, 1000, "UP");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page des différentes attractions : " + e.getMessage());
        }
    }

    @FXML
    private void logoutClick() {
        Session.setUser(null);
        System.out.println("Session utilisateur réinitialisée.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }
}
