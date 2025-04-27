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
            preparedStatement = connection.prepareStatement("SELECT Schedule.ID, nom, hour_debut, hour_end, total_places, reserved_places, statut " +
                    "FROM Schedule JOIN Attraction ON Schedule.Attraction_ID = Attraction.ID WHERE Attraction.ID = ? AND date = ? ORDER BY Schedule.hour_debut");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nameAttraction = resultSet.getString("nom");
                Time hourDebut = resultSet.getTime("hour_debut");
                Time hourEnd = resultSet.getTime("hour_end");
                int totalPlaces = resultSet.getInt("total_places");
                int reservedPlaces = resultSet.getInt("reserved_places");
                String statut = resultSet.getString("statut");

                Schedule schedule = new Schedule(id, nameAttraction, hourDebut, hourEnd, reservedPlaces, totalPlaces, statut, date);
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
     * Méthode addScheduleWithDuration pour ajouter un horaire à la base de données avec en plus une durée
     * @param attraction L'attraction associée
     * @param currentTime L'heure de début
     * @param endTime L'heure de fin
     * @param date La date
     */
    public void addScheduleWithDuration(Attraction attraction, Date date, Time currentTime, Time endTime) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Schedule(Attraction_ID, hour_debut, hour_end, total_places, reserved_places, date,statut) values(?,?,?,?,?,?,'Ouvert')");
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setTime(2, currentTime);
            preparedStatement.setTime(3, endTime);
            preparedStatement.setInt(4, attraction.getCapacity());
            preparedStatement.setInt(5, 0);
            preparedStatement.setDate(6, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de schedule impossible avec ID attraction " + attraction.getAttractionID());
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
     * Méthode pour supprimer tout les horaires d'une attraction
     * @param attraction_ID l'ID de l'attraction
     */
    public void deleteScheduleByAttraction(int attraction_ID) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Schedule WHERE Attraction_ID = ?");
            preparedStatement.setInt(1, attraction_ID);
            preparedStatement.executeUpdate();
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

    public Time[] getScheduleTimes(int idSchedule) {
        Time[] tab = new Time[2];
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT Schedule.hour_debut, Schedule.hour_end FROM Schedule WHERE Schedule.ID = ?");
            preparedStatement.setInt(1, idSchedule);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tab[0] = resultSet.getTime("hour_debut");
                tab[1] = resultSet.getTime("hour_end");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération des horaires de la session impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return tab;
    }

    public int getPdispos(Schedule schedule) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT total_places, reserved_places FROM Schedule WHERE ID = ?");
            preparedStatement.setInt(1, schedule.getIdSchedule());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total_places") - resultSet.getInt("reserved_places");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Récupération du nombre de places disponibles impossible");
        }
        return 0;
    }

    public Date getScheduleDateById(int idSchedule) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT date FROM Schedule WHERE ID = ?");
            preparedStatement.setInt(1, idSchedule);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDate("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Récupération de la date de la session impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur de fermeture des ressources");
            }
        }
        return null;
    }

    public void tempReservation(Schedule schedule, int nbBillets) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Schedule SET reserved_places = reserved_places + ? WHERE ID = ?");
            preparedStatement.setInt(1, nbBillets);
            preparedStatement.setInt(2, schedule.getIdSchedule());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Réservation temporaire impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
    }

    public void deleteTempReservation(int scheduleId, int nbBillets) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Schedule SET reserved_places = reserved_places - ? WHERE ID = ?");
            preparedStatement.setInt(1, nbBillets);
            preparedStatement.setInt(2, scheduleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Mise à jour de la session impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Fermeture des ressources impossible");
            }
        }

    }
}
