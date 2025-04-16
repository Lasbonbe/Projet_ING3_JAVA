package Vue.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ButtonFreeCalendar {
    protected Button button;
    protected StackPane root;

    public ButtonFreeCalendar(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-free-calendar");

        Circle mouseEffect = new Circle(0, 0, 0);
        mouseEffect.getStyleClass().add("button-mouse-effect");
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        Circle buttonBackground = new Circle(40);
        buttonBackground.getStyleClass().add("button-background");

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(this.button);
        this.root.getStyleClass().add("calendar.css");

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
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.button.setOnAction(handler);
    }

    public StackPane getRoot() {
        return this.root;
    }
}
