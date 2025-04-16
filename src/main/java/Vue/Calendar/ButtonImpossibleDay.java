package Vue.Calendar;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ButtonImpossibleDay extends ButtonFreeCalendar {
    public ButtonImpossibleDay(String value) {
        super(value);
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-impossible-day");
        this.button.setOnAction(null);

        Rectangle buttonBackground = new Rectangle(80, 80);
        buttonBackground.getStyleClass().add("button-impossible-day-background");

        this.root.getStyleClass().add("calendar.css");
        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(this.button);
    }

    public StackPane getRoot() {
        return this.root;
    }
}
