package Controller;


import DAO.AttractionDAO;
import Modele.Attraction;
import Modele.Schedule;
import Vue.Calendar.ButtonNavigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ReservationWindowController {
    @FXML private Label sessionDateLabel;
    @FXML private Label heureLabel;
    @FXML private Label prixLabel;
    @FXML private ComboBox<Integer> comboboxNbPers;
    @FXML private HBox hboxPanier;
    @FXML private Label attractionNameLabel;
    private Schedule schedule;
    private Attraction attraction;
    private ButtonNavigation panierButton;
    private int basePrice;

    public void setSchedule(Schedule schedule, Attraction attraction) {
        this.schedule = schedule;
        this.attraction = attraction;
        chargeDatas();
    }

    private void chargeDatas() {
        AttractionDAO attractionDAO = new AttractionDAO();
        basePrice = attractionDAO.getBasePrice(this.attraction.getAttractionID());
        System.out.println(basePrice);

        panierButton = new ButtonNavigation("Ajouter au panier");
        panierButton.setDisable(true);
        comboboxNbPers.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                panierButton.setDisable(false);
                int totalPrice = basePrice * newValue;
                prixLabel.setText("Prix de la réservation : " + totalPrice + "€");
            }
        });
        hboxPanier.getChildren().add(panierButton.getRoot());

        ObservableList<Integer> personnesOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) { ///  Test à changer avec le nombre de places dispos
            personnesOptions.add(i);
        }
        comboboxNbPers.setItems(personnesOptions);

        sessionDateLabel.setText("Session du " + this.schedule.getDate());
        attractionNameLabel.setText(attraction.getName());
        heureLabel.setText("8:00:00 - 9:00:00");







    }
}
