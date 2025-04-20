package Controller;


import DAO.AttractionDAO;
import DAO.PromotionDAO;
import Modele.Attraction;
import Modele.Promotion;
import Modele.Schedule;
import Vue.Calendar.ButtonNavigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class ReservationWindowController {
    @FXML private Label sessionDateLabel;
    @FXML private Label heureLabel;
    @FXML private Label prixLabel;
    @FXML private ComboBox<Integer> comboboxNbPers;
    @FXML private HBox hboxPanier;
    @FXML private Label attractionNameLabel;
    @FXML private VBox promoContainer;
    @FXML private VBox vboxPrice;
    private Schedule schedule;
    private Attraction attraction;
    private ButtonNavigation panierButton;
    private List<Promotion> applicablePromotions;
    private int PercentageReduc = 0;
    private double basePrice;
    private double totalPrice;

    public void setSchedule(Schedule schedule, Attraction attraction) {
        this.schedule = schedule;
        this.attraction = attraction;
        chargeDatas();
    }

    private void chargeDatas() {
        AttractionDAO attractionDAO = new AttractionDAO();
        basePrice = attractionDAO.getBasePrice(attraction.getAttractionID());
        PromotionDAO promotionDAO = new PromotionDAO();
        LocalDate sessionDate = schedule.getDate();
        applicablePromotions = promotionDAO.getApplicablePromotions(attraction.getAttractionID(), sessionDate);

        panierButton = new ButtonNavigation("Ajouter au panier", 250, 75);
        panierButton.setDisable(true);
        vboxPrice.setVisible(false);
        promoContainer.setVisible(false);
        comboboxNbPers.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                promoContainer.setVisible(true);
                vboxPrice.setVisible(true);
                panierButton.setDisable(false);
                totalPrice = basePrice * newValue;
                calculePercentageReduc();
                prixLabel.setText("Prix de la réservation : " + totalPrice + "€");
                afficherPromos();
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
        heureLabel.setText(schedule.getHourDebut() + " - " + schedule.getHourEnd());
    }

    private void calculePercentageReduc() {
        for (Promotion promotion : applicablePromotions) {
            PercentageReduc = promotion.getPercentage();
            totalPrice = totalPrice - (totalPrice * PercentageReduc/100.0);
            System.out.println(PercentageReduc);
            System.out.println(totalPrice);
            System.out.println(promotion.getName());
        }
    }

    private void afficherPromos() {
        promoContainer.getChildren().clear();
        if (applicablePromotions.isEmpty()) {
            Label noPromo = new Label("Aucune promotion n'est applicable");
            promoContainer.getChildren().add(noPromo);
        }
        for (Promotion promotion : applicablePromotions) {
            VBox VBoxPromo = new VBox();
            Label descPromo = new Label(promotion.getName() + " : " + promotion.getDescription());
            descPromo.getStyleClass().add("info-label");
            VBoxPromo.getStyleClass().add("VBox-promo");
            VBoxPromo.getChildren().add(descPromo);
            promoContainer.getChildren().addAll(VBoxPromo);
        }
    }
}
