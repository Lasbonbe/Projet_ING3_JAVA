package DAO;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;

public class AttractionDAO implements AttractionInterface {

    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    @Override
    public ArrayList<Attraction> getAllAttractions() {
        ArrayList<Attraction> listAttractions = new ArrayList<>();
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Attraction");

            while (resultSet.next()) {
                int attractionID = resultSet.getInt("ID");
                String attractionName = resultSet.getString("nom");
                int attractionCapacity = resultSet.getInt("max_capacity");
                int attractionPrice = resultSet.getInt("base_price");
                int attractionDuration = resultSet.getInt("duration");

                Attraction attraction = new Attraction(attractionID, attractionName, attractionCapacity, attractionPrice, attractionDuration);
                listAttractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listAttractions;
    }

    @Override
    public void addAttraction(Attraction attraction) {

        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Attraction(ID, nom, max_capacity, base_price, duration) values(?,?,?,?,?)");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setString(2, attraction.getName());
            preparedStatement.setInt(3, attraction.getCapacity());
            preparedStatement.setDouble(4, attraction.getPrice());
            preparedStatement.setInt(5, attraction.getDuration());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du Attraction impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    @Override
    public void deleteAttraction(Attraction attraction) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE from Attraction where ID = ?");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de Attraction impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    @Override
    public Attraction findAttraction(int attractionID) {
        Attraction attractionFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Attraction where ID = ?");
            preparedStatement.setInt(1, attractionID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String attractionName = resultSet.getString("nom");
                int attractionCapacity = resultSet.getInt("max_capacity");
                int attractionPrice = resultSet.getInt("base_price");
                int attractionDuration = resultSet.getInt("duration");

                if (attractionID == id) {
                    attractionFound = new Attraction(id, attractionName, attractionCapacity, attractionPrice, attractionDuration);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Attraction introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return attractionFound;
    }

    @Override
    public Attraction editAttraction(Attraction attraction) {

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Attraction set nom=?, max_capacity=?, base_price=?, duration=? where ID=?");
            preparedStatement.setString(1, attraction.getName());
            preparedStatement.setInt(2, attraction.getCapacity());
            preparedStatement.setInt(3, attraction.getPrice());
            preparedStatement.setInt(4, attraction.getDuration());
            preparedStatement.setInt(5, attraction.getAttractionID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de modification de Attraction");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return attraction;
    }
}
