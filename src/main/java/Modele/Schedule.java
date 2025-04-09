package Modele;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Schedule {
    private int idSchedule;
    private int attractionId;
    private Time hourDebut;
    private Time hourEnd;
    private int totalPlaces;
    private int reservedPlaces;
    private int placesDispos;
    private Date date;
    private String statut;

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
    public Date getDate() {
        return date;
    }
    public int getReservedPlaces() {
        return reservedPlaces;
    }
    public int getTotalPlaces() {
        return totalPlaces;
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
    public int getPlacesDispos() {
        return placesDispos;
    }

    //░█████╗░░█████╗░███╗░░██╗░██████╗████████╗██████╗░██╗░░░██╗░█████╗░████████╗░█████╗░██████╗░░██████╗
    //██╔══██╗██╔══██╗████╗░██║██╔════╝╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗██╔════╝
    //██║░░╚═╝██║░░██║██╔██╗██║╚█████╗░░░░██║░░░██████╔╝██║░░░██║██║░░╚═╝░░░██║░░░██║░░██║██████╔╝╚█████╗░
    //██║░░██╗██║░░██║██║╚████║░╚═══██╗░░░██║░░░██╔══██╗██║░░░██║██║░░██╗░░░██║░░░██║░░██║██╔══██╗░╚═══██╗
    //╚█████╔╝╚█████╔╝██║░╚███║██████╔╝░░░██║░░░██║░░██║╚██████╔╝╚█████╔╝░░░██║░░░╚█████╔╝██║░░██║██████╔╝
    //░╚════╝░░╚════╝░╚═╝░░╚══╝╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝░╚═════╝░░╚════╝░░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝╚═════╝░

    public Schedule(int idSchedule, int idAttraction, Time hourDebut, Time hourEnd, int reservedPlaces, int totalPlaces, Date date, String statut) {
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
}
