package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchView extends Application {

    public void showSearchArea(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/search-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Rechercher");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la barre de recherche : " + e.getMessage());
        }
    }

    @Override
    public void start(Stage stage) {
        showSearchArea(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}