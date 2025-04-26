package Controller;

import DAO.ScheduleDAO;
import Modele.Attraction;
import Modele.Schedule;
import Modele.Session;
import Vue.Calendar.ButtonNavigation;
import Vue.Calendar.ReservationWindow;
import Vue.MainApp;
import Vue.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Controller de la fenêtre de sélection de la date.
 * Permet d'afficher les horaires disponibles pour une attraction à une date donnée.
 */
public class DayWindowController {
    @FXML private GridPane headerGridPane;
    @FXML private BorderPane rootPane;
    @FXML private Label titleLabel;
    @FXML private GridPane gridPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Label noDataLabel;
    @FXML private ImageView img;
    @FXML private HBox titleContainer;
    @FXML private ImageView quitButton;
    @FXML private ImageView backButton;

    private final ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
    private LocalDate date;
    private Attraction attraction;

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
        titleContainer.setTranslateY(-469);
        titleContainer.setTranslateX(-200);
    }

    /**
     * Initialise la fenêtre de sélection de la date.
     * @param date Date sélectionnée
     * @param attraction Attraction associée
     */
    public void setDate(LocalDate date, Attraction attraction) {
        this.date = date;
        this.attraction = attraction;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.titleLabel.setText(" - " + date.format(formatter));
        chargeDatas();
    }

    /**
     * Crée une cellule dans la grille avec le texte spécifié.
     * @param text Le texte à afficher dans la cellule
     * @param colIndex L'index de la colonne
     * @param rowIndex L'index de la ligne
     */
    private void createCell(String text, int colIndex, int rowIndex) {
        Label label = new Label(text);
        label.getStyleClass().add("grid-cell-text");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        StackPane cell = new StackPane(label);
        cell.getStyleClass().add("grid-cell");
        this.gridPane.add(cell, colIndex, rowIndex);
    }

    /**
     * Charge les données de la grille en fonction de la date et de l'attraction sélectionnées.
     */
    private void chargeDatas() {
        ScheduleDAO dao = new ScheduleDAO();
        ArrayList<Schedule> listSchedule = dao.getScheduleWithAttractionNamesByDate(this.date, this.attraction);

        if (listSchedule != null && !listSchedule.isEmpty()) {
            this.noDataLabel.setVisible(false);
            this.scrollPane.setVisible(true);
            this.scheduleData.clear();
            this.scheduleData.addAll(listSchedule);

            for (int i = 0; i < this.scheduleData.size(); i++) {
                Schedule schedule = this.scheduleData.get(i);
                int rowIndex = i + 1;

                ButtonNavigation button = schedule.getBtnNav();
                button.setOnAction(e -> resButtonOnClick(schedule, attraction));
                if (!Objects.equals(schedule.getStatut(), "Ouvert")) {
                    button.setDisable(true);
                    schedule.setPlacesDispos(0);
                }

                createCell(schedule.getNameAttraction(), 0, rowIndex);
                createCell(schedule.getHourDebut().toString(), 1, rowIndex);
                createCell(schedule.getHourEnd().toString(), 2, rowIndex);
                createCell(Integer.toString(schedule.getPDispos()), 3, rowIndex);
                createCell(schedule.getStatut(), 4, rowIndex);

                this.gridPane.add(button.getRoot(), 5, rowIndex);
                GridPane.setHalignment(button.getRoot(), javafx.geometry.HPos.CENTER);
            }
        } else {
            this.noDataLabel.setVisible(true);
            this.scrollPane.setVisible(false);
        }
    }

    /**
     * Gère le clic sur le bouton de réservation.
     * @param schedule Horaire sélectionné
     * @param attraction Attraction associée
     */
    private void resButtonOnClick(Schedule schedule, Attraction attraction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/reservationWindow-view.fxml"));
            Parent reservationView = loader.load();

            ReservationWindowController controller = loader.getController();
            controller.setSchedule(schedule, attraction);

            StackPane rootPane = (StackPane) this.rootPane.getScene().getRoot();
            reservationView.translateXProperty().set(rootPane.getWidth());
            rootPane.getChildren().add(reservationView);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(1500), this.rootPane);
            slideOut.setToX(-1920); // Slide vers la gauche
            slideOut.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Custom curve

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), reservationView);
            slideIn.setToX(0);
            slideIn.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Custom curve

            // Suppression de l'ancienne vue après la transition
            slideIn.setOnFinished(e -> rootPane.getChildren().remove(this.rootPane));

            // Lancement des animations
            slideOut.play();
            slideIn.play();

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue de réservation : " + exception.getMessage());
        }
    }

    /**
     * Gère le clic sur le bouton de retour.
     * Retourne à la vue du calendrier.
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
     * Gère le clic sur le bouton de retour.
     * Retourne à la vue du calendrier.
     */
    @FXML
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/calendar-view.fxml"));
            Parent calendarView = loader.load();

            CalendarController controller = loader.getController();
            controller.initialize(this.attraction);

            Transition.slideTransition(MainApp.rootPane, calendarView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }

}
