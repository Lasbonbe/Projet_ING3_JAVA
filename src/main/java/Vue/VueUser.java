package Vue;

import Modele.User;
import java.util.ArrayList;

public class VueUser {

    public void displayUser(User user) {
        System.out.println("\nUtilisateur :");
        System.out.println("Nom : " + user.getLastName());
        System.out.println("Prenom : "+  user.getFirstName());
        System.out.println("Age : " + user.getAge());
        System.out.println("Email : " + user.getEmail());
    }

    public void displayUserList(ArrayList<User> users) {
        // Afficher la liste des clients récupérés de l'objet de ClientDAO
        for (User user : users) {
            displayUser(user);
        }
    }
}

