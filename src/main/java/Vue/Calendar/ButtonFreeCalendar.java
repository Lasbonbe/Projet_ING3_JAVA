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

public class ButtonFreeCalendar {
    protected Button button;
    protected StackPane root;
    protected Rectangle mouseEffect;
    protected Rectangle buttonBackground;
    protected Timeline animationIn;
    protected Timeline animationOut;

    public ButtonFreeCalendar(String value) {
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-free-calendar");

        this.mouseEffect = new Rectangle(80, 80);
        this.mouseEffect.getStyleClass().add("button-mouse-effect");
        this.mouseEffect.setVisible(false);
        this.mouseEffect.setMouseTransparent(true);

        this.buttonBackground = new Rectangle(80, 80);
        this.buttonBackground.getStyleClass().add("button-background");

        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(mouseEffect);
        this.root.getChildren().add(this.button);
        this.root.getStyleClass().add("Vue/css/calendar.css");

        preparedAnimations();

        this.button.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(true);

                animationOut.stop();
                animationIn.playFromStart();
            }
        });

        this.button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEffect.setVisible(false);

                animationIn.stop();
                animationOut.playFromStart();
            }
        });
    }

    protected void preparedAnimations() {
        this.animationIn = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 0),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 0),
                        new KeyValue(this.mouseEffect.arcWidthProperty(), 0),
                        new KeyValue(this.mouseEffect.arcHeightProperty(), 0)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 80),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 80),
                        new KeyValue(this.mouseEffect.arcWidthProperty(), 80),
                        new KeyValue(this.mouseEffect.arcHeightProperty(), 80)
                )
        );

        this.animationOut = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(this.buttonBackground.arcWidthProperty(), 80),
                        new KeyValue(this.buttonBackground.arcHeightProperty(), 80),
                        new KeyValue(this.mouseEffect.arcWidthProperty(), 80),
                        new KeyValue(this.mouseEffect.arcHeightProperty(), 80)
                ),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(this.buttonBackground.arcWidthProperty(),0),
                        new KeyValue(this.buttonBackground.arcHeightProperty(),0),
                        new KeyValue(this.mouseEffect.arcWidthProperty(), 0),
                        new KeyValue(this.mouseEffect.arcHeightProperty(), 0)
                )
        );
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.button.setOnAction(handler);
    }

    public StackPane getRoot() {
        return this.root;
    }
}
