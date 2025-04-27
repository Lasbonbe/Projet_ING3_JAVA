package Modele;

/**
 * Exeption class for handling client-related errors in a JavaFX application.
 */
public class UserNotAdminException extends RuntimeException {
    public UserNotAdminException(String message) {
        super(message);
    }
}
