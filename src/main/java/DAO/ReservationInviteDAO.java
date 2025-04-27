package DAO;

import java.sql.*;

public class ReservationInviteDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    public void addReservationInvite(int inviteId, int scheduleID, Date date, int nbrTickets, double price) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO ReservationsInvites(invite_ID, schedule_ID, date, nb_tickets, total_price) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, inviteId);
            preparedStatement.setInt(2, scheduleID);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, nbrTickets);
            preparedStatement.setDouble(5, price);
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

    public int getNextInviteId() {
        int nextId = 1;
        try {
            Connection connection = sqlDatabase.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(invite_ID) as max_id FROM ReservationsInvites");
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getObject("max_id") != null) {
                    nextId = rs.getInt("max_id") + 1;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération du prochain ID d'invité");
        }
        return nextId;
    }
}
