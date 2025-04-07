package Vue;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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

public class ButtonNavigation {
    private Button button;
    private StackPane root;

    public ButtonNavigation(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        try {
            Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.button.setStyle("-fx-background-color: transparent; -fx-min-width: 175px; -fx-min-height: 75px; -fx-border-radius: 20; -fx-background-radius: 0; -fx-font-family: 'Bungee' ;-fx-text-fill: #051039; -fx-font-size: 24px; font-weight: bold;"); /// Propriétés CSS du bouton

        Rectangle mouseEffect = new Rectangle();
        mouseEffect.setFill(Color.web("#cdd5e4"));
        mouseEffect.setArcHeight(20);
        mouseEffect.setArcWidth(20);
        mouseEffect.setOpacity(0.6);
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        Rectangle buttonBackground = new Rectangle(175, 75);
        buttonBackground.setArcWidth(20);
        buttonBackground.setArcHeight(20);
        buttonBackground.setFill(Color.WHITE);

        root.getChildren().add(buttonBackground);
        root.getChildren().add(mouseEffect);
        root.getChildren().add(this.button);

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setHeight(75);
                mouseEffect.setWidth(175);
                mouseEffect.setVisible(true);
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(false);
            }
        });
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.button.setOnAction(handler);
    }

    public StackPane getRoot() {
        return this.root;
    }

    public void setDisable(boolean b) {
        this.button.setDisable(b);
    }
}
