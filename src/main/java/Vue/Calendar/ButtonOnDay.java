package Vue.Calendar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * ButtonOnDay is a custom button class that extends ButtonFreeCalendar.
 * It represents a button that is styled to indicate a specific day in a calendar.
 */
public class ButtonOnDay extends ButtonFreeCalendar {
    public ButtonOnDay(String value) {
        super(value);
        this.buttonBackground.getStyleClass().add("button-on-day-background");
    }

    public StackPane getRoot() {
        return this.root;
    }
}
