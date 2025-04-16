package Controller;

import Modele.Attraction;
import Vue.Search;
import DAO.AttractionDAO;
import Vue.VueAttraction;

import java.util.ArrayList;


public class SearchController {
    private Search searchView;
    private AttractionDAO attractionDAO = new AttractionDAO();
    private VueAttraction vueAttraction = new VueAttraction();


    public SearchController(Search searchView) {
        this.searchView = searchView;
        setupListeners();
    }

    public void setupListeners() {
        searchView.getSearchField().setOnAction(e -> {
            performSearch();
        });

        searchView.getCheckBoxAvailable().selectedProperty().addListener((obs, oldVal, newVal) -> {
            performSearch();
        });

        searchView.getComboBoxPrice().setOnAction(e -> {
            performSearch();
        });

        searchView.getComboBoxDuration().setOnAction(e -> {
            performSearch();
        });
    }

    private void performSearch() {
        String searchText = searchView.getSearchField().getText();
        boolean placesDispo = searchView.getCheckBoxAvailable().isSelected();
        String prix = searchView.getComboBoxPrice().getValue();
        String duree = searchView.getComboBoxDuration().getValue();

        ArrayList<Attraction> resultats = attractionDAO.searchAttractions(searchText, placesDispo, prix, duree);
        vueAttraction.displayAttractionList(resultats);
    }
}
