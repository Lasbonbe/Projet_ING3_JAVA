package Vue;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Exception class for handling transition-related errors in a JavaFX application.
 */
public class TransitionJavaFxException extends RuntimeException {
    private Pane container;
    private Parent newView;
    private String ErrorMessage;

    /**
     * Constructor for TransitionJavaFxException.
     *
     * @param message   The error message to be displayed.
     * @param container The container where the transition will occur.
     * @param newView   The new view to be displayed.
     */
    public TransitionJavaFxException(String message, Pane container, Parent newView) {
        super(message);
        this.ErrorMessage = message;
        this.container = container;
        this.newView = newView;
    }
}


