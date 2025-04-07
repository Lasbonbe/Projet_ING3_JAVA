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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ButtonFreeCalendar {
    private Button button;
    private StackPane root;

    public ButtonFreeCalendar(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        try {
            Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.button.setStyle("-fx-background-color: transparent; -fx-min-width: 80px; -fx-min-height: 80px;-fx-background-radius: 0; -fx-font-family: 'Bungee' ;-fx-text-fill: #051039; -fx-font-size: 24px; font-weight: bold;"); /// Propriétés CSS du bouton

        Circle mouseEffect = new Circle(0, 0, 0);
        mouseEffect.setFill(Color.web("#cdd5e4"));
        mouseEffect.setOpacity(0.6);
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        Circle buttonBackground = new Circle(40);
        buttonBackground.setFill(Color.WHITE);

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(this.button);

        this.button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setRadius(40);
                mouseEffect.setVisible(true);
            }
        });

        this.button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(false);
            }
        });

        this.button.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Réservation effectuée");
            alert.show();
        });
    }

    public StackPane getRoot() {
        return this.root;
    }
}
