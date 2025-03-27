package DAO;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;

public class AttractionDao implements AttractionInterface {
    private AccesSQLDatabase sqlDatabase;

    @Override
    public ArrayList<Attraction> getAllAttractions() {
        ArrayList<Attraction> listAttractions = new ArrayList<>();
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Attraction");

            while (resultSet.next()) {
                int attractionID = resultSet.getInt("ID");
                String attractionName = resultSet.getString("nom");

                Attraction attraction = new Attraction(attractionID, attractionName);

                listAttractions.add(attraction);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listAttractions;
    }

    @Override
    public void addAttraction(Attraction attraction) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Attraction(id, nom) values(?,?)");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setString(2, attraction.getAttractionName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du Attraction impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    @Override
    public void deleteAttraction(Attraction attraction) {

    }

    @Override
    public Attraction findAttraction(Attraction attraction) {
        return null;
    }

    @Override
    public Attraction editAttraction(Attraction attraction) {
        return null;
    }

    public void deleteUser(User user) { }

    public User findUser(User user) {
        return null;
    }

    public User editUser(User user) {
        return null;
    }
}
