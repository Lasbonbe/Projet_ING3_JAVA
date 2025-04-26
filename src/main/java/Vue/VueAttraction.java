package Vue;

import Modele.Attraction;
import java.util.ArrayList;

public class VueAttraction {
    /** Méthode pour afficher les détails d'une attraction */

    public void displayAttraction(Attraction attraction) {
        System.out.println("\nNom de l'attraction : " + attraction.getName());
        System.out.println("Nombre de places : " + attraction.getCapacity());
        System.out.println("Prix : " + attraction.getPrice());
        System.out.println("Durée d'un tour : " + attraction.getDuration());
    }

    /** Méthode pour afficher la liste des attractions */
    public void displayAttractionList(ArrayList<Attraction> attractions) {
        // Afficher la liste des attractions récupérées de l'objet de AttractionDAO
        for (Attraction attraction : attractions) {
            displayAttraction(attraction);
        }
    }
}
