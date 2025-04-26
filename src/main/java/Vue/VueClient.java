package Vue;

import Modele.Client;
import java.util.ArrayList;

/**
 * Classe VueClient qui représente la vue d'un client.
 * Elle contient des méthodes pour afficher les informations d'un client.
 */
public class VueClient {

    /** Affiche les informations d'un client.
     * @param client Le client à afficher.
     */
    public void displayClient(Client client) {
        System.out.println("\nClient :");
        System.out.println("Nom : " + client.getLastName());
        System.out.println("Prenom : "+  client.getFirstName());
        System.out.println("Date de naissance : " + client.getBirthDate());
        System.out.println("Email : " + client.getEmail());
    }

    /** Affiche la liste des clients.
     * @param clients La liste des clients à afficher.
     */
    public void displayClientList(ArrayList<Client> clients) {
        // Afficher la liste des clients récupérés de l'objet de ClientDAO
        for (Client client : clients) {
            displayClient(client);
        }
    }
}

