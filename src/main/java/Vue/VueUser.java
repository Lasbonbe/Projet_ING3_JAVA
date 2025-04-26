package Vue;

import Modele.User;
import java.util.ArrayList;

/**
 * Classe VueUser qui représente la vue utilisateur.
 * Elle contient des méthodes pour afficher les informations d'un utilisateur.
 */
public class VueUser {

    /**
     * Méthode pour afficher les informations d'un utilisateur.
     *
     * @param user L'utilisateur à afficher.
     */
    public void displayUser(User user) {
        System.out.println("\nUtilisateur :");
        System.out.println("Nom : " + user.getLastName());
        System.out.println("Prenom : "+  user.getFirstName());
    }

    /**
     * Méthode pour afficher la liste des utilisateurs.
     *
     * @param users La liste des utilisateurs à afficher.
     */
    public void displayUserList(ArrayList<User> users) {
        // Afficher la liste des clients récupérés de l'objet de ClientDAO
        for (User user : users) {
            displayUser(user);
        }
    }
}

