package Controller;

import DAO.ScheduleDAO;
import Modele.Attraction;
import Modele.Schedule;
import Vue.Calendar.ButtonNavigation;
import Vue.Calendar.ReservationWindow;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class DayWindowController {
    @FXML private GridPane headerGridPane;
    @FXML private BorderPane rootPane;
    @FXML private Label titleLabel;
    @FXML private GridPane gridPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Label noDataLabel;
    @FXML private ImageView img;
    @FXML private HBox titleContainer;

    private final ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
    private LocalDate date;
    private Attraction attraction;

    @FXML private void initialize() {
        Image image = new Image(getClass().getResource("/imgs/dayWindow_background.png").toExternalForm());
        img.setImage(image);

        titleContainer.setTranslateX(-200);  // Position horizontale
        titleContainer.setTranslateY(-469);  // Position verticale
    }

    public void setDate(LocalDate date, Attraction attraction) {
        this.date = date;
        this.attraction = attraction;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.titleLabel.setText(" - " + date.format(formatter));
        chargeDatas();
    }

    private void createCell(String text, int colIndex, int rowIndex) {
        Label label = new Label(text);
        label.getStyleClass().add("grid-cell-text");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        StackPane cell = new StackPane(label);
        cell.getStyleClass().add("grid-cell");
        this.gridPane.add(cell, colIndex, rowIndex);
    }

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

}
