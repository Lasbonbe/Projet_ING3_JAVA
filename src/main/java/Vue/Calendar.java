package Vue;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

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
        try {
            Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.currentYearMonth = YearMonth.now();

        HBox hBox = getHBox();
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox calendarBox = new HBox(this.calendarGrid);
        calendarBox.setAlignment(Pos.CENTER);
        calendarBox.setPadding(new Insets(30, 0, 0, 0)); /// Temporaire je pense
        borderPane.setCenter(calendarBox);
        updateCalendar();
        Scene scene = new Scene(borderPane, 800, 600, Color.WHITE);
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
        Rectangle monthBackground = new Rectangle(175, 75);
        monthBackground.setArcHeight(20);
        monthBackground.setArcWidth(20);
        monthBackground.setFill(Color.WHITE);
        this.monthLabel.setFont(Font.font("Bungee", 24));
        monthZone.getChildren().addAll(monthBackground, this.monthLabel);


        HBox hBox = new HBox(10, prevButton.getRoot(), monthZone, nextButton.getRoot());
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private void updateCalendar() {
        this.calendarGrid.getChildren().clear();
        this.monthLabel.setText(this.currentYearMonth.getMonth() + " " + this.currentYearMonth.getYear());
        GridPane.setHalignment(this.monthLabel, HPos.CENTER);
        GridPane.setValignment(this.monthLabel, VPos.CENTER);

        if (this.currentYearMonth.minusMonths(1).isBefore(YearMonth.from(this.today))) {
            this.prevButton.setDisable(true);
            this.prevButton.getRoot().setStyle("-fx-text-fill: #a6a8af;");
        } else {
            this.prevButton.setDisable(false);
        }

        for (int i = 0; i < 7; i++) {
            Label label = new Label(this.days[i]);
            label.setFont(Font.font("Bungee", 24));
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
