package Vue;

import Modele.Attraction;
import java.util.ArrayList;

public class VueAttraction {
    public void displayAttraction(Attraction attraction) {
        System.out.println("Nom de l'attraction : " + attraction.getName());
    }

    public void displayAttractionList(ArrayList<Attraction> attractions) {
        // Afficher la liste des attractions récupérées de l'objet de AttractionDAO
        for (Attraction attraction : attractions) {
            displayAttraction(attraction);
        }
    }
}
