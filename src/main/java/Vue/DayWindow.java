package Vue;

import Modele.Schedule;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Details");
        stage.setScene(scene);
    }

    private void chargeDatas() {
    }

    public void show() {
        stage.show();
    }
}
