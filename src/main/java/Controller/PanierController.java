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

/**
 * Controller de la page Panier.
 * Permet de gérer le panier de l'utilisateur.
 */
public class PanierController {
    private Schedule schedule;
    private Attraction attraction;
    private ArrayList<PanierItem> panierItems = new ArrayList<>();
    private Client client;
    PanierDAO panierDAO = new PanierDAO();
    @FXML private GridPane gridPanePanier;
    @FXML private ImageView backButton;
    @FXML private ImageView quitButton;

    /**
     * Initialisation de la vue Panier.
     * Charge les images et initialise le panier.
     */
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

    /**
     * Méthode pour initialiser le panier avec un client, le charger et l'afficher.
     *
     * @param client Le client dont le panier doit être affiché.
     */
    public void setSchedule(Client client) {
        this.client = client;
        chargerPanier();
        afficherPanier();
    }

    /**
     * Méthode pour initialiser le panier avec un horaire, une attraction et un client.
     *
     * @param schedule L'horaire de la réservation.
     * @param attraction L'attraction réservée.
     * @param client Le client qui a réservé.
     */
    public void setSchedule(Schedule schedule, Attraction attraction, Client client) {
        this.client = client;
        this.schedule = schedule;
        this.attraction = attraction;
        chargerPanier();
        afficherPanier();
    }

    /**
     * Méthode pour charger le panier du client.
     * Récupère tous les éléments du panier à partir de la base de données.
     */
    private void chargerPanier() {
        panierItems = panierDAO.getAllPanier(client.getUserID());
    }

    /**
     * Méthode pour afficher le panier dans la vue.
     * Crée les labels et les boutons pour chaque élément du panier.
     */
    private void afficherPanier() {
        int rowIndex = 1;
        for (PanierItem panierItem : panierItems) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            Label debutLabel = new Label(panierItem.getHeureDebut().toLocalTime().format(formatter));
            Label finLabel = new Label(panierItem.getHeureFin().toLocalTime().format(formatter));
            Label nbBilletLabel = new Label(String.valueOf(panierItem.getNbBillets()));
            Label prixLabel = new Label(String.format("%.2f€", panierItem.getPrix()));
            Label reservationLabel = new Label(panierItem.getAttractionName());
            reservationLabel.setWrapText(true);
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

    /**
     * Méthode pour supprimer un élément du panier.
     *
     * @param panierItem L'élément à supprimer du panier.
     */
    private void supprimerPanierItem(PanierItem panierItem) {
        panierItems.remove(panierItem);
        gridPanePanier.getChildren().clear();
        Label labelReservation = new Label("Attraction");
        labelReservation.getStyleClass().add("grid-value");
        gridPanePanier.add(labelReservation, 0, 0);
        GridPane.setHalignment(labelReservation, HPos.CENTER);
        GridPane.setValignment(labelReservation, VPos.CENTER);
        Label labelDeb = new Label("Début");
        labelDeb.getStyleClass().add("grid-value");
        gridPanePanier.add(labelDeb, 1, 0);
        GridPane.setHalignment(labelDeb, HPos.CENTER);
        GridPane.setValignment(labelDeb, VPos.CENTER);
        Label labelFin = new Label("Fin");
        labelFin.getStyleClass().add("grid-value");
        gridPanePanier.add(labelFin, 2, 0);
        GridPane.setHalignment(labelFin, HPos.CENTER);
        GridPane.setValignment(labelFin, VPos.CENTER);
        Label labelNbBil = new Label("Nombre de Billets");
        labelNbBil.getStyleClass().add("grid-value");
        gridPanePanier.add(labelNbBil, 3, 0);
        GridPane.setHalignment(labelNbBil, HPos.CENTER);
        GridPane.setValignment(labelNbBil, VPos.CENTER);
        Label labelPrix = new Label("Prix");
        labelPrix.getStyleClass().add("grid-value");
        gridPanePanier.add(labelPrix, 4, 0);
        GridPane.setHalignment(labelPrix, HPos.CENTER);
        GridPane.setValignment(labelPrix, VPos.CENTER);
        Label labelSuppr = new Label("Supprimer");
        labelSuppr.getStyleClass().add("grid-value");
        gridPanePanier.add(labelSuppr, 5, 0);
        GridPane.setHalignment(labelSuppr, HPos.CENTER);
        GridPane.setValignment(labelSuppr, VPos.CENTER);
        int panierID = panierDAO.getPanierId(client.getUserID());
        int scheduleID = panierDAO.getScheduleIdFromPanierElement(panierItem.getId());
        panierDAO.supprimerElementPanier(scheduleID, panierID);
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

    /**
     * PAYMENT BUTTON
     */
    @FXML private void paymentClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/payment-view.fxml"));
            Parent paymentView = loader.load();

            PaymentController controller = loader.getController();
            controller.setSchedule(schedule, attraction, client);

            if (schedule != null) {
                Transition.slideTransition(MainApp.rootPane, paymentView, 1000, "LEFT");
            } else {
                Transition.slideTransition(MainApp.rootPane, paymentView, 1000, "UP");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }
}
