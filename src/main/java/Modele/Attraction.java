package Modele;

public class Attraction {
    private int attractionID;
    private String name;
    private int place;

    public Attraction() {
        attractionID = 0;
        name = "Le Gigolo N'golo D Kante - The attraction";
        place = 10;
    }
    public Attraction(int attractionID, String name, int place) {
        this.attractionID = attractionID;
        this.name = name;
        this.place = place;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░
    public int getAttractionID() { return attractionID;}
    public String getName() { return name;}
    public int getPlace() { return place;}

    public void setAttractionID(int attractionID) { this.attractionID = attractionID;}
    public void setName(String name) { this.name = name;}
    public void setPlace(int place) { this.place = place;}
}
