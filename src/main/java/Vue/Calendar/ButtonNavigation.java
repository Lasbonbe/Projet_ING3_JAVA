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
 * ButtonNavigation est un bouton personnalisé qui change d'apparence
 * lorsqu'on passe la souris dessus.
 * Il est utilisé dans la vue du calendrier.
 */

public class ButtonNavigation {
    private Button button;
    private StackPane root;
    private Timeline animationIn;
    private Timeline animationOut;
    private Rectangle buttonBackground;
    private Rectangle mouseEffect;

    /**
     * Constructeur de la classe ButtonNavigation.
     * Initialise le bouton avec un texte, une largeur et une hauteur spécifiés.
     *
     * @param value  Le texte à afficher sur le bouton.
     * @param width  La largeur du bouton.
     * @param height La hauteur du bouton.
     */
    public ButtonNavigation(String value, int width, int height) {
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-navigation");
        this.button.setStyle("-fx-min-width: " + width + "px; -fx-min-height: " + height + "px;");

        this.mouseEffect = new Rectangle();
        this.mouseEffect.getStyleClass().add("button-navigation-mouse-effect");
        this.mouseEffect.setVisible(false);
        this.mouseEffect.setMouseTransparent(true);

        this.buttonBackground = new Rectangle(width, height);
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
                mouseEffect.setHeight(height);
                mouseEffect.setWidth(width);
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

    /**
     * Méthode pour préparer les animations d'entrée et de sortie du bouton.
     * Définit les animations pour l'arcWidth et l'arcHeight du bouton.
     */
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

    /**
     * Méthode pour set l'action à effectuer lorsque le bouton est cliqué.
     *
     * @param handler L'action à effectuer lors du clic sur le bouton.
     */
    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.button.setOnAction(handler);
    }

    /**
     * Méthode pour obtenir le bouton.
     *
     * @return Le bouton.
     */
    public StackPane getRoot() {
        return this.root;
    }


    public void setDisable(boolean b) {
        this.button.setDisable(b);
    }
}
