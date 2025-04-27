package Controller;

import DAO.PromotionDAO;
import Modele.Attraction;
import Modele.Client;
import Modele.Session;
import Modele.User;
import Vue.Calendar.*;
import Vue.MainApp;
import Vue.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Controller de la page de calendrier.
 * Permet d'afficher le calendrier et de sélectionner une date.
 */
public class CalendarController {
    @FXML public ImageView img;
    //@FXML private Button prevButton;
    //@FXML private Button nextButton;
    @FXML private Label monthLabel;
    @FXML private GridPane calendarGrid;
    @FXML private HBox navigationHBox;
    @FXML private ImageView quitButton;
    @FXML private ImageView backButton;

    private final String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
    private YearMonth currentYearMonth;
    private final LocalDate today = LocalDate.now();
    private ButtonNavigation prevButton;
    private ButtonNavigation nextButton;
    private PromotionDAO promotionDAO = new PromotionDAO();
    private Attraction attraction;
    private Client client;

    /**
     * Initialisation de la page de calendrier.
     * @param attraction Attraction associée au calendrier.
     */
    @FXML public void initialize(Attraction attraction, Client client) {
        this.client = client;
        this.attraction = attraction;

        try {
            img.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/CALENDAR.png")).toExternalForm()
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

        prevButton = new ButtonNavigation("Précédent", 175, 75);
        nextButton = new ButtonNavigation("Suivant", 175, 75);

        navigationHBox.getChildren().add(prevButton.getRoot());
        navigationHBox.getChildren().add(nextButton.getRoot());

        prevButton.setOnAction(e -> onPrevButtonClick());
        nextButton.setOnAction(e -> onNextButtonClick());

        currentYearMonth = YearMonth.now();
        updateCalendar();
    }

    /**
     * BOUTON PREVIOUS
     * Appelé lorsque le bouton "Précédent" est cliqué.
        * Vérifie si le mois précédent est avant le mois actuel
     * et met à jour le calendrier.
     */
    private void onPrevButtonClick() {
        YearMonth previous = currentYearMonth.minusMonths(1);
        if (!previous.isBefore(YearMonth.from(this.today))) {
            currentYearMonth = previous;
            updateCalendar();
        }
    }

    /**
     * BOUTON NEXT
     * Appelé lorsque le bouton "Suivant" est cliqué.
     * Met à jour le mois actuel et le calendrier.
     */
    private void onNextButtonClick() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }


    /**
     * Met à jour le calendrier en fonction du mois actuel.
     * Affiche les jours de la semaine et les jours du mois.
     * @hidden WARNING 160% COMPLEXITY
     */
    public void updateCalendar() {
        this.calendarGrid.getChildren().clear();
        this.monthLabel.setText(this.currentYearMonth.getMonth() + " " + this.currentYearMonth.getYear());
        GridPane.setHalignment(this.monthLabel, HPos.CENTER);
        GridPane.setValignment(this.monthLabel, VPos.CENTER);

        if (this.currentYearMonth.minusMonths(1).isBefore(YearMonth.from(this.today))) {
            this.prevButton.setDisable(true);
        } else {
            this.prevButton.setDisable(false);
        }

        for (int i = 0; i < 7; i++) {
            Label label = new Label(this.days[i]);
            label.getStyleClass().add("day-label");
            this.calendarGrid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        LocalDate firstOfMonth = this.currentYearMonth.atDay(1);
        int startDay = firstOfMonth.getDayOfWeek().getValue() - 1;
        int day = 1;
        int row = 1;
        int col = startDay;
        while (day <= this.currentYearMonth.lengthOfMonth()) {
            ButtonFreeCalendar dayButton;
            int cday = day;
            LocalDate currentDate = this.currentYearMonth.atDay(day);
            if (this.currentYearMonth.equals(YearMonth.from(this.today)) && day < this.today.getDayOfMonth()) {
                dayButton = new ButtonImpossibleDay(String.valueOf(day));
            } else {
                boolean hasPromo = false;
                if (attraction != null) {
                    hasPromo = promotionDAO.hasPromotion(attraction, currentDate);
                }
                if (hasPromo) {
                    dayButton = new ButtonPromotionDay(String.valueOf(day));
                    dayButton.preparedAnimations();
                    if (this.currentYearMonth.equals(YearMonth.from(this.today)) && day == this.today.getDayOfMonth()) {
                        dayButton.getButtonBackground().getStyleClass().remove("button-promotion-day-background");
                        dayButton.getButtonBackground().getStyleClass().add("button-promotion-day-background-onDay");
                    }
                } else if (this.currentYearMonth.equals(YearMonth.from(this.today)) && day == this.today.getDayOfMonth()) {
                    dayButton = new ButtonOnDay(String.valueOf(day));
                    dayButton.preparedAnimations();
                } else {
                    dayButton = new ButtonFreeCalendar(String.valueOf(day));
                    dayButton.preparedAnimations();
                }

                dayButton.setOnAction(e -> onDayButtonClick(cday));
            }
            this.calendarGrid.add(dayButton.getRoot(), col, row);
            day++;
            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

    }

    /**
     * Appelé lorsque le bouton d'un jour est cliqué.
     * Ouvre la fenêtre du jour correspondant.
     * @param cday Le jour du mois sélectionné.
     */
    private void onDayButtonClick(int cday) {
        LocalDate selectedDate = this.currentYearMonth.atDay(cday);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/daywindow-view.fxml"));
            Parent dayWindowView = loader.load();

            DayWindowController controller = loader.getController();
            controller.setDate(selectedDate, attraction, client);

            Transition.slideTransition(MainApp.rootPane, dayWindowView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue du jour : " + e.getMessage());
        }
    }

    /**
     * Appelé lorsque le bouton "Quitter" est cliqué.
     * Réinitialise la session utilisateur et charge la vue de connexion.
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
     * Appelé lorsque le bouton "Retour" est cliqué.
     * Charge la vue de code.
     */
    @FXML
    private void backClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/home-view.fxml"));
            Parent codeView = loader.load();

            Transition.slideTransition(MainApp.rootPane, codeView, 1000, "RIGHT");

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

}