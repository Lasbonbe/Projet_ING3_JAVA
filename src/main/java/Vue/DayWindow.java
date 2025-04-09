package Vue;

import Modele.Schedule;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;

public class DayWindow {
    private LocalDate date;
    private Stage stage;
    private TableView<Schedule> tableView;

    public DayWindow(LocalDate date) {
        this.date = date;
        this.stage = new Stage();
        initialize();
        chargeDatas();
    }

    private void initialize() {
        Label title = new Label("Réservations pour le : " + this.date);
        title.setFont(Font.font("Bungee", 18));
        HBox hbox = new HBox(title);
        hbox.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(hbox);

        this.tableView = new TableView<>();
        this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Schedule, String> attractionCol = new TableColumn<>("Attraction");
        attractionCol.setCellValueFactory(new PropertyValueFactory<>("nameAttraction"));
        TableColumn<Schedule, Time> debutCol = new TableColumn<>("Heure de début");
        debutCol.setCellValueFactory(new PropertyValueFactory<>("debutHour"));
        TableColumn<Schedule, Time> endCol = new TableColumn<>("Heure de fin");
        endCol.setCellValueFactory(new PropertyValueFactory<>("endHour"));
        TableColumn<Schedule, Integer> pDisposCol = new TableColumn<>("Places disponibles");
        pDisposCol.setCellValueFactory(new PropertyValueFactory<>("pDispos"));
        TableColumn<Schedule, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Schedule, ButtonNavigation> resCol = new TableColumn<>("Réserver");
        resCol.setCellValueFactory(new PropertyValueFactory<>("res"));

        this.tableView.getColumns().addAll(attractionCol, debutCol, endCol, pDisposCol, statusCol, resCol);

        VBox vbox = new VBox(this.tableView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Details");
        stage.setScene(scene);
    }

    private void chargeDatas() {

    }

    public void show() {
        stage.show();
    }
}
