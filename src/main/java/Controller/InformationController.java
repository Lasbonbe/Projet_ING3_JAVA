package Controller;

import Modele.Attraction;
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


public class InformationController {
    @FXML public ImageView img;
    @FXML private ImageView searchButton;
    @FXML private ImageView quitButton;

    @FXML public Label attractionName;
    @FXML public ImageView attractionImg;
    @FXML public TextArea attractionDescription;
    @FXML public Label attractionType;
    @FXML public Label attractionPrice;
    @FXML public Label attractionDuration;
    @FXML public Button bookingButton;

    @FXML public void initialize(Attraction attraction) {
        try {
            Image image = new Image(getClass().getResource("/imgs/CALENDAR.png").toExternalForm());
            img.setImage(image);
            searchButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/ACCOUNT_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
        setupInformation(attraction);
        bookingButton.setOnAction(e -> reserve(attraction));
    }

    public void setupInformation (Attraction attraction) {
        attractionName.setText(attraction.getName());

        Image imageAttraction = new Image(getClass().getResource(attraction.getImagePath()).toExternalForm());
        attractionImg.setImage(imageAttraction);

        attractionDescription.setText(attraction.getDescription());
        attractionType.setText("Type de manège : " + attraction.getType());
        attractionPrice.setText("Prix d'un tour/personne : " + attraction.getPrice() + "€");
        attractionDuration.setText("Durée d'un tour : " + attraction.getDuration() + " minutes");

    }

    private void reserve(Attraction a) {
        System.out.println("Réservation pour : " + a.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/calendar-view.fxml"));
            Parent calendarView = loader.load();

            CalendarController controller = loader.getController();
            controller.initialize(a);

            Transition.slideTransition(MainApp.rootPane, calendarView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue du calendrier : " + e.getMessage());
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

    @FXML
    public void userIconClick(ActionEvent actionEvent) {
        // à implémenter si besoin
    }

}