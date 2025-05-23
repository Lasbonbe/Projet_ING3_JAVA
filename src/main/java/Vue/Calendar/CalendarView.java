package Vue.Calendar;

import Controller.CalendarController;
import Modele.Attraction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarView extends Application {
    private static StackPane rootPane;

    public static StackPane getRootPane() {
        return rootPane;
    }

    public void showCalendar(Stage stage) {
        try {
            rootPane = new StackPane();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/calendar-view.fxml"));
            Parent root = loader.load();

            CalendarController controller = loader.getController();
            /// Pour des tests
            //controller.initialize(new Attraction(1, "La roue tourne va tourner","Grande Roue", 50, 3, 15, "La roue tourne hyper vite", "/imgs/Attractions/attraction_GrandeRoue.png"));
            rootPane.getChildren().add(root);

            Scene scene = new Scene(rootPane);

            stage.setTitle("Calendrier");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du calendrier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        showCalendar(stage);
    }
}
