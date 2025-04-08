package DAO;

import Modele.Administrator;
import Modele.Attraction;
import Modele.User;
import Vue.VueAttraction;
import Vue.VueUser;

import java.util.ArrayList;

public class DEBUG_TestDAO {
    public static void main(String[] args) {
        AttractionDao attractionDao = new AttractionDao();
        VueAttraction vueAttraction = new VueAttraction();

        ArrayList<Attraction> attractions = attractionDao.getAllAttractions();
        vueAttraction.displayAttractionList(attractions);

        Attraction findAttraction = attractionDao.findAttraction(1);
        vueAttraction.displayAttraction(findAttraction);


    }
}
