package Controller;


import DAO.AttractionDAO;
import DAO.PanierDAO;
import DAO.PromotionDAO;
import DAO.ScheduleDAO;
import Modele.*;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller de la fenêtre de réservation.
 * Permet de gérer la réservation d'une attraction.
 */
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
    private Client client;
    private LocalDate birthdate;

    /**
     * Initialisation de la vue
     */
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

    /**
     * Méthode pour set la date et l'attraction
     * @param schedule Schedule - Horaire de la réservation
     * @param attraction Attraction - Attraction à réserver
     */
    public void setSchedule(Schedule schedule, Attraction attraction, Client client) {
        this.client = client;
        if (this.client != null) {
            this.birthdate = client.getBirthDate().toLocalDate();
        } else {
            this.birthdate = null;
        }
        this.schedule = schedule;
        this.attraction = attraction;
        chargeDatas();
    }

    /**
     * Méthode pour le chargement des données
     */
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

        if (this.client != null) {
            applicablePromotions = promotionDAO.getApplicablePromotions(attraction.getAttractionID(), sessionDate);
            filtrerPromotions();
        } else {
            applicablePromotions = new ArrayList<>();
        }



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
        panierButton.setOnAction(e -> panierButtonClick());

        placesDispos.setText("Nombre de places disponibles : " + schedule.getPDispos());

        hboxPanier.getChildren().add(panierButton.getRoot());

        ObservableList<Integer> personnesOptions = FXCollections.observableArrayList();
        for (int i = 1; i <= schedule.getPDispos(); i++) {
            personnesOptions.add(i);
        }
        comboboxNbPers.setItems(personnesOptions);
    }

    /**
     * Méthode pour le calcul de la réduction
     */
    private void calculePercentageReduc() {
        for (Promotion promotion : applicablePromotions) {
            PercentageReduc = promotion.getPercentage();
            totalPrice = totalPrice - (totalPrice * PercentageReduc/100.0);
            System.out.println(PercentageReduc);
            System.out.println(totalPrice);
            System.out.println(promotion.getName());
        }
    }

    /**
     * Méthode pour l'affichage des promotions
     */
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/daywindow-view.fxml"));
            Parent dayWindowView = loader.load();

            DayWindowController controller = loader.getController();
            controller.setDate(schedule.getDate(), attraction, client);

            Transition.slideTransition(MainApp.rootPane, dayWindowView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }

    private void panierButtonClick() {
        if (comboboxNbPers.getValue() == null) {
            return; // Pas de sélection de nombre de billets
        }

        int nbBillets = comboboxNbPers.getValue();

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        if (this.client != null) {
            int clientID = Session.getUser().getUserID(); // Récupérer l'ID de l'utilisateur connecté
            PanierDAO panierDAO = new PanierDAO();
            System.out.println("ID du schedule : " + schedule.getIdSchedule());
            panierDAO.addReservationPanier(clientID, schedule, nbBillets, totalPrice);
            schedule.setPlacesDispos(scheduleDAO.getPdispos(schedule));
        } else {
            PanierItem panierItem = new PanierItem(99, schedule.getIdSchedule(), nbBillets, totalPrice);
            PanierInvite.addPanierItem(panierItem);
            scheduleDAO.tempReservation(schedule, nbBillets);
            schedule.setPlacesDispos(scheduleDAO.getPdispos(schedule));
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/panier-view.fxml"));
            Parent calendarView = loader.load();

            PanierController controller = loader.getController();
            controller.setSchedule(schedule, attraction, client);

            Transition.slideTransition(MainApp.rootPane, calendarView, 1000, "LEFT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }

    private void filtrerPromotions() {
        ArrayList<Promotion> promotionsToRemove = new ArrayList<>();
        for (Promotion promotion : applicablePromotions) {
            if (promotion.getDescription().contains("Réduction sur l'âge")) {
                boolean cond = false;
                if (promotion.getDescription().contains("moins de")) {
                    String condition = "moins de";
                    int startIndex = promotion.getDescription().indexOf(condition) + condition.length();
                    StringBuilder ageString = new StringBuilder();
                    for (int i = startIndex; i < promotion.getDescription().length(); i++) {
                        char c = promotion.getDescription().charAt(i);
                        if (Character.isDigit(c)) {
                            ageString.append(c);
                        } else if (ageString.length() > 0) {
                            break;
                        }
                    }
                    int age = Integer.parseInt(ageString.toString());
                    int ageClient = calculateAgeClient();
                    if (ageClient < age) {
                        cond = true;
                    }
                }
                if (promotion.getDescription().contains("plus de")) {
                    String condition = "plus de";
                    int startIndex = promotion.getDescription().indexOf(condition) + condition.length();
                    StringBuilder ageString = new StringBuilder();
                    for (int i = startIndex; i < promotion.getDescription().length(); i++) {
                        char c = promotion.getDescription().charAt(i);
                        if (Character.isDigit(c)) {
                            ageString.append(c);
                        } else if (ageString.length() > 0) {
                            break;
                        }
                    }
                    int age = Integer.parseInt(ageString.toString());
                    int ageClient = calculateAgeClient();
                    if (ageClient > age) {
                        cond = true;
                    }
                }
                if (!cond) {
                    promotionsToRemove.add(promotion);
                }
            }
        }
        applicablePromotions.removeAll(promotionsToRemove);
    }

    private int calculateAgeClient() {
        LocalDate today = LocalDate.now();
        return today.getYear() - birthdate.getYear() - (today.getDayOfYear() < birthdate.getDayOfYear() ? 1 : 0);
    }

}
