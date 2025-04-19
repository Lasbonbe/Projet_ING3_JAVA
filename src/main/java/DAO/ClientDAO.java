package DAO;

import Modele.Client;
import java.sql.*;
import java.util.ArrayList;

public class ClientDAO {
    private static AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

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

    public void deleteClient(Client client) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Client where ID = ?");
            preparedStatement.setInt(1, client.getUserID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de Client impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    public static Client findClient(int userID) {
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
