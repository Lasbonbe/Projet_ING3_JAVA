package Vue;

import Controller.InformationController;
import Controller.ProfilController;
import Modele.Attraction;
import Modele.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.sql.Date;

public class ProfilView extends Application {
    private static StackPane rootPane;

    public static StackPane getRootPane() {
        return rootPane;
    }

    public void showProfil(Stage stage) {
        try {
            rootPane = new StackPane();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/profil-view.fxml"));
            Parent root = loader.load();

            // Pour des tests
            ProfilController controller = loader.getController();
            Date specificDate = Date.valueOf("2001-09-11");
            controller.initialize(new Client(14, "NGNGNGNGNGNGNG", "GAGAGAGAGAGA",specificDate, "a", "a"));

            rootPane.getChildren().add(root);
            Scene scene = new Scene(rootPane);

            stage.setTitle("Votre Profil");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la page du profil : " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        showProfil(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}