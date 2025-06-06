package Controller;

import DAO.ClientDAO;
import Modele.Attraction;
import Modele.Client;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


/**
 * Controller de la page d'information d'une attraction.
 * Permet d'afficher les informations détaillées sur une attraction.
 */
public class InformationController {
    @FXML public ImageView img;
    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;

    @FXML public Label attractionName;
    @FXML public ImageView attractionImg;
    @FXML public TextArea attractionDescription;
    @FXML public Label attractionType;
    @FXML public Label attractionPrice;
    @FXML public Label attractionDuration;
    @FXML public Button bookingButton;
    private Client client;

    /**
     * Initialise la vue d'information d'une attraction.
     * @param attraction Attraction à afficher.
     */
    @FXML public void initialize(Attraction attraction) {
        try {
            previousButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
        setupInformation(attraction);
        bookingButton.setOnAction(e -> reserve(attraction));
    }

    /**
     * Configure les informations de l'attraction à afficher.
     * @param attraction Attraction à afficher.
     */
    public void setupInformation (Attraction attraction) {
        attractionName.setText(attraction.getName());

        if (attraction.getImagePath()!=null && attraction.getImagePath().startsWith("/imgs/Attractions/")) {
            Image imageAttraction = new Image(getClass().getResource(attraction.getImagePath()).toExternalForm());
            attractionImg.setImage(imageAttraction);
        }

        attractionDescription.setText(attraction.getDescription());
        attractionType.setText("Type de manège : " + attraction.getType());
        attractionPrice.setText("Prix d'un tour/personne : " + attraction.getPrice() + "€");
        attractionDuration.setText("Durée d'un tour : " + attraction.getDuration() + " minutes");

    }

    /**
     * Gère le clic sur le bouton de réservation.
     * @param a Attraction à réserver.
     */
    private void reserve(Attraction a) {
        System.out.println("Réservation pour : " + a.getName());
        client = ClientDAO.findClientByEmail(Session.getUser().getEmail());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/calendar-view.fxml"));
            Parent calendarView = loader.load();

            CalendarController controller = loader.getController();
            controller.initialize(a, client);

            Transition.slideTransition(MainApp.rootPane, calendarView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue du calendrier : " + e.getMessage());
        }
    }

    /**
     * Gère le clic sur le bouton de retour.
     * Charge la vue d'accueil.
     */
    @FXML
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
            Parent homeView = loader.load();


            Transition.slideTransition(MainApp.rootPane, homeView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page des différentes attractions : " + e.getMessage());
        }
    }

    /**
     * Gère le clic sur le bouton UserInfo.
     */
    @FXML
    public void userIconClick(ActionEvent actionEvent) {
        // à implémenter si besoin
    }

}