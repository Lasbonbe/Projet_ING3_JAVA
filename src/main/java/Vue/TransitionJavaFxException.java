package Vue;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TransitionJavaFxException extends RuntimeException {
    private Pane container;
    private Parent newView;
    private String ErrorMessage;

    public TransitionJavaFxException(String message, Pane container, Parent newView) {
        super(message);
        this.ErrorMessage = message;
        this.container = container;
        this.newView = newView;
    }
}


