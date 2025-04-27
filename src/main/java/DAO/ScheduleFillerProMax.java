package DAO;

import Modele.Attraction;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/** Classe poir remplir les Schedules des différentes attractions
 * @version 1.0
 */
public class ScheduleFillerProMax {
    private static LocalTime startTime = LocalTime.of(9, 0);
    private static LocalTime endTime = LocalTime.of(17, 0);
    // 3 mois de réservation en avance
    private static ScheduleDAO scheduleDAO = new ScheduleDAO();
    private static AttractionDAO attractionDAO = new AttractionDAO();

    /**
     * Remplit les horaires d'une attraction entre startTime et endTime
     * La méthode va chercher la durée de l'attraction dans la base de données
     * puis génère tout les schedules entre startTime et endTime par intervalles de la durée de l'attraction
     *
     * @param attraction Une attraction
     */
    public static void generateSchedulesForAttraction(Attraction attraction) {
        int duration = attraction.getDuration();
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusMonths(1);


        while (!currentDate.isAfter(endDate)) {
            LocalTime currentTime = startTime;
            while (currentTime.plusMinutes(duration).isBefore(endTime)
                    || currentTime.plusMinutes(duration).equals(endTime)) {
                java.sql.Time startTimeSQL = java.sql.Time.valueOf(currentTime);
                java.sql.Time endTimeSQL = java.sql.Time.valueOf(currentTime.plusMinutes(duration));
                scheduleDAO.addScheduleWithDuration(attraction, java.sql.Date.valueOf(currentDate), startTimeSQL, endTimeSQL);
                currentTime = currentTime.plusMinutes(duration);
            }
            currentDate = currentDate.plusDays(1);
        }
    }

    /**
     * Remplit les horaires de toutes les attractions
     * La méthode va chercher toutes les attractions dans la base de données
     * puis génère tout les schedules entre startTime et endTime par intervalles de la durée de l'attraction
     */
    public static void generateAllSchedulesForAttractions() {
        for (Attraction attraction : attractionDAO.getAllAttractions()) {
            System.out.println("Generating schedules for " + attraction.getName());
            generateSchedulesForAttraction(attraction);
            System.out.println("Schedules generated for " + attraction.getName());
        }
    }

    public static void main(String[] args) {
        generateAllSchedulesForAttractions();
    }
}