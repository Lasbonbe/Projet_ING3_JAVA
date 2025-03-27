package Modele;

public class Attraction {
    private int attractionID;
    private String attractionName;

    public Attraction() {
        attractionID = 0;
        attractionName = "Le Gigolo N'golo D Kante - The attraction";
    }
    public Attraction(int attractionID, String attractionName) {
        this.attractionID = attractionID;
        this.attractionName = attractionName;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░
    public int getAttractionID() { return attractionID;}
    public String getAttractionName() { return attractionName;}

    public void setAttractionID(int attractionID) { this.attractionID = attractionID;}
    public void setAttractionName(String attractionName) { this.attractionName = attractionName;}
}
