package Vue.Calendar;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar extends Application {
    private final String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
    private YearMonth currentYearMonth;
    private final LocalDate today = LocalDate.now();
    private final GridPane calendarGrid = new GridPane();
    private final Label monthLabel = new Label();
    private ButtonNavigation prevButton;
    private ButtonNavigation nextButton;

    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);

        this.currentYearMonth = YearMonth.now();

        HBox hBox = getHBox();
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        borderPane.setTop(hBox);

        this.calendarGrid.getStyleClass().add("calendar-grid");

        HBox calendarBox = new HBox(this.calendarGrid);
        calendarBox.getStyleClass().add("calendar-box");

        borderPane.setCenter(calendarBox);
        updateCalendar();

        Scene scene = new Scene(borderPane);

        scene.getStylesheets().add("calendar.css");
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.show();
    }

    private HBox getHBox() {
        this.prevButton = new ButtonNavigation("Précédent");
        this.nextButton = new ButtonNavigation("Suivant");

        prevButton.setOnAction(e -> {
            YearMonth previous = this.currentYearMonth.minusMonths(1);
            if (!previous.isBefore(YearMonth.from(this.today))) {
                this.currentYearMonth = previous;
                updateCalendar();
            }
        });

        nextButton.setOnAction(e -> {
            this.currentYearMonth = this.currentYearMonth.plusMonths(1);
            updateCalendar();
        });

        StackPane monthZone = new StackPane();
        monthZone.getStyleClass().add("month-zone");

        Rectangle monthBackground = new Rectangle(175, 75);
        monthBackground.getStyleClass().add("month-background");

        this.monthLabel.getStyleClass().add("month-label");
        monthZone.getChildren().addAll(monthBackground, this.monthLabel);


        HBox hBox = new HBox(10, prevButton.getRoot(), monthZone, nextButton.getRoot());
        hBox.getStyleClass().add("navigation-hbox");
        return hBox;
    }

    private void updateCalendar() {
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
                if(day == this.today.getDayOfMonth()) {
                    dayButton = new ButtonOnDay(String.valueOf(day));
                } else {
                    dayButton = new ButtonFreeCalendar(String.valueOf(day));
                }

                dayButton.setOnAction(e -> {
                    LocalDate selectedDate = this.currentYearMonth.atDay(cday);
                    DayWindow dayWindow = new DayWindow(selectedDate);
                    dayWindow.show();
                });
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

    public static void main(String[] args) {
        launch(args);
    }
}
