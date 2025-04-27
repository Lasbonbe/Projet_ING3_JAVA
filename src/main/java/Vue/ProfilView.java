package Vue;

import Controller.ProfilController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
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

}