package Vue.Calendar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class ButtonPromotionDay extends ButtonFreeCalendar {
    public ButtonPromotionDay(String value) {
        super(value);
        this.button.getStyleClass().add("button-promotion-day");
    }
}
