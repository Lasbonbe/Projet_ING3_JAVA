package DAO;

import Modele.Attraction;
import Vue.VueAttraction;

import java.util.ArrayList;

public class DEBUG_TestDAO {
    public static void main(String[] args) {
        AttractionDao attractionDao = new AttractionDao();
        VueAttraction vueAttraction = new VueAttraction();

        ArrayList<Attraction> attractions = attractionDao.getAllAttractions();

        vueAttraction.displayAttractionList(attractions);
        Attraction deleteAttraction = new Attraction(9,"Test",10,10,10);
        attractionDao.deleteAttraction(deleteAttraction);

    }
}
