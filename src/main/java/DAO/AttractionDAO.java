package DAO;

import Modele.*;

import java.sql.*;
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

    public ArrayList<Attraction>searchAttractions(String searchAttractions, boolean placesAvailable, String chosenPrice, String chosenDuration) {
        ArrayList<Attraction> listAttractions = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            StringBuilder dynamicSQL = new StringBuilder("SELECT * FROM Attraction WHERE 1=1");
            ArrayList<Object> params = new ArrayList<>();

            if (searchAttractions != null && !searchAttractions.trim().isEmpty()) {
                dynamicSQL.append(" AND nom LIKE ?");
                params.add("%" + searchAttractions + "%");
            }

            if (placesAvailable) {
                dynamicSQL.append(" AND max_capacity > 0");
            }

            if (chosenPrice != null && !chosenPrice.startsWith("Choisir")) {
                if (chosenPrice.contains("1€-5€")) {
                    dynamicSQL.append(" AND base_price BETWEEN ? AND ?");
                    params.add(1);
                    params.add(5);
                } else if (chosenPrice.contains("6€-10€")) {
                    dynamicSQL.append(" AND base_price BETWEEN ? AND ?");
                    params.add(6);
                    params.add(10);
                } else if (chosenPrice.contains("+10€")) {
                    dynamicSQL.append(" AND base_price > ?");
                    params.add(10);
                }
            }

            if (chosenDuration != null && !chosenDuration.startsWith("Choisir")) {
                if (chosenDuration.contains("1-5")) {
                    dynamicSQL.append(" AND duration BETWEEN ? AND ?");
                    params.add(1);
                    params.add(5);
                } else if (chosenDuration.contains("6-10")) {
                    dynamicSQL.append(" AND duration BETWEEN ? AND ?");
                    params.add(6);
                    params.add(10);
                } else if (chosenDuration.contains("+10")) {
                    dynamicSQL.append(" AND duration > ?");
                    params.add(10);
                }
            }

            preparedStatement = connection.prepareStatement(dynamicSQL.toString());

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                int capacite = resultSet.getInt("max_capacity");
                int prix = resultSet.getInt("base_price");
                int duree = resultSet.getInt("duration");

                listAttractions.add(new Attraction(id, nom, capacite, prix, duree));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Recherche de Attraction impossible");
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
}




