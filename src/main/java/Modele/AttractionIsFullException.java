package Modele;

/**
 * Classe représentant un administrateur.
 * Hérite de la classe User.
 */
public class AttractionIsFullException extends RuntimeException {
    public AttractionIsFullException(String message) {
        super(message);
    }
}
