package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    public static StackPane rootPane; // accessible globalement

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charge la première vue (login)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
        Parent loginView = fxmlLoader.load();

        // Création du StackPane racine
        rootPane = new StackPane();
        rootPane.getChildren().add(loginView);

        Scene scene = new Scene(rootPane, 1280, 720);
        primaryStage.setScene(scene);

        // Icône optionnelle
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/img.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Parc Attractions - Connexion");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
