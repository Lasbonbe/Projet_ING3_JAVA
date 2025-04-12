package Vue.Calendar;

import Modele.Schedule;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.text.Font;

public class ReservationWindow {
    private Schedule schedule;
    private Stage stage;

    public ReservationWindow(Schedule schedule) {
        this.schedule = schedule;
        this.stage = new Stage();
        this.stage.setTitle("Reservation");
        this.stage.setMaximized(true);
        initialize();
    }

    public void initialize() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        Label title = new Label("Réservation"); /// peut etre à compléter avec la plage horaire de la session
        title.setFont(Font.font("Bungee", 18));
        HBox hbox = new HBox(title);
        hbox.setAlignment(Pos.CENTER);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        Scene scene = new Scene(borderPane);
        this.stage.setTitle("Réservation");
        this.stage.setScene(scene);

    }

    public void show() {
        this.stage.show();
    }


}
