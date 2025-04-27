package Modele;

import Vue.Calendar.ButtonNavigation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

/**
 * Classe Schedule
 * Représente un horaire d'attraction.
 */
public class Schedule {
    private int idSchedule;
    private String nameAttraction;
    private int attractionId;
    private Time hourDebut;
    private Time hourEnd;
    private int totalPlaces;
    private int reservedPlaces;
    private int placesDispos;
    private LocalDate date;
    private String statut;
    private ButtonNavigation btnNav;

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░


    /** GETTER getIdSchedule
     * @return L'identifiant de l'horaire.
     */
    public int getIdSchedule() {
        return idSchedule;
    }
    /** GETTER getAttractionId
     * @return L'identifiant de l'attraction.
     */
    public int getAttractionId() {
        return attractionId;
    }
    /** GETTER getDate
     * @return La date de l'horaire.
     */
    public LocalDate getDate() {
        return date;
    }
    /** GETTER get ReservedPlaces
     * @return Le nombre de places réservées.
     */
    public int getReservedPlaces() {
        return reservedPlaces;
    }
    /** GETTER getTotalPlaces
     * @return Le nombre total de places.
     */
    public int getTotalPlaces() {
        return totalPlaces;
    }
    /** GETTER getNameAttraction
     * @return Le nom de l'attraction.
     */
    public String getNameAttraction() {
        return nameAttraction;
    }

    /** GETTER getBtnNav
     * @return Le bouton de navigation.
     */
    public ButtonNavigation getBtnNav() {
        return btnNav;
    }
    /** GETTER getPlacesDispos
     * @return Le nombre de places disponibles.
     */
    public int getPDispos() {
        return this.placesDispos;
    }
    /** GETTER getHourDebut
     * @return L'heure de début de l'horaire.
     */
    public Time getHourDebut() {
        return hourDebut;
    }
    /** GETTER getHourEnd
     * @return L'heure de fin de l'horaire.
     */
    public Time getHourEnd() {
        return hourEnd;
    }
    /** GETTER getStatut
     * @return Le statut de l'horaire.
     */
    public String getStatut() {
        return statut;
    }
    /** SETTER setPlacesDispos
     * @param placesDispos Le nombre de places disponibles.
     */
    public void setPlacesDispos(int placesDispos) {
        this.placesDispos = placesDispos;
    }

    /** SETTER setStatut
     *
     * @param statut Le statut de la session de l'attraction
     */
    public void setStatut(String statut) {
        this.statut = statut;
    }
    //░█████╗░░█████╗░███╗░░██╗░██████╗████████╗██████╗░██╗░░░██╗░█████╗░████████╗░█████╗░██████╗░░██████╗
    //██╔══██╗██╔══██╗████╗░██║██╔════╝╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗██╔════╝
    //██║░░╚═╝██║░░██║██╔██╗██║╚█████╗░░░░██║░░░██████╔╝██║░░░██║██║░░╚═╝░░░██║░░░██║░░██║██████╔╝╚█████╗░
    //██║░░██╗██║░░██║██║╚████║░╚═══██╗░░░██║░░░██╔══██╗██║░░░██║██║░░██╗░░░██║░░░██║░░██║██╔══██╗░╚═══██╗
    //╚█████╔╝╚█████╔╝██║░╚███║██████╔╝░░░██║░░░██║░░██║╚██████╔╝╚█████╔╝░░░██║░░░╚█████╔╝██║░░██║██████╔╝
    //░╚════╝░░╚════╝░╚═╝░░╚══╝╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝░╚═════╝░░╚════╝░░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝╚═════╝░

    /**
     * Constructeur de la classe Schedule.
     * @param idSchedule L'identifiant de l'horaire.
     * @param idAttraction L'identifiant de l'attraction.
     * @param hourDebut L'heure de début de l'horaire.
     * @param hourEnd L'heure de fin de l'horaire.
     * @param reservedPlaces Le nombre de places réservées.
     * @param totalPlaces Le nombre total de places.
     * @param date La date de l'horaire.
     * @param statut Le statut de l'horaire.
     */
    public Schedule(int idSchedule, int idAttraction, Time hourDebut, Time hourEnd, int reservedPlaces, int totalPlaces, LocalDate date, String statut) {
        this.idSchedule = idSchedule;
        this.attractionId = idAttraction;
        this.hourDebut = hourDebut;
        this.hourEnd = hourEnd;
        this.totalPlaces = totalPlaces;
        this.reservedPlaces = reservedPlaces;
        this.placesDispos = this.totalPlaces - this.reservedPlaces;
        this.date = date;
        this.statut = statut;
    }

    /**
     * Constructeur de la classe Schedule.
     * @param nameAttraction Le nom de l'attraction.
     * @param hourDebut L'heure de début de l'horaire.
     * @param hourEnd L'heure de fin de l'horaire.
     * @param reservedPlaces Le nombre de places réservées.
     * @param totalPlaces Le nombre total de places.
     * @param statut Le statut de l'horaire.
     * @param date La date de l'horaire.
     */
    public Schedule(int id, String nameAttraction, Time hourDebut, Time hourEnd, int reservedPlaces, int totalPlaces, String statut, LocalDate date) {
        this.idSchedule = id;
        this.nameAttraction = nameAttraction;
        this.hourDebut = hourDebut;
        this.hourEnd = hourEnd;
        this.reservedPlaces = reservedPlaces;
        this.totalPlaces = totalPlaces;
        this.placesDispos = this.totalPlaces - this.reservedPlaces;
        this.statut = statut;
        this.btnNav = new ButtonNavigation("Réserver", 175, 75);
        this.date = date;
    }
}
