package DAO;

import Modele.Client;

import java.util.ArrayList;

public interface ClientInterface {
    public ArrayList<Client> getAllClient();
    public void addClient(Client client);
    public void deleteClient(Client client);
    public Client findClient(int clientID);
    public Client editClient(Client client);
}
