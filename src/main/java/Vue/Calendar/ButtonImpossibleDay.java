package Vue.Calendar;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ButtonImpossibleDay extends ButtonFreeCalendar {
    public ButtonImpossibleDay(String value) {
        super(value);
        this.root = new StackPane();
        this.button = new Button(value);

        try {
            Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.button.setStyle("-fx-background-color: transparent; -fx-min-width: 80px; -fx-min-height: 80px;-fx-background-radius: 0; -fx-font-family: 'Bungee' ;-fx-text-fill: #a6a8af; -fx-font-size: 24px; font-weight: bold;"); /// Propriétés CSS du bouton

        this.button.setOnAction(null);

        Circle buttonBackground = new Circle(40);
        buttonBackground.setFill(Color.WHITE);

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(this.button);
    }

    public StackPane getRoot() {
        return this.root;
    }
}
