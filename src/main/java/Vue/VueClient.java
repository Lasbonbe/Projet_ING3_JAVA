package Vue;

import Modele.Client;
import java.util.ArrayList;

public class VueClient {

    public void displayClient(Client client) {
        System.out.println("Client :\n");
        System.out.println("Nom : " + client.getLastName());
        System.out.println("Prenom : "+  client.getFirstName());
        System.out.println("Email : " + client.getEmail());
    }

    public void displayClientList(ArrayList<Client> clients) {
        // Afficher la liste des clients récupérés de l'objet de ClientDAO
        for (Client client : clients) {
            displayClient(client);
        }
    }
}

