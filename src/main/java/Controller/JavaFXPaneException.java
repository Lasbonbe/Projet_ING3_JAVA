package Controller;

/**
 * Exception class for handling image loading errors in JavaFX applications.
 * This class extends RuntimeException and provides additional information about the image path.
 */
public class JavaFXPaneException extends RuntimeException {
    public JavaFXPaneException(String message) {
        super(message);
    }
}
