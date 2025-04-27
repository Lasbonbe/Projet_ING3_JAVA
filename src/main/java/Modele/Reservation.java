package Modele;


import DAO.ScheduleDAO;

import java.sql.Time;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int clientID;
    private int scheduleID;
    private int panierID;
    private int nbBillets;
    private float prix;
    private LocalDate date;
    private Time heureDebut;
    private Time heureFin;



    public Reservation(int id, int clientID, int scheduleID, LocalDate date, int nbBillets, float prix, int panierID) {
        ScheduleDAO dao = new ScheduleDAO();
        Time[] tab = new Time[2];
        tab = dao.getScheduleTimes(scheduleID);
        System.out.println("" + tab[0]);
        this.heureDebut = tab[0];
        this.heureFin = tab[1];
        this.id = id;
        this.clientID = clientID;
        this.scheduleID = scheduleID;
        this.panierID = panierID;
        this.date = date;
        this.prix = prix;
        this.nbBillets = nbBillets;
    }

    public int getId() { return id; }
    public int getClientID() { return clientID; }
    public int getScheduleID() { return scheduleID; }
    public int getPanierID() { return panierID; }
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
}