package Modele;


import DAO.AttractionDAO;
import DAO.ScheduleDAO;

import java.sql.Time;

public class PanierItem {
    private int id;
    private Time heureDebut;
    private Time heureFin;
    private int nbBillets;
    private double prix;
    private String attractionName;
    private int idSchedule;

    public int getId() {
        return id;
    }
    public Time getHeureDebut() {
        return heureDebut;
    }
    public Time getHeureFin() {
        return heureFin;
    }
    public int getNbBillets() {
        return nbBillets;
    }
    public double getPrix() {
        return prix;
    }
    public String getAttractionName() {
        return attractionName;
    }
    public int getIdSchedule() {
        return idSchedule;
    }

    public PanierItem(int id, int scheduleID, int nbBillets, double prix) {
        this.id = id;
        this.idSchedule = scheduleID;
        ScheduleDAO dao = new ScheduleDAO();
        Time[] tab = new Time[2];
        tab = dao.getScheduleTimes(scheduleID);
        AttractionDAO attractionDAO = new AttractionDAO();
        this.attractionName = attractionDAO.getAttractionNameFromSchedule(scheduleID);
        System.out.println("" + tab[0]);
        this.heureDebut = tab[0];
        this.heureFin = tab[1];
        this.nbBillets = nbBillets;
        this.prix = prix;
    }
}
