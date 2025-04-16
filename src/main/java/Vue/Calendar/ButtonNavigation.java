package Vue.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ButtonNavigation {
    private Button button;
    private StackPane root;

    public ButtonNavigation(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-navigation");

        Rectangle mouseEffect = new Rectangle();
        mouseEffect.getStyleClass().add("button-navigation-mouse-effect");
        mouseEffect.setVisible(false);
        mouseEffect.setMouseTransparent(true);

        Rectangle buttonBackground = new Rectangle(175, 75);
        buttonBackground.getStyleClass().add("button-navigation-background");

        this.root.getStyleClass().add("calendar.css");
        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(this.button);

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
