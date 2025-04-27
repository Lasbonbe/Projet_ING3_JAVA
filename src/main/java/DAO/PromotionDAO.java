package DAO;

import Modele.Attraction;
import Modele.Promotion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO pour gérer les promotions dans la base de données.
 * Fournit des méthodes pour récupérer, ajouter, modifier et supprimer des promotions.
 */
public class PromotionDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    /**
     * Récupère les promotions applicables à une attraction pour une date de session donnée.
     *
     * @param attractionID l'ID de l'attraction
     * @param sessionDate  la date de la session
     * @return une liste de promotions applicables
     */
    public List<Promotion> getApplicablePromotions(int attractionID, LocalDate sessionDate) {
        List<Promotion> applicablePromotions = new ArrayList<>();
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT p.ID, p.nom, p.percentage, p.description " +
                "FROM Promotion p " +
                "JOIN Promotion_Attraction pa ON p.ID = pa.promotion_id " +
                "LEFT JOIN Promotion_Jour pj ON p.ID = pj.promotion_id " +
                "WHERE pa.attraction_id = ? " +
                "AND ((p.date_debut <= ? AND p.date_fin >= ?) OR p.permanente = TRUE) " +
                "AND (pj.promotion_id IS NULL OR pj.jour_semaine = DAYNAME(?))";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, attractionID);
            preparedStatement.setDate(2, Date.valueOf(sessionDate));
            preparedStatement.setDate(3, Date.valueOf(sessionDate));
            preparedStatement.setDate(4, Date.valueOf(sessionDate));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int promotionID = rs.getInt("ID");
                String nom = rs.getString("nom");
                int percentage = rs.getInt("percentage");
                String description = rs.getString("description");
                Promotion promotion = new Promotion(promotionID, nom, percentage, description);
                applicablePromotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Récupération des promotions applicables impossible");
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
        return applicablePromotions;
    }

    /**
     * Vérifie si une attraction a une promotion applicable pour une date de session donnée.
     *
     * @param attraction  l'attraction à vérifier
     * @param sessionDate la date de la session
     * @return true si une promotion est applicable, false sinon
     */
    public boolean hasPromotion(Attraction attraction, LocalDate sessionDate) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Promotion p " +
                "JOIN Promotion_Attraction pa ON p.ID = pa.promotion_id " +
                "LEFT JOIN Promotion_Jour pj ON p.ID = pj.promotion_id " +
                "WHERE pa.attraction_id = ? AND ((p.date_debut <= ? " +
                "AND p.date_fin >= ?) OR (p.permanente = TRUE AND pj.promotion_id = p.ID) " +
                "AND (pj.promotion_id IS NULL OR pj.jour_semaine = DAYNAME(?)))";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, attraction.getAttractionID());
            preparedStatement.setDate(2, Date.valueOf(sessionDate));
            preparedStatement.setDate(3, Date.valueOf(sessionDate));
            preparedStatement.setDate(4, Date.valueOf(sessionDate));
            rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de vérification des promotions");
            return false;
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Récupère toutes les promotions de la base de données.
     *
     * @return une liste de toutes les promotions
     */
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Promotion";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("nom");
                String desc = rs.getString("description");
                int pourcentage = rs.getInt("percentage");

                Date debutSql = rs.getDate("date_debut");
                LocalDate dateDebut = debutSql != null ? debutSql.toLocalDate() : null;

                Date finSql = rs.getDate("date_fin");
                LocalDate dateFin = finSql != null ? finSql.toLocalDate() : null;

                boolean permanente = rs.getBoolean("permanente");
                Promotion p = new Promotion(id, nom, pourcentage, desc, dateDebut, dateFin, permanente);
                promotions.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de récupération des promotions");
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
        return promotions;
    }

    /**
     * Supprime une promotion de la base de données par son ID.
     *
     * @param id l'ID de la promotion à supprimer
     */
    public void deletePromotionByID(int id) {
        Connection conn = sqlDatabase.getConnection();
        // Supprime d'abord les liaisons enfants pour lever la contrainte FK
        clearPromotionAttractions(id);
        clearPromotionDays(id);

        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM Promotion WHERE ID = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Suppression de Promotion impossible");
        } finally {
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Supprime les jours associés à une promotion.
     *
     * @param promoId l'ID de la promotion
     */
    public void clearPromotionDays(int promoId) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM Promotion_Jour WHERE promotion_id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, promoId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de suppression des jours de la promotion");
        } finally {
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Supprime les attractions associées à une promotion.
     *
     * @param promoId l'ID de la promotion
     */
    public void clearPromotionAttractions(int promoId) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM Promotion_Attraction WHERE promotion_id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, promoId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de suppression des attractions de la promotion");
        } finally {
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Ajoute une nouvelle promotion à la base de données.
     *
     * @param promo la promotion à ajouter
     * @return l'ID de la promotion ajoutée
     */
    public int addPromotionReturnID(Promotion promo) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Promotion (nom, percentage, description, date_debut, date_fin, permanente) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, promo.getName());
            preparedStatement.setInt(2, promo.getPercentage());
            preparedStatement.setString(3, promo.getDescription());
            if (promo.getStartDate() != null) preparedStatement.setDate(4, Date.valueOf(promo.getStartDate())); else preparedStatement.setNull(4, Types.DATE);
            if (promo.getEndDate() != null) preparedStatement.setDate(5, Date.valueOf(promo.getEndDate())); else preparedStatement.setNull(5, Types.DATE);
            preparedStatement.setBoolean(6, promo.isPermanent());
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout de la promotion", e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
        return -1;
    }

    /**
     * Modifie une promotion existante dans la base de données.
     *
     * @param promotion la promotion à modifier
     */
    public void editPromotion(Promotion promotion) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE Promotion " +
                "SET nom = ?, percentage = ?, description = ?, date_debut = ?, date_fin = ?, permanente = ? " +
                "WHERE ID = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, promotion.getName());
            ps.setInt(2, promotion.getPercentage());
            ps.setString(3, promotion.getDescription());
            if (promotion.getStartDate() != null) {
                ps.setDate(4, Date.valueOf(promotion.getStartDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            if (promotion.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(promotion.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setBoolean(6, promotion.isPermanent());
            ps.setInt(7, promotion.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de mise à jour de la promotion");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ignored) {}
            }
        }
    }


    /**
     * Ajoute les jours de la semaine associés à une promotion.
     *
     * @param promoId l'ID de la promotion
     * @param days    la liste des jours de la semaine
     */
    public void addPromotionDays(int promoId, List<String> days) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO Promotion_Jour (promotion_id, jour_semaine) VALUES (?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (String d : days) {
                preparedStatement.setInt(1, promoId);
                preparedStatement.setString(2, d);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur d'insertion des jours de la promotion");
        } finally {
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Ajoute les attractions associées à une promotion.
     *
     * @param promoId l'ID de la promotion
     * @param attIds  la liste des IDs d'attractions
     */
    public void addPromotionAttractions(int promoId, List<Integer> attIds) {
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO Promotion_Attraction (promotion_id, attraction_id) VALUES (?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (Integer id : attIds) {
                preparedStatement.setInt(1, promoId);
                preparedStatement.setInt(2, id);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur d'insertion des attractions de la promotion");
        } finally {
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
    }

    /**
     * Récupère les IDs des attractions associées à une promotion.
     *
     * @param id l'ID de la promotion
     * @return une liste d'IDs d'attractions
     */
    public List<Integer> getPromotionAttractions(int id) {
        List<Integer> attIds = new ArrayList<>();
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT attraction_id FROM Promotion_Attraction WHERE promotion_id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                attIds.add(rs.getInt("attraction_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de récupération des attractions de la promotion");
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
        return attIds;
    }

    /**
     * Récupère les jours de la semaine associés à une promotion.
     *
     * @param promoId l'ID de la promotion
     * @return une liste de jours de la semaine
     */
    public List<String> getPromotionDays(int promoId) {
        List<String> days = new ArrayList<>();
        Connection conn = sqlDatabase.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT jour_semaine FROM Promotion_Jour WHERE promotion_id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, promoId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                days.add(rs.getString("jour_semaine"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de récupération des jours de la promotion");
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException ignored) {}
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException ignored) {}
            }
        }
        return days;
    }
}
