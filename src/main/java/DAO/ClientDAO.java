package DAO;

import Modele.Client;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;

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


    public ArrayList<Client> searchClient (String searchClients, String clientsAge, String clientsOrdersTime) {
        ArrayList<Client> listClients = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            StringBuilder dynamicSQL = new StringBuilder("SELECT client.*, COUNT(odr.OrderID) AS orderCount " + "FROM Client client " + "LEFT JOIN `Order` odr ON client.ID = odr.ClientID ");
            ArrayList<Object> params = new ArrayList<>();

            if (searchClients != null && !searchClients.trim().isEmpty()) {
                dynamicSQL.append("WHERE (client.nom LIKE ? OR client.prenom LIKE ? OR client.email LIKE ?) ");
                String search = "%" + searchClients.trim() + "%";
                params.add(search);
                params.add(search);
                params.add(search);
            }

            if (clientsAge != null && !clientsAge.startsWith("Trier")) {
                LocalDate actualDate = LocalDate.now();
                LocalDate minimumYear;
                LocalDate maximumYear;

                if (clientsAge.contains("Mini")) {
                    maximumYear = actualDate.minusYears(12);
                    dynamicSQL.append("AND client.birthdate > ? ");
                    params.add(Date.valueOf(maximumYear));
                } else if (clientsAge.contains("Maxi")) {
                    minimumYear = actualDate.minusYears(12);
                    maximumYear = actualDate.minusYears(60);
                    dynamicSQL.append("AND client.birthdate BETWEEN ? AND ? ");
                    params.add(Date.valueOf(maximumYear));
                    params.add(Date.valueOf(minimumYear));
                } else if (clientsAge.contains("Senior")) {
                    minimumYear = actualDate.minusYears(60);
                    dynamicSQL.append("AND client.birthdate < ? ");
                    params.add(Date.valueOf(minimumYear));
                }
            }

            if (clientsOrdersTime != null && !clientsOrdersTime.startsWith("Toujours")) {
                LocalDate actualDate = LocalDate.now();
                LocalDate fromThisDate = null;

                if (clientsOrdersTime.contains("3")) {
                    fromThisDate = actualDate.minusMonths(3);
                }
                else if (clientsOrdersTime.contains("6")) {
                    fromThisDate = actualDate.minusMonths(6);
                }
                else if (clientsOrdersTime.contains("12")) {
                    fromThisDate = actualDate.minusMonths(12);
                }
                if (fromThisDate != null) {
                    dynamicSQL.append("AND odr.orderDate >= ? ");
                    params.add(Date.valueOf(fromThisDate));
                }
            }

            dynamicSQL.append("GROUP BY client.ID ");

            if (clientsOrdersTime != null && !clientsOrdersTime.startsWith("Toujours")) {
                dynamicSQL.append("HAVING COUNT(odr.OrderID) > 0 ");
            }

            preparedStatement = connection.prepareStatement(dynamicSQL.toString());

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date birthDate = resultSet.getDate("birthDate");

                listClients.add(new Client(id, nom, prenom, birthDate, email, password));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Recherche de Client impossible");
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

}
