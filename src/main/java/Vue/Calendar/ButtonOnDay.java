package Vue.Calendar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class ButtonOnDay extends ButtonFreeCalendar {
    public ButtonOnDay(String value) {
        super(value);
        this.buttonBackground.getStyleClass().add("button-on-day-background");
    }



    public StackPane getRoot() {
        return this.root;
    }
}
