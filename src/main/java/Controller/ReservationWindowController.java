package Controller;


import DAO.AttractionDAO;
import DAO.PromotionDAO;
import Modele.Attraction;
import Modele.Promotion;
import Modele.Schedule;
import Modele.Session;
import Vue.Calendar.ButtonNavigation;
import Vue.MainApp;
import Vue.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ReservationWindowController {
    @FXML private Label sessionDateLabel;
    @FXML private Label heureLabel;
    @FXML private Label prixLabel;
    @FXML private ComboBox<Integer> comboboxNbPers;
    @FXML private Label placesDispos;
    @FXML private HBox hboxPanier;
    @FXML private VBox promoContainer;
    @FXML private VBox vboxPrice;
    @FXML private ImageView backButton;
    @FXML private ImageView quitButton;
    @FXML private ImageView img;

    private Schedule schedule;
    private Attraction attraction;
    private ButtonNavigation panierButton;
    private List<Promotion> applicablePromotions;
    private int PercentageReduc = 0;
    private double basePrice;
    private double totalPrice;

    @FXML private void initialize() {
        try {
            img.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/dayWindow_background.png")).toExternalForm()
            ));
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.sessionDateLabel.setText(sessionDate.format(formatter));

        LocalTime tempHourDebut = schedule.getHourDebut().toLocalTime();
        LocalTime tempHourEnd = schedule.getHourEnd().toLocalTime();

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        this.heureLabel.setText(tempHourDebut.format(formatter2) + " - " + tempHourEnd.format(formatter2));

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
                String formattedTotalPrice = String.format("%.2f", totalPrice);
                prixLabel.setText("Prix de la réservation : " + formattedTotalPrice + "€");
                afficherPromos();
            }
        });

        placesDispos.setText("Nombre de places disponibles : " + schedule.getPDispos());

        hboxPanier.getChildren().add(panierButton.getRoot());

        ObservableList<Integer> personnesOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= schedule.getPDispos(); i++) { ///  Test à changer avec le nombre de places dispos
            personnesOptions.add(i);
        }
        comboboxNbPers.setItems(personnesOptions);
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
            Label descPromo = new Label(promotion.getName() + " : " + promotion.getDescription() + "\n\n\n");
            descPromo.getStyleClass().add("info-label");
            VBoxPromo.getStyleClass().add("VBox-promo");
            VBoxPromo.getChildren().add(descPromo);
            promoContainer.getChildren().addAll(VBoxPromo);
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
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/daywindow-view.fxml"));
            Parent dayWindowView = loader.load();

            DayWindowController controller = loader.getController();
            controller.setDate(schedule.getDate(), attraction);

            Transition.slideTransition(MainApp.rootPane, dayWindowView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }
}
