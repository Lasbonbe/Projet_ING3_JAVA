package Modele;

public class ClientNotAdminException extends RuntimeException {
    public ClientNotAdminException(String message) {
        super(message);
    }
}
