package Vue;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BoutonCalendar {
    Button button;
    Scene scene;

    public BoutonCalendar(String value) {
        StackPane root = new StackPane();
        this.button = new Button(value);
        this.button.setStyle("-fx-background-color: white; -fx-min-width: 80px; min-height: 80px;-fx-background-radius: 0; text-fill: #0062ff; -fx-font-size: 24px; font-weight: bold;"); /// Propriétés CSS du bouton

        Circle mouseEffect = new Circle(0, 0, 0);
        mouseEffect.setFill(Color.web("#00a2ff"));
        mouseEffect.setOpacity(0.6);
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        root.getChildren().add(this.button);
        root.getChildren().add(mouseEffect);

        FadeTransition b = new FadeTransition(Duration.millis(500), mouseEffect);
        b.setFromValue(0.1);
        b.setToValue(0.4);
        b.setCycleCount(Timeline.INDEFINITE);
        b.setAutoReverse(true);

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setRadius(Math.min(button.getWidth(), button.getHeight()) * 0.3);
                mouseEffect.setVisible(true);
                b.play();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.stop();
                mouseEffect.setVisible(false);
            }
        });

        button.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Réservation effectuée");
            alert.show();
        });

        scene = new Scene(root);
        scene.setFill(Color.GREY);
    }

    public Scene getScene() {
        return scene;
    }
}
