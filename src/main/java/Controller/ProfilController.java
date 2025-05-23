package Controller;

import DAO.ClientDAO;
import DAO.ReservationDAO;
import Modele.*;
import Vue.MainApp;
import Vue.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ProfilController {

    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;

    @FXML private Label clientLName;
    @FXML private Label clientFName;
    @FXML private Label clientBDate;
    @FXML private Label clientEmail;
    @FXML private PasswordField clientPassword;
    @FXML private Button ordersButton;
    @FXML private GridPane gridPaneReservations;
    private Client client;

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
        this.client = ClientDAO.findClientByEmail(user.getEmail());
        setupProfil(client);
        setupReservations(client);
    }

    public void setupProfil (Client client) {
        clientLName.setText(client.getLastName());
        clientFName.setText(client.getFirstName());
        clientBDate.setText(client.getBirthDate().toString());
        clientEmail.setText(client.getEmail());
        clientPassword.setText(client.getPassword());
    }

    public void setupReservations (Client client){
        ArrayList<Reservation> clientReservation = ReservationDAO.getReservationsByClient(client.getUserID());

        int rowIndex = 1;

        for (Reservation reservation : clientReservation) {
            Label hourDebutRes= new Label(reservation.getHeureDebut().toString());
            Label hourEndRes = new Label(reservation.getHeureFin().toString());
            Label dateRes = new Label(reservation.getDate().toString());
            Label nbrTicketsRes = new Label(String.valueOf(reservation.getNbBillets()));
            Label priceRes= new Label(String.format("%.2f€", reservation.getPrix()));

            hourDebutRes.getStyleClass().add("data-label");
            hourEndRes.getStyleClass().add("data-label");
            dateRes.getStyleClass().add("data-label");
            nbrTicketsRes.getStyleClass().add("data-label");
            priceRes.getStyleClass().add("data-label");

            GridPane.setHalignment(hourDebutRes, HPos.CENTER);
            GridPane.setValignment(hourDebutRes, VPos.CENTER);
            GridPane.setHalignment(hourEndRes, HPos.CENTER);
            GridPane.setValignment(hourEndRes, VPos.CENTER);
            GridPane.setHalignment(dateRes, HPos.CENTER);
            GridPane.setValignment(dateRes, VPos.CENTER);
            GridPane.setHalignment(nbrTicketsRes, HPos.CENTER);
            GridPane.setValignment(nbrTicketsRes, VPos.CENTER);
            GridPane.setHalignment(priceRes, HPos.CENTER);
            GridPane.setValignment(priceRes, VPos.CENTER);

            gridPaneReservations.add(hourDebutRes, 0, rowIndex);
            gridPaneReservations.add(hourEndRes, 1, rowIndex);
            gridPaneReservations.add(dateRes, 2, rowIndex);
            gridPaneReservations.add(nbrTicketsRes, 3, rowIndex);
            gridPaneReservations.add(priceRes, 4, rowIndex);

            rowIndex++;
        }
    }

    @FXML private void panierClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/panier-view.fxml"));
            Parent panierView = loader.load();

            PanierController controller = loader.getController();
            controller.setSchedule(client);

            Transition.slideTransition(MainApp.rootPane, panierView, 1000, "UP");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page des différentes attractions : " + e.getMessage());
        }
    }

    @FXML
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
            Parent homeView = loader.load();


            Transition.slideTransition(MainApp.rootPane, homeView, 1000, "DOWN");
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
