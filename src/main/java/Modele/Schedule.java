package Modele;

import Vue.Calendar.ButtonNavigation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

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


    public int getIdSchedule() {
        return idSchedule;
    }
    public int getAttractionId() {
        return attractionId;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getReservedPlaces() {
        return reservedPlaces;
    }
    public int getTotalPlaces() {
        return totalPlaces;
    }
    public String getNameAttraction() {
        return nameAttraction;
    }
    public ButtonNavigation getBtnNav() {
        return btnNav;
    }
    public int getPDispos() {
        return this.placesDispos;
    }
    public Time getHourDebut() {
        return hourDebut;
    }
    public Time getHourEnd() {
        return hourEnd;
    }
    public String getStatut() {
        return statut;
    }

    public void setPlacesDispos(int placesDispos) {
        this.placesDispos = placesDispos;
    }

    //░█████╗░░█████╗░███╗░░██╗░██████╗████████╗██████╗░██╗░░░██╗░█████╗░████████╗░█████╗░██████╗░░██████╗
    //██╔══██╗██╔══██╗████╗░██║██╔════╝╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗██╔════╝
    //██║░░╚═╝██║░░██║██╔██╗██║╚█████╗░░░░██║░░░██████╔╝██║░░░██║██║░░╚═╝░░░██║░░░██║░░██║██████╔╝╚█████╗░
    //██║░░██╗██║░░██║██║╚████║░╚═══██╗░░░██║░░░██╔══██╗██║░░░██║██║░░██╗░░░██║░░░██║░░██║██╔══██╗░╚═══██╗
    //╚█████╔╝╚█████╔╝██║░╚███║██████╔╝░░░██║░░░██║░░██║╚██████╔╝╚█████╔╝░░░██║░░░╚█████╔╝██║░░██║██████╔╝
    //░╚════╝░░╚════╝░╚═╝░░╚══╝╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝░╚═════╝░░╚════╝░░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝╚═════╝░

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

    public Schedule(String nameAttraction, Time hourDebut, Time hourEnd, int reservedPlaces, int totalPlaces, String statut, LocalDate date) {
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
