package Controller;

import Modele.Attraction;
import DAO.AttractionDAO;
import Vue.VueAttraction;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;


public class SearchController {
    @FXML public ImageView img;
    @FXML private TextField searchField;
    @FXML private CheckBox checkBoxAvailable;
    @FXML private ComboBox<String> comboBoxPrice;
    @FXML private ComboBox<String> comboBoxDuration;
    @FXML private Button searchButton;
    @FXML private VBox searchArea;
    ArrayList<Attraction> searchResults = null;

    private final AttractionDAO attractionDAO = new AttractionDAO();
    private final VueAttraction vueAttraction = new VueAttraction();

    @FXML public void initialize() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
        img.setImage(image);
        setupListeners();
    }

    public void setupListeners () {
        searchField.setOnAction(e -> {
            performSearch();
        });

        searchButton.setOnAction(e -> {
            performSearch();
        });
    }

    private void performSearch () {
        String searchPrompt = searchField.getText();
        boolean placesAvailable = checkBoxAvailable.isSelected();
        String filterPrice = comboBoxPrice.getValue();
        String filterDuration = comboBoxDuration.getValue();

        searchResults = attractionDAO.searchAttractions(searchPrompt, placesAvailable, filterPrice, filterDuration);
        if ( searchResults.isEmpty()){
            System.out.println("Aucune attraction correspond à votre recherche");
        }
        else {
            vueAttraction.displayAttractionList(searchResults);
        }
    }


}
