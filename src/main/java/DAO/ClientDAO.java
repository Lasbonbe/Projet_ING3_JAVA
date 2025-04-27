package DAO;

import Modele.Client;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;

/**
 * Classe ClientDAO
 * Cette classe gère les opérations de base de données liées aux clients.
 * Elle permet d'ajouter, supprimer, modifier et rechercher des clients dans la base de données.
 */
public class ClientDAO {
    private static AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    /**
     * Méthode pour recuperer tous les clients de la base de données.
     *
     * @return ArrayList<Client> - Liste de tous les clients
     */

    public ArrayList<Client> getAllClient() {
        ArrayList<Client> listClients = new ArrayList<>();
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Client");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date birthDate = resultSet.getDate("birthDate");

                Client client = new Client(id, nom, prenom, birthDate, email, password);

                listClients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listClients;
    }

    /**
     * Méthode pour ajouter un client à la base de données.
     *
     * @param client - Client à ajouter
     */
    public void addClient(Client client) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Client(nom, prenom, email, password, birthdate) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, client.getLastName());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setDate(5, client.getBirthDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du Client impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    /**
     * Méthode pour supprimer un client de la base de données.
     *
     * @param id - ID du client à supprimer
     */
    public void deleteClientByID(int id ) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Client WHERE ID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du Client impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    /**
     * Méthode pour vérifier si un client peut se connecter avec ses identifiants.
     *
     * @param email - Email du client
     * @param password - Mot de passe du client
     * @return boolean - true si les identifiants sont valides, false sinon
     */
    public boolean loginClient(String email, String password) {
        boolean isValid = false;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Client WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return isValid;
    }


    /**
     * Méthode pour trouver un client par son ID.
     *
     * @param userID - ID du client à rechercher
     * @return Client - Client trouvé ou null si non trouvé
     */
    public static Client findClientByID(int userID) {
        Client clientFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Client where ID = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date birthDate = resultSet.getDate("birthDate");

                if (userID == id) {
                    clientFound = new Client(id, nom, prenom, birthDate, email, password);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return clientFound;
    }

    /**
     * Méthode pour trouver un client par son email.
     *
     * @param email - Email du client à rechercher
     * @return Client - Client trouvé ou null si non trouvé
     */
    public static Client findClientByEmail(String email) {
        Client clientFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Client where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                Date birthDate = resultSet.getDate("birthDate");

                if (email.equals(resultSet.getString("email"))) {
                    clientFound = new Client(id, nom, prenom, birthDate, email, password);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return clientFound;
    }



    /**
     * Méthode pour modifier un client dans la base de données.
     *
     * @param client - Client à modifier
     * @return Client - Client modifié
     */
    public Client editClient(Client client) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Client set nom=?, prenom=?, email=?, password=?, birthDate=? where ID=?");
            preparedStatement.setString(1, client.getLastName());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setDate(5, client.getBirthDate());
            preparedStatement.setInt(6, client.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de modification de Client");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return client;
    }

}
