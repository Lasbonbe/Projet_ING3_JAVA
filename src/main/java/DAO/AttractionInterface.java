package DAO;

import Modele.Attraction;
import java.util.ArrayList;

public interface AttractionInterface {
    public ArrayList<Attraction> getAllAttractions();

    public void addAttraction(Attraction attraction);
    public void deleteAttraction(Attraction attraction);
    public Attraction findAttraction(int attraction);
    public Attraction editAttraction(Attraction attraction);
}