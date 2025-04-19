package DAO;

import Modele.Attraction;
import Modele.Client;
import Vue.VueAttraction;
import Vue.VueClient;

import java.util.ArrayList;

public class DEBUG_TestDAO {
    public static void main(String[] args) {
        //Test find attraction
        AttractionDAO attractionDao = new AttractionDAO();
        VueAttraction vueAttraction = new VueAttraction();

        ArrayList<Attraction> attractions = attractionDao.getAllAttractions();
        vueAttraction.displayAttractionList(attractions);

        Attraction findAttraction = attractionDao.findAttraction(1);
        vueAttraction.displayAttraction(findAttraction);

        //Test find client
        ClientDAO clientDao = new ClientDAO();
        VueClient vueClient = new VueClient();

        ArrayList<Client> clients = clientDao.getAllClient();
        vueClient.displayClientList(clients);

        Client findClient = clientDao.findClientByID(1);
        vueClient.displayClient(findClient);
    }
}
