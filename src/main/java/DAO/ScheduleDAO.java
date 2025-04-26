package DAO;

import Modele.Attraction;
import Modele.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Classe ScheduleDAO
 * Permet d'accéder à la base de données pour les opérations liées aux horaires.
 */
public class ScheduleDAO{
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    /**
     * Convertit un LocalDate en Date
     * @param localDate le LocalDate à convertir
     * @return la date convertie
     */
    public static Date localDateToDate(LocalDate localDate) {
        return (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Méthode pour récupérer tous les horaires de la base de données
     * @return
     */
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
                LocalDate tempDate = date.toLocalDate();
                String statut = resultSet.getString("statut");

                Schedule schedule = new Schedule(id, attractionId, hourDebut, hourEnd, totalPlaces, reservedPlaces, tempDate, statut);
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
        return schedules;
    }

    /**
     * Méthode pour récupérer les horaires d'une attraction par date
     * @param date la date de l'horaire
     * @param attraction l'attraction associée
     * @return une liste d'horaires
     */
    public ArrayList<Schedule> getScheduleWithAttractionNamesByDate(LocalDate date, Attraction attraction) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT nom, hour_debut, hour_end, total_places, reserved_places, statut " +
                    "FROM Schedule JOIN Attraction ON Schedule.Attraction_ID = Attraction.ID WHERE Attraction.ID = ? AND date = ? ORDER BY Schedule.hour_debut");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nameAttraction = resultSet.getString("nom");
                Time hourDebut = resultSet.getTime("hour_debut");
                Time hourEnd = resultSet.getTime("hour_end");
                int totalPlaces = resultSet.getInt("total_places");
                int reservedPlaces = resultSet.getInt("reserved_places");
                String statut = resultSet.getString("statut");

                Schedule schedule = new Schedule(nameAttraction, hourDebut, hourEnd, reservedPlaces, totalPlaces, statut, date);
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
        return schedules;
    }

    /**
     * Méthode pour ajouter un horaire à la base de données*
     * @param schedule l'horaire à ajouter
     */
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
            Date tempDate = localDateToDate(schedule.getDate());
            preparedStatement.setDate(7, tempDate);

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

    /**
     * Méthode pour mettre à jour un horaire dans la base de données
     * @param schedule l'horaire à mettre à jour
     */
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
