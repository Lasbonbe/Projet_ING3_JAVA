package Modele;

public class AttractionIsFullException extends RuntimeException {
    public AttractionIsFullException(String message) {
        super(message);
    }
}
