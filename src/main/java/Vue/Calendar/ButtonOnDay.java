package Vue.Calendar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ButtonOnDay extends ButtonFreeCalendar {
    public ButtonOnDay(String value) {
        super(value);
        this.root = new StackPane();
        this.button = new Button(value);

        try {
            Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setStyle("-fx-background-color: transparent; -fx-min-width: 80px; -fx-min-height: 80px;-fx-background-radius: 0; -fx-font-family: 'Bungee' ;-fx-text-fill: #051039; -fx-font-size: 24px; font-weight: bold;"); /// Propriétés CSS du bouton

        Circle mouseEffect = new Circle(0, 0, 0);
        mouseEffect.setFill(Color.web("#ebf1fa"));
        mouseEffect.setOpacity(0.6);
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        Circle buttonBackground = new Circle(40);
        buttonBackground.setFill(Color.web("#cdd5e4"));

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(button);

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setRadius(40);
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



    public StackPane getRoot() {
        return this.root;
    }
}
