package Modele;


import DAO.ScheduleDAO;

import java.sql.Time;
import java.time.LocalDate;

/**
 * Classe représentant une réservation.
 */
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
    private String statut;



    /**
     * Constructeur de la classe Reservation.
     *
     * @param id        L'identifiant de la réservation.
     * @param clientID  L'identifiant du client.
     * @param scheduleID L'identifiant du programme.
     * @param date      La date de la réservation.
     * @param nbBillets Le nombre de billets réservés.
     * @param prix      Le prix total de la réservation.
     * @param panierID  L'identifiant du panier.
     */
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

    /**
     * Constructeur de la classe Reservation.
     *
     * @param id        L'identifiant de la réservation.
     * @param clientID  L'identifiant du client.
     * @param scheduleID L'identifiant du programme.
     * @param date      La date de la réservation.
     * @param nbBillets Le nombre de billets réservés.
     * @param prix      Le prix total de la réservation.
     * @param payementStatus Le statut de paiement de la réservation.
     **/
    public Reservation(int id, int clientID, int scheduleID, LocalDate date, int nbBillets, float prix, String payementStatus) {
        this.id = id;
        this.clientID = clientID;
        this.scheduleID = scheduleID;
        this.date = date;
        this.nbBillets = nbBillets;
        this.prix = prix;
        this.statut = payementStatus;
    }

    /** GETTER getID
     * @return L'identifiant de la réservation.
     */
    public int getId() { return id; }
    /** GETTER getClientID
     * @return L'identifiant du client.
     */
    public int getClientID() { return clientID; }
    /** GETTER getScheduleID
     * @return L'identifiant du programme.
     */
    public int getScheduleID() { return scheduleID; }
    /** GETTER getDate
     * @return La date de la réservation.
     */
    public LocalDate getDate() { return date; }
    /** GETTER getPanierID
     * @return L'identifiant du panier.
     */
    public int getPanierID() { return panierID; }
    /** GETTER getHeureDebut
     * @return L'heure de début de la réservation.
     */
    public Time getHeureDebut() {return heureDebut;}
    /** GETTER getHeureFin
     * @return L'heure de fin de la réservation.
     */
    public Time getHeureFin() {return heureFin;}
    /** GETTER getNbBillets
     * @return Le nombre de billets réservés.
     */
    public int getNbBillets() {return nbBillets;}
    /** GETTER getPrix
     * @return Le prix total de la réservation.
     */
    public double getPrix() {return prix;}
    /** GETTER getStatut
     * @return Le statut de paiement de la réservation.
     */
    public String getStatut() {return statut;}
}