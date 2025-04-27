package Modele;
import java.sql.Date;

/**
 * Classe représentant un utilisateur invité.
 * Elle hérite de la classe User.
 */
public class Invite extends User {

    /**
     * Constructeur de la classe Invite.
     * @param userID L'identifiant de l'utilisateur.
     * @param firstName Le prénom de l'utilisateur.
     * @param lastName Le nom de l'utilisateur.
     * @param email L'adresse e-mail de l'utilisateur.
     */
    public Invite(int userID,String firstName, String lastName,String email) {
        super(0,firstName,lastName,email,"");
    }

    public Invite() {
        super(0,"Invité","Invité","","");
    }
}