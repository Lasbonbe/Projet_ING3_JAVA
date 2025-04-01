package Vue;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransiTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        Pane scene1 = new Pane(); // Première scène
        scene1.setStyle("-fx-background-color: lightblue;");
        scene1.setPrefSize(1920, 1080);

        Pane scene2 = new Pane(); // Seconde scène
        scene2.setStyle("-fx-background-color: lightcoral;");
        scene2.setPrefSize(1920, 1080);

        root.getChildren().addAll(scene2, scene1); // scene1 au-dessus

        Scene mainScene = new Scene(root, 1920, 1080);
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // Simuler le changement de scène après 2 secondes
        scene1.setOnMouseClicked(e -> {
            TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000), scene1);
            tt1.setToX(-1920); // Slide vers la gauche

            TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000), scene2);
            tt2.setFromX(1920); // Départ à droite
            tt2.setToX(0); // Slide vers le centre

            tt1.play();
            tt2.play();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
