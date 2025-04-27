package Controller;

import DAO.PanierDAO;
import Modele.*;
import Vue.Calendar.ButtonNavigation;
import Vue.MainApp;
import Vue.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PanierController {
    private Schedule schedule;
    private Attraction attraction;
    private ArrayList<PanierItem> panierItems = new ArrayList<>();
    private Client client;
    @FXML private GridPane gridPanePanier;
    @FXML private ImageView backButton;
    @FXML private ImageView quitButton;

    @FXML public void initialize() {
        try {
            backButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
    }

    public void setSchedule(Client client) {
        this.client = client;
        chargerPanier();
        afficherPanier();
    }

    public void setSchedule(Schedule schedule, Attraction attraction, Client client) {
        this.client = client;
        this.schedule = schedule;
        this.attraction = attraction;
        chargerPanier();
        afficherPanier();
    }

    private void chargerPanier() {
        PanierDAO panierDAO = new PanierDAO();
        panierItems = panierDAO.getAllPanier(12);
    }

    private void afficherPanier() {
        int rowIndex = 1;
        for (PanierItem panierItem : panierItems) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            Label debutLabel = new Label(panierItem.getHeureDebut().toLocalTime().format(formatter));
            Label finLabel = new Label(panierItem.getHeureFin().toLocalTime().format(formatter));
            Label nbBilletLabel = new Label(String.valueOf(panierItem.getNbBillets()));
            Label prixLabel = new Label(String.format("%.2f€", panierItem.getPrix()));
            Label reservationLabel = new Label("Réservation " + rowIndex);
            debutLabel.getStyleClass().add("grid-value");
            finLabel.getStyleClass().add("grid-value");
            nbBilletLabel.getStyleClass().add("grid-value");
            prixLabel.getStyleClass().add("grid-value");
            reservationLabel.getStyleClass().add("grid-value");
            gridPanePanier.add(reservationLabel, 0, rowIndex);
            GridPane.setHalignment(reservationLabel, HPos.CENTER);
            GridPane.setValignment(reservationLabel, VPos.CENTER);
            gridPanePanier.add(debutLabel, 1, rowIndex);
            GridPane.setHalignment(debutLabel, HPos.CENTER);
            GridPane.setValignment(debutLabel, VPos.CENTER);
            gridPanePanier.add(finLabel, 2, rowIndex);
            GridPane.setHalignment(finLabel, HPos.CENTER);
            GridPane.setValignment(finLabel, VPos.CENTER);
            gridPanePanier.add(nbBilletLabel, 3, rowIndex);
            GridPane.setHalignment(nbBilletLabel, HPos.CENTER);
            GridPane.setValignment(nbBilletLabel, VPos.CENTER);
            gridPanePanier.add(prixLabel, 4, rowIndex);
            GridPane.setHalignment(prixLabel, HPos.CENTER);
            GridPane.setValignment(prixLabel, VPos.CENTER);
            ButtonNavigation deleteButton = new ButtonNavigation("Supprimer", 175, 75);
            gridPanePanier.add(deleteButton.getRoot(), 5, rowIndex);
            GridPane.setHalignment(deleteButton.getRoot(), HPos.CENTER);
            GridPane.setValignment(deleteButton.getRoot(), VPos.CENTER);
            deleteButton.setOnAction(e -> supprimerPanierItem(panierItem));
            rowIndex++;
        }
    }

    private void supprimerPanierItem(PanierItem panierItem) {
        panierItems.remove(panierItem);
        gridPanePanier.getChildren().clear();
        gridPanePanier.add(new Label("Début"), 1, 0);
        gridPanePanier.add(new Label("Fin"), 2, 0);
        gridPanePanier.add(new Label("Nombre de Billets"), 3, 0);
        gridPanePanier.add(new Label("Prix"), 4, 0);
        afficherPanier();
    }





    /**
     * QUIT BUTTON
     */
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

    /**
     * BACK BUTTON
     */
    @FXML
    private void backClick() {
        try {
            if (schedule != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/reservationWindow-view.fxml"));
                Parent calendarView = loader.load();

                ReservationWindowController controller = loader.getController();
                controller.setSchedule(schedule, attraction, client);

                Transition.slideTransition(MainApp.rootPane, calendarView, 1000, "RIGHT");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/profil-view.fxml"));
                Parent profilView = loader.load();

                ProfilController controller = loader.getController();
                controller.initialize(Session.getUser());

                Transition.slideTransition(MainApp.rootPane, profilView, 1500, "DOWN");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }
}
