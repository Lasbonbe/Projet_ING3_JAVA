package Modele;

/**
 * Classe d'exception pour les utilisateurs invités.
 * Hérite de RuntimeException.
 * Utilisée pour signaler une erreur lorsque l'utilisateur est un invité.
 * et non un client ou un administrateur, lorsqu'il essaye d'accéder à une fonctionnalité réservée.
 */
public class UserIsInviteException extends RuntimeException {
    public UserIsInviteException(String message) {
        super(message);
    }
}
