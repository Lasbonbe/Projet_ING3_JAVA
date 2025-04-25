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


    public Search searchAttractionsPrompt(String searchAttractions, boolean placesAvailable, String chosenPrice, String chosenDuration){
        StringBuilder searchPrompt = new StringBuilder("SELECT * FROM Attraction WHERE 1=1");
        ArrayList<Object> searchParameter = new ArrayList<>();

        if (searchAttractions != null && !searchAttractions.trim().isEmpty()) {
            searchPrompt.append(" AND nom LIKE ?");
            searchParameter.add("%" + searchAttractions + "%");
        }

        if (placesAvailable) {
            searchPrompt.append(" AND max_capacity > 0");
        }

        if (chosenPrice != null && !chosenPrice.startsWith("Choisir")) {
            if (chosenPrice.contains("1€-5€")) {
                searchPrompt.append(" AND base_price BETWEEN ? AND ?");
                searchParameter.add(1);
                searchParameter.add(5);
            } else if (chosenPrice.contains("6€-10€")) {
                searchPrompt.append(" AND base_price BETWEEN ? AND ?");
                searchParameter.add(6);
                searchParameter.add(10);
            } else if (chosenPrice.contains("+10€")) {
                searchPrompt.append(" AND base_price > ?");
                searchParameter.add(10);
            }
        }

        if (chosenDuration != null && !chosenDuration.startsWith("Choisir")) {
            if (chosenDuration.contains("1-5")) {
                searchPrompt.append(" AND duration BETWEEN ? AND ?");
                searchParameter.add(1);
                searchParameter.add(5);
            } else if (chosenDuration.contains("6-10")) {
                searchPrompt.append(" AND duration BETWEEN ? AND ?");
                searchParameter.add(6);
                searchParameter.add(10);
            } else if (chosenDuration.contains("+10")) {
                searchPrompt.append(" AND duration > ?");
                searchParameter.add(10);
            }
        }
        Search mySearch = new Search(searchPrompt.toString(), searchParameter);

        return mySearch;
    }

    public ArrayList<Attraction>searchAttractions(Search searchQuery) {
        ArrayList<Attraction> listAttractions = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();

            preparedStatement = connection.prepareStatement(searchQuery.getSearchPrompt());

            for (int i = 0; i < searchQuery.getSearchParameters().size(); i++) {
                preparedStatement.setObject(i + 1, searchQuery.getSearchParameters().get(i));
            }

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

                listAttractions.add(new Attraction(id, attractionName, attractionType, attractionCapacity, attractionPrice, attractionDuration, attractionDescription, attractionImage));
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
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return basePrice;
    }
}





