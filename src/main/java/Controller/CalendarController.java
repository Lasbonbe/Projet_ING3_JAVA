package Controller;

import Modele.Attraction;
import Vue.Calendar.*;
import Vue.MainApp;
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
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarController {
    @FXML public ImageView img;
    //@FXML private Button prevButton;
    //@FXML private Button nextButton;
    @FXML private Label monthLabel;
    @FXML private GridPane calendarGrid;
    @FXML private HBox navigationHBox;

    private final String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
    private YearMonth currentYearMonth;
    private final LocalDate today = LocalDate.now();
    private ButtonNavigation prevButton;
    private ButtonNavigation nextButton;

    @FXML public void initialize() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
        img.setImage(image);

        prevButton = new ButtonNavigation("Précédent");
        nextButton = new ButtonNavigation("Suivant");

        navigationHBox.getChildren().add(prevButton.getRoot());
        navigationHBox.getChildren().add(nextButton.getRoot());

        prevButton.setOnAction(e -> onPrevButtonClick());
        nextButton.setOnAction(e -> onNextButtonClick());

        currentYearMonth = YearMonth.now();
        updateCalendar();
    }

    private void onPrevButtonClick() {
        YearMonth previous = currentYearMonth.minusMonths(1);
        if (!previous.isBefore(YearMonth.from(this.today))) {
            currentYearMonth = previous;
            updateCalendar();
        }
    }

    private void onNextButtonClick() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }


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
            if (this.currentYearMonth.equals(YearMonth.from(this.today)) && day < this.today.getDayOfMonth()) {
                dayButton = new ButtonImpossibleDay(String.valueOf(day));
            } else {
                if(this.currentYearMonth.equals(YearMonth.from(this.today)) && day == this.today.getDayOfMonth()) {
                    dayButton = new ButtonOnDay(String.valueOf(day));
                } else {
                    dayButton = new ButtonFreeCalendar(String.valueOf(day));
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

    private void onDayButtonClick(int cday) {
        Attraction attraction = new Attraction(1, "La roue tourne va tourner (Grande roue)", 50, 3, 15); /// TEMPORAIRE A MODIFIER QUAND ON REUNIRA TOUT
        LocalDate selectedDate = this.currentYearMonth.atDay(cday);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/daywindow-view.fxml"));
            Parent dayWindowView = loader.load();

            DayWindowController controller = loader.getController();
            controller.setDate(selectedDate, attraction);

            StackPane rootPane = CalendarView.getRootPane();
            dayWindowView.translateXProperty().set(rootPane.getWidth());
            rootPane.getChildren().add(dayWindowView);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(1500), rootPane.getChildren().getFirst());
            slideOut.setToX(-1920); // Slide vers la gauche
            slideOut.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Custom curve

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), dayWindowView);
            slideIn.setToX(0);
            slideIn.setInterpolator(javafx.animation.Interpolator.SPLINE(0.7, 0.0, 0.3, 1.0)); // Custom curve

            // Suppression de l'ancienne vue après la transition
            slideIn.setOnFinished(e -> rootPane.getChildren().removeFirst());

            // Lancement des animations
            slideOut.play();
            slideIn.play();

        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue du jour : " + exception.getMessage());
        }
    }

}
