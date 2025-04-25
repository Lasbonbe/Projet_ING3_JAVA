package Vue;

import Controller.CalendarController;
import Controller.InformationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InformationView extends Application {
    private static StackPane rootPane;

    public void showInformation(Stage stage) {
        try {
            rootPane = new StackPane();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/information-view.fxml"));
            Parent root = loader.load();

            InformationController controller = loader.getController();

            Scene scene = new Scene(root);

            stage.setTitle("Informations de l'attraction");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la page d'information : " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        showInformation(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}