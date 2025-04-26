package Modele;

/**
 * Exeption class for handling client-related errors in a JavaFX application.
 */
public class ClientNotAdminException extends RuntimeException {
    public ClientNotAdminException(String message) {
        super(message);
    }
}
