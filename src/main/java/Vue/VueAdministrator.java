package Vue;

import Modele.Administrator;
import java.util.ArrayList;

/**
 * Classe VueAdministrator qui représente la vue pour afficher les informations des administrateurs.
 * Elle contient des méthodes pour afficher un administrateur et une liste d'administrateurs.
 */
public class VueAdministrator {

    /**
     * Méthode pour afficher les informations d'un administrateur.
     */
    public void displayAdministrator(Administrator administrator) {
        System.out.println("\nAdministrateur :");
        System.out.println("Nom : " + administrator.getLastName());
        System.out.println("Prenom : "+  administrator.getFirstName());
        System.out.println("Email : " + administrator.getEmail());
    }

    /**
     * Méthode pour afficher la liste des administrateurs.
     *
     * @param administrators Liste des administrateurs à afficher.
     */
    public void displayAdminList(ArrayList<Administrator> administrators) {
        // Afficher la liste des admins récupérés de l'objet de AdministratorDAO
        for (Administrator administrator : administrators) {
            displayAdministrator(administrator);
        }
    }
}