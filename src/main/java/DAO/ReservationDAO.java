package DAO;

import Modele.Client;
import Modele.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class to manage the connection to the SQL database and perform operations on it.
 */
public class ReservationDAO {

    private static AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    /**
     * Retourne le nombre de réservations pour un client donné
     **/
    public int countReservationsByClient(int clientID) {
        int count = 0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Reservation WHERE client_ID = ?");
            preparedStatement.setInt(1, clientID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du comptage des réservations");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return count;
    }

    /**
     * Retourne la liste de réservations pour un client donné
     **/
    public static ArrayList<Reservation> getReservationsByClient (int clientID) {
        ArrayList<Reservation> listReservations = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Reservation WHERE client_ID = ?");
            preparedStatement.setInt(1, clientID);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int clientId = resultSet.getInt("client_ID");
                int scheduleId = resultSet.getInt("schedule_ID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int nbrTickets = resultSet.getInt("nb_tickets");
                float price = resultSet.getFloat("total_price");
                int panierId = resultSet.getInt("panier_ID");

                Reservation reservation = new Reservation(id, clientId, scheduleId, date, nbrTickets, price, panierId);

                listReservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de resérvations du client impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listReservations;
    }

    /**
     * Retourne la liste de toutes les réservations
     **/
    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> listReservations = new ArrayList<>();
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Reservation");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int clientId = resultSet.getInt("client_ID");
                int scheduleId = resultSet.getInt("schedule_ID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int nbrTickets = resultSet.getInt("nb_tickets");
                float price = resultSet.getFloat("total_price");
                int panierId = resultSet.getInt("panier_ID");

                Reservation reservation = new Reservation(id, clientId, scheduleId, date, nbrTickets, price, panierId);

                listReservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de toutes les réservations impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listReservations;
    }

    /**
     * Ajoute une réservation à la base de données
     *
     * @param clientID  L'ID du client
     * @param scheduleID L'ID du calendrier
     * @param date      La date de la réservation
     * @param nbrTickets Le nombre de tickets réservés
     * @param price     Le prix total de la réservation
     * @param panierID  L'ID du panier
     **/
    public void addReservation(int clientID, int scheduleID, Date date, int nbrTickets, double price, int panierID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Reservation(client_ID, schedule_ID, date, nb_tickets, total_price, panier_ID) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, clientID);
            preparedStatement.setInt(2, scheduleID);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, nbrTickets);
            preparedStatement.setDouble(5, price);
            preparedStatement.setInt(6, panierID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur d'ajout de réservation");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur de fermeture des ressources");
            }
        }
    }


}


