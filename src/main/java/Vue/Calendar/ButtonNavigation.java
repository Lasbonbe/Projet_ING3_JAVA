package Vue.Calendar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ButtonNavigation {
    private Button button;
    private StackPane root;
    private Timeline animationIn;
    private Timeline animationOut;
    private Rectangle buttonBackground;
    private Rectangle mouseEffect;

    public ButtonNavigation(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-navigation");

        this.mouseEffect = new Rectangle();
        this.mouseEffect.getStyleClass().add("button-navigation-mouse-effect");
        this.mouseEffect.setVisible(false);
        this.mouseEffect.setMouseTransparent(true);

        this.buttonBackground = new Rectangle(175, 75);
        this.buttonBackground.getStyleClass().add("button-navigation-background");

        preparedAnimations();

        this.root.getStyleClass().add("Vue/css/calendar.css");
        this.root.getStyleClass().add("grid-cell");
        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(this.button);

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setHeight(75);
                mouseEffect.setWidth(175);
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

    private void preparedAnimations() {
        this.animationIn = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 10),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 10)
                        ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 20),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 20)
                )
        );

        this.animationOut = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 20),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 20)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(this.buttonBackground.arcWidthProperty(),10),
                        new KeyValue(this.buttonBackground.arcHeightProperty(),10)
                        )
        );
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
