package Vue.Calendar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class ButtonOnDay extends ButtonFreeCalendar {
    public ButtonOnDay(String value) {
        super(value);
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-on-day");

        this.mouseEffect = new Rectangle(80, 80);
        this.mouseEffect.getStyleClass().add("button-mouse-effect");
        this.mouseEffect.setVisible(false);
        this.mouseEffect.setMouseTransparent(true);

        this.buttonBackground = new Rectangle(80, 80);
        this.buttonBackground.getStyleClass().add("button-on-day-background");

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(button);

        this.root.getStyleClass().add("Vue/css/calendar.css");

        preparedAnimations();

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(true);

                animationOut.stop();
                animationIn.playFromStart();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(false);

                animationIn.stop();
                animationOut.playFromStart();
            }
        });
    }



    public StackPane getRoot() {
        return this.root;
    }
}
