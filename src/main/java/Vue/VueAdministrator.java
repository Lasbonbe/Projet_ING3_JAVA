package Vue;

import Modele.Administrator;
import java.util.ArrayList;

public class VueAdministrator {

    public void displayAdministrator(Administrator administrator) {
        System.out.println("Administrateur :\n");
        System.out.println("Nom : " + administrator.getLastName());
        System.out.println("Prenom : "+  administrator.getFirstName());
        System.out.println("Email : " + administrator.getEmail());
    }

    public void displayAdminList(ArrayList<Administrator> administrators) {
        // Afficher la liste des admins récupérés de l'objet de AdministratorDAO
        for (Administrator administrator : administrators) {
            displayAdministrator(administrator);
        }
    }
}