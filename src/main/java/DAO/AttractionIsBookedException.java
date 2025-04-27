package DAO;

/**
 * Classe d'exception pour indiquer qu'une attraction est déjà réservée et ne peut pas être supprimée.
 * Cette exception est levée lorsque l'utilisateur essaie de réserver une attraction qui est déjà réservée.
 */
public class AttractionIsBookedException extends RuntimeException {
    public AttractionIsBookedException(String message) {
        super(message);
    }
}
