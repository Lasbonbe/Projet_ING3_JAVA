package DAO;

import Modele.Schedule;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleDAO implements ScheduleInterface{
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    @Override
    public ArrayList<Schedule> getSchedule() {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Schedule");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int attractionId = resultSet.getInt("Attraction_ID");
                Time hourDebut = resultSet.getTime("hour_debut");
                Time hourEnd = resultSet.getTime("hour_end");
                int totalPlaces = resultSet.getInt("total_places");
                int reservedPlaces = resultSet.getInt("reserved_places");
                Date date = resultSet.getDate("date");
                String statut = resultSet.getString("statut");

                Schedule schedule = new Schedule(id, attractionId, hourDebut, hourEnd, totalPlaces, reservedPlaces, date, statut);
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de schedules impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return null;
    }

    @Override
    public void addSchedule(Schedule schedule) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Schedule(ID, Attraction_ID, hour_debut, hour_end, total_places, reserved_places, date, statut) values(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, schedule.getIdSchedule());
            preparedStatement.setInt(2, schedule.getAttractionId());
            preparedStatement.setTime(3, schedule.getHourDebut());
            preparedStatement.setTime(4, schedule.getHourEnd());
            preparedStatement.setInt(5, schedule.getTotalPlaces());
            preparedStatement.setInt(6, schedule.getReservedPlaces());
            preparedStatement.setDate(7, schedule.getDate());
            preparedStatement.setString(8, schedule.getStatut());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de schedule impossible");
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
    public void deleteSchedule(Schedule schedule) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Schedule where ID = ?");
            preparedStatement.setInt(1, schedule.getIdSchedule());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de schedule impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }
}
