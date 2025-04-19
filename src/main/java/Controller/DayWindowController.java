package Controller;

import DAO.ScheduleDAO;
import Modele.Schedule;
import Vue.Calendar.ButtonNavigation;
import Vue.Calendar.ReservationWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayWindowController {
    @FXML private GridPane headerGridPane;
    @FXML private BorderPane rootPane;
    @FXML private Label titleLabel;
    @FXML private GridPane gridPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Label noDataLabel;

    private final ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
    private LocalDate date;

    public void setDate(LocalDate date) {
        this.date = date;
        this.titleLabel.setText("Horaires pour le " + this.date);
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
        ArrayList<Schedule> listSchedule = dao.getScheduleWithAttractionNamesByDate(this.date);

        if (listSchedule != null && !listSchedule.isEmpty()) {
            this.noDataLabel.setVisible(false);
            this.scrollPane.setVisible(true);
            this.scheduleData.clear();
            this.scheduleData.addAll(listSchedule);

            for (int i = 0; i < this.scheduleData.size(); i++) {
                Schedule schedule = this.scheduleData.get(i);
                int rowIndex = i + 1;

                ButtonNavigation button = schedule.getBtnNav();
                button.setOnAction(e -> resButtonOnClick(schedule));

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

    private void resButtonOnClick(Schedule schedule) {
        ReservationWindow resWindow = new ReservationWindow(schedule);
        resWindow.show();
    }

}
