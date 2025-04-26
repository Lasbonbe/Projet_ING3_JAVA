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

/**
 * ButtonFreeCalendar is a custom button class that provides a visual effect
 * when the mouse hovers over it. It uses JavaFX's Timeline for animations.
 */
public class ButtonFreeCalendar {
    protected Button button;
    protected StackPane root;
    protected Rectangle mouseEffect;
    protected Rectangle buttonBackground;
    protected Timeline animationIn;
    protected Timeline animationOut;

    /**
     * Returns the mouse effect rectangle associated with this ButtonFreeCalendar instance.
     *
     * @return the mouse effect rectangle
     */
    public Rectangle getButtonBackground() {
        return buttonBackground;
    }

    /**
     * Returns the button associated with this ButtonFreeCalendar instance.
     *
     * @return the button
     */
    public Button getButton() {
        return button;
    }

    /**
     * Constructeur pour initialiser le bouton avec un texte donné.
     *
     * @param value Le texte à afficher sur le bouton.
     */
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

        setupEventHandlers();
    }

    /**
     * Méthode pour gérer l'événement de survol de la souris sur le bouton.
     */
    protected void handleMouseEntered(MouseEvent mouseEvent) {
        mouseEffect.setVisible(true);

        animationOut.stop();
        animationIn.playFromStart();
    }

    /**
     * Méthode pour gérer l'événement de sortie de la souris du bouton.
     */
    protected void handleMouseExited(MouseEvent mouseEvent) {
        mouseEffect.setVisible(false);

        animationIn.stop();
        animationOut.playFromStart();
    }

    /**
     * Méthode pour configurer les gestionnaires d'événements pour le bouton.
     */
    protected void setupEventHandlers() {
        this.button.setOnMouseEntered(this::handleMouseEntered);
        this.button.setOnMouseExited(this::handleMouseExited);
    }

    /**
     * Méthode pour configurer les animations d'entrée et de sortie du bouton.
     */
    public void preparedAnimations() {
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

    /**
     * Méthode pour set le gestionnaire d'événements d'action du bouton.
     *
     * @param handler Le gestionnaire d'événements à associer au bouton.
     */
    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.button.setOnAction(handler);
    }

    /**
     * Méthode pour obtenir le StackPane racine du bouton.
     *
     * @return Le StackPane racine du bouton.
     */
    public StackPane getRoot() {
        return this.root;
    }
}
