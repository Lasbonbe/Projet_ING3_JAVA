package DAO;

import Modele.Attraction;
import Modele.Promotion;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PromotionDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    public List<Promotion> getApplicablePromotions(int attractionID, LocalDate sessionDate) {
        List<Promotion> applicablePromotions = new ArrayList<>();
        Connection connection = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT p.ID, p.nom, p.percentage, p.description FROM Promotion p JOIN Promotion_Attraction pa ON p.ID = pa.promotion_id LEFT JOIN Promotion_Jour pj ON p.ID = pj.promotion_id WHERE pa.attraction_id = ? AND ((p.date_debut <= ? AND p.date_fin >= ?) OR p.permanente = TRUE) AND (pj.promotion_id IS NULL OR pj.jour_semaine = DAYNAME(?))");
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

    public boolean hasPromotion(Attraction attraction, LocalDate sessionDate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            String sql = "SELECT * FROM Promotion p JOIN Promotion_Attraction pa ON p.ID = pa.promotion_id LEFT JOIN Promotion_Jour pj ON p.ID = pj.promotion_id WHERE pa.attraction_id = ? AND ((p.date_debut <= ? AND p.date_fin >= ?) OR (p.permanente = TRUE AND pj.promotion_id = p.ID) AND (pj.promotion_id IS NULL OR pj.jour_semaine = DAYNAME(?)))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(sessionDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(sessionDate));
            //preparedStatement.setString(4, "age");
            preparedStatement.setDate(4, java.sql.Date.valueOf(sessionDate));
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de vérification des promotions");
            return false;
        }
    }

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM Promotion";
        try (Connection conn = sqlDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id          = rs.getInt("ID");
                String nom      = rs.getString("nom");
                String desc     = rs.getString("description");
                int pourcentage = rs.getInt("percentage");

                // date_debut peut être NULL
                java.sql.Date debutSql = rs.getDate("date_debut");
                LocalDate dateDebut    = (debutSql != null)
                        ? debutSql.toLocalDate()
                        : null;

                // date_fin peut être NULL
                java.sql.Date finSql   = rs.getDate("date_fin");
                LocalDate dateFin      = (finSql != null)
                        ? finSql.toLocalDate()
                        : null;

                // le flag permanente (tinyint 0/1)
                boolean permanente     = rs.getBoolean("permanente");
                System.out.println(permanente);

                // Créez votre constructeur qui prend ces champs
                Promotion p = new Promotion(id, nom, pourcentage, desc, dateDebut, dateFin);
                promotions.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de récupération des promotions");
        }
        return promotions;
    }

    public void deletePromotionByID(int id ) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            String sql = "DELETE FROM Promotion WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de suppression de la promotion");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }
}
