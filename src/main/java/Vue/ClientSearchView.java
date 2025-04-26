package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe représentant la vue de recherche de client.
 * Elle permet d'afficher une interface de recherche pour les clients.
 */
public class    ClientSearchView extends Application {

    /**
     * Méthode pour afficher la barre de recherche.
     * @param stage La scène à afficher.
     */
    public void showSearchArea(Stage stage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/client-search-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            stage.setTitle("Rechercher un client");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la barre de recherche : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher la vue de recherche de client.
     * @param stage La scène à afficher.
     */
    @Override
    public void start(Stage stage) {
        showSearchArea(stage);
    }

    /**
     * Méthode principale pour lancer l'application.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}