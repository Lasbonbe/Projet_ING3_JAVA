package Modele;

/**
 * Classe représentant un administrateur.
 * Hérite de la classe User.
 */
public class PromotionException extends RuntimeException {
    public PromotionException(String message) {
        super(message);
    }
}
