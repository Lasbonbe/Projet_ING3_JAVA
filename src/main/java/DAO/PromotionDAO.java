package DAO;

import Modele.Promotion;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    public List<Promotion> getApplicablePromotions(int attractionID, LocalDate sessionDate) {
        List<Promotion> applicablePromotions = new ArrayList<>();
        Connection connection = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT p.ID, p.nom, p.percentage, p.description FROM Promotion p JOIN Promotion_Attraction pa ON p.ID = pa.promotion_id LEFT JOIN Promotion_Jour pj ON p.ID = pj.promotion_id WHERE pa.attraction_id = ? AND ((p.date_debut <= ? AND p.date_fin <= ?) OR p.permanente = TRUE) AND (pj.promotion_id IS NULL OR pj.jour_semaine = DAYNAME(?))");
            preparedStatement.setInt(1, attractionID);
            preparedStatement.setDate(2, java.sql.Date.valueOf(sessionDate));
            System.out.println(java.sql.Date.valueOf(sessionDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(sessionDate));
            preparedStatement.setDate(4, java.sql.Date.valueOf(sessionDate));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int promotionID = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                int percentage = resultSet.getInt("percentage");
                String description = resultSet.getString("description");
                Promotion promotion = new Promotion(promotionID, nom, percentage, description);
                applicablePromotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération des promotions applicables impossibles");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return applicablePromotions;
    }

}
