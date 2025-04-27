package Modele;


import DAO.ScheduleDAO;

import java.sql.Time;

public class PanierItem {
    private Time heureDebut;
    private Time heureFin;
    private int nbBillets;
    private double prix;

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

    public PanierItem(int scheduleID, int nbBillets, double prix) {
        ScheduleDAO dao = new ScheduleDAO();
        Time[] tab = new Time[2];
        tab = dao.getScheduleTimes(scheduleID);
        System.out.println("" + tab[0]);
        this.heureDebut = tab[0];
        this.heureFin = tab[1];
        this.nbBillets = nbBillets;
        this.prix = prix;
    }
}
