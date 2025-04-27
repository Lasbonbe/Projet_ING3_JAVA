package Controller;

import DAO.PromotionDAO;
import DAO.ReservationDAO;
import Modele.Client;
import Modele.Promotion;
import Modele.Reservation;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminReservationPageController implements Initializable{
    @FXML private TableView<Reservation> reservationsTable;
    @FXML private TableColumn<Reservation, Integer> colID;
    @FXML private TableColumn<Reservation, Integer> colclient_ID;
    @FXML private TableColumn<Reservation, Integer> colschedule_ID;
    @FXML private TableColumn<Reservation, LocalDate> colDate;
    @FXML private TableColumn<Reservation, Integer> colnb_tickets;
    @FXML private TableColumn<Reservation, Double> coltotal_price;
    @FXML private TableColumn<Reservation, String> colpayement_status;

    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;
    @FXML private ImageView nextButton;

    private final ReservationDAO reservationDAO = new ReservationDAO();

    public void initialize(URL url, ResourceBundle rb) {

        colID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colclient_ID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getClientID()).asObject());
        colschedule_ID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getScheduleID()).asObject());
        colDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        colnb_tickets.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbBillets()).asObject());
        coltotal_price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());
        colpayement_status.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStatut())));

        List<Reservation> reservations = reservationDAO.getAllReservations();
        reservationsTable.setItems(FXCollections.observableArrayList(reservations));

        nextButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/NEXT_BUTTON.png")).toExternalForm()));
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));

        reservationsTable.setPrefHeight(700);
        reservationsTable.setMaxHeight(700);
        reservationsTable.setMinHeight(700);
        reservationsTable.setPrefWidth(1500);
        reservationsTable.setMaxWidth(1500);
        reservationsTable.setMinWidth(1500);

        List<Reservation> list = reservationDAO.getAllReservations();
        reservationsTable.setItems(FXCollections.observableArrayList(list));

    }




    /**
     * Bouton NEXT, fait le lien RESERVATION-VIEW -> ADMIN-PAGE
     *
     * @param event Bah c'est l'event
     */
    @FXML
    private void nextClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "LEFT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bouton PREVIOUS, fait le lien RESERVATION-VIEW -> USER-PAGE
     *
     * @param event Bah c'est l'event
     */
    @FXML private void previousClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-user-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Bouton QUIT
     * @param  e ActionEvent - bah c'est l'event
     * */
    @FXML
    private void logoutClick(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "DOWN");
            Session.clearSession();
        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }
}
