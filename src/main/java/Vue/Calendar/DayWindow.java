package Vue.Calendar;

import DAO.ScheduleDAO;
import Modele.Schedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

public class DayWindow {
    private LocalDate date;
    private Stage stage;
    private TableView<Schedule> tableView;

    public DayWindow(LocalDate date) {
        this.date = date;
        this.stage = new Stage();
        this.stage.setMaximized(true);
        initialize();
        chargeDatas();
    }

    private void initialize() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        //System.out.println("Screen width: " + screenWidth);
        Label title = new Label("Horaires pour le : " + this.date);
        title.setFont(Font.font("Bungee", 18));
        HBox hbox = new HBox(title);
        hbox.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(hbox);

        this.tableView = new TableView<>();

        // STYLE DE LA TABLE

        this.tableView.setStyle("-fx-table-cell-border-color: WHITE; " +
                "-fx-border-color: WHITE;" +
                "-fx-font-family:'Bungee';");
        this.tableView.setFixedCellSize(100);
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Schedule, String> attractionCol = new TableColumn<>("Attraction");
        attractionCol.setCellValueFactory(new PropertyValueFactory<>("nameAttraction"));
        attractionCol.setPrefWidth(4.0/12 * screenWidth);
        TableColumn<Schedule, Time> debutCol = new TableColumn<>("Début");
        debutCol.setCellValueFactory(new PropertyValueFactory<>("HourDebut"));
        debutCol.setPrefWidth(1.0/12 * screenWidth);
        TableColumn<Schedule, Time> endCol = new TableColumn<>("Fin");
        endCol.setCellValueFactory(new PropertyValueFactory<>("HourEnd"));
        endCol.setPrefWidth(1.0/12 * screenWidth);
        TableColumn<Schedule, Integer> pDisposCol = new TableColumn<>("Places disponibles");
        pDisposCol.setCellValueFactory(new PropertyValueFactory<>("PDispos"));
        pDisposCol.setPrefWidth(2.4/12 * screenWidth);
        TableColumn<Schedule, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Statut"));
        statusCol.setPrefWidth(1.6/12 * screenWidth);
        TableColumn<Schedule, ButtonNavigation> resCol = new TableColumn<>("Réserver");
        resCol.setCellValueFactory(new PropertyValueFactory<>("BtnNav"));
        resCol.setPrefWidth(2.0/12 * screenWidth);

        this.tableView.getColumns().addAll(attractionCol, debutCol, endCol, pDisposCol, statusCol, resCol);

        for (TableColumn<Schedule, ?> col : this.tableView.getColumns()) {
            col.setStyle("-fx-alignment: CENTER;" +
                    "-fx-font-size: 18px;" +
                    "-fx-background-color: transparent;");
            col.setResizable(false);
            col.setReorderable(false);
        }

        this.tableView.setRowFactory(row -> new TableRow<Schedule>() {
            @Override
            protected void updateItem(Schedule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setStyle("-fx-background-color: WHITE;");
                } else {
                    setStyle("");
                }
            }
        });

        resCol.setCellFactory(col -> new TableCell<Schedule, ButtonNavigation>() {
            @Override
            protected void updateItem(ButtonNavigation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item.getRoot());
                    setAlignment(Pos.CENTER);
                }
            }
        });

        VBox vbox = new VBox(this.tableView);
        VBox.setVgrow(this.tableView, javafx.scene.layout.Priority.ALWAYS);
        vbox.setStyle("-fx-background-color: transparent;");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        root.setCenter(vbox);

        Scene scene = new Scene(root);
        stage.setTitle("Details");
        stage.setScene(scene);
    }

    private void chargeDatas() {
        ObservableList<Schedule> data = FXCollections.observableArrayList();

        ScheduleDAO dao = new ScheduleDAO();
        ArrayList<Schedule> listSchedule = dao.getScheduleWithAttractionNamesByDate(this.date);

        if (listSchedule != null && !listSchedule.isEmpty()) {
            for (Schedule schedule : listSchedule) {
                ButtonNavigation button = schedule.getBtnNav();
                button.setOnAction(e -> {
                    ReservationWindow resWindow = new ReservationWindow(schedule);
                    resWindow.show();
                });
            }
            data.addAll(listSchedule);
        }

        if (data.isEmpty()) {
            Label noData = new Label("Aucune sessions disponibles");
            noData.setFont(Font.font("Bungee", 18));
            tableView.setPlaceholder(noData);
        }
        tableView.setItems(data);
    }

    public void show() {
        stage.show();
    }
}
