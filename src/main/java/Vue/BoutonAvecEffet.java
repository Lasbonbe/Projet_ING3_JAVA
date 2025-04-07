package Vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class BoutonAvecEffet extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création du conteneur principal
        StackPane buttonContainer = new StackPane();

        // Création du bouton carré avec un nombre bleu (28)
        Button button = new Button("28");
        button.setStyle("-fx-background-color: white; -fx-min-width: 80px; -fx-min-height: 80px; " +
                "-fx-background-radius: 0; -fx-text-fill: #0000FF; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Création d'un cercle bleu plus petit que le bouton
        Circle hoverEffect = new Circle(0, 0, 0);
        hoverEffect.setFill(Color.BLUE);
        hoverEffect.setOpacity(0.3);
        hoverEffect.setVisible(false);
        hoverEffect.setMouseTransparent(true); // Rend le cercle "traversable" par les événements de souris

        // Ajout du bouton et du cercle dans le même conteneur
        buttonContainer.getChildren().addAll(button, hoverEffect);

        // Animation de clignotement pour le cercle
        FadeTransition blink = new FadeTransition(Duration.millis(500), hoverEffect);
        blink.setFromValue(0.1);
        blink.setToValue(0.4);
        blink.setCycleCount(Timeline.INDEFINITE);
        blink.setAutoReverse(true);

        // Effet de survol de la souris
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Définir la taille du cercle à 60% de la taille du bouton
                hoverEffect.setRadius(Math.min(button.getWidth(), button.getHeight()) * 0.3);
                hoverEffect.setVisible(true);

                // Le texte reste toujours bleu, pas de changement de couleur

                // Démarrer l'animation de clignotement
                blink.play();
            }
        });

        // Effet lorsque la souris quitte le bouton
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Arrêter l'animation de clignotement
                blink.stop();

                // Masquer le cercle
                hoverEffect.setVisible(false);
            }
        });

        // Action de clic sur le bouton
        button.setOnAction(event -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Réservation effectuée");
            alert.show();
        });

        // Configuration de la scène
        Scene scene = new Scene(buttonContainer, 300, 200);
        scene.setFill(Color.LIGHTGRAY);

        // Configuration de la fenêtre
        primaryStage.setTitle("Bouton Carré avec Effet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}