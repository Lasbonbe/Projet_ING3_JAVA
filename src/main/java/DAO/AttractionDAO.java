package DAO;

import Modele.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttractionDAO {

    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

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
                String attractionType = resultSet.getString("type");
                int attractionCapacity = resultSet.getInt("max_capacity");
                int attractionPrice = resultSet.getInt("base_price");
                int attractionDuration = resultSet.getInt("duration");
                String attractionDescription = resultSet.getString("description");
                String attractionImage = resultSet.getString("image");

                Attraction attraction = new Attraction(attractionID, attractionName, attractionType, attractionCapacity, attractionPrice, attractionDuration, attractionDescription, attractionImage);
                listAttractions.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listAttractions;
    }

    public void addAttraction(Attraction attraction) {

        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Attraction(ID, nom, type, max_capacity, base_price, duration,image, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setString(2, attraction.getName());
            preparedStatement.setString(3, attraction.getType());
            preparedStatement.setInt(4, attraction.getCapacity());
            preparedStatement.setInt(5, attraction.getPrice());
            preparedStatement.setInt(6, attraction.getDuration());
            preparedStatement.setString(7, attraction.getImagePath());
            preparedStatement.setString(8, attraction.getDescription());

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

    public void deleteAttractionByID(int id) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE from Attraction where ID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();  // Changé de executeQuery() à executeUpdate()

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
                String attractionType = resultSet.getString("type");
                int attractionCapacity = resultSet.getInt("max_capacity");
                int attractionPrice = resultSet.getInt("base_price");
                int attractionDuration = resultSet.getInt("duration");
                String attractionDescription = resultSet.getString("description");
                String attractionImage = resultSet.getString("image");

                if (attractionID == id) {
                    attractionFound = new Attraction(id, attractionName, attractionType, attractionCapacity, attractionPrice, attractionDuration, attractionDescription, attractionImage);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Attraction introuvable");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return attractionFound;
    }

    public Attraction editAttraction(Attraction attraction) {

        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Attraction set nom=?, type=?, max_capacity=?, base_price=?, duration=?, image=?, description=? where ID = ?");
            preparedStatement.setString(1, attraction.getName());
            preparedStatement.setString(2, attraction.getType());
            preparedStatement.setInt(3, attraction.getCapacity());
            preparedStatement.setInt(4, attraction.getPrice());
            preparedStatement.setInt(5, attraction.getDuration());
            preparedStatement.setString(6, attraction.getImagePath());
            preparedStatement.setString(7, attraction.getDescription());
            preparedStatement.setInt(8, attraction.getAttractionID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de modification de Attraction");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return attraction;
    }


    public int getBasePrice(int attractionID) {
        int basePrice = 0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT base_price FROM Attraction WHERE ID = ?");
            preparedStatement.setInt(1, attractionID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                basePrice = resultSet.getInt("base_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération du prix de l'attraction impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return basePrice;
    }


}






