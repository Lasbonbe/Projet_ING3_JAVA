package DAO;

import java.sql.*;

/**
 * Class to manage the connection to the SQL database and perform operations on it.
 */
public class ReservationDAO {

    /**
     * Retourne le nombre de réservations pour un client donné
     **/
    public int countReservationsByClient(int clientID) {
        int count = 0;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = new AccesSQLDatabase().getConnection();
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
}


