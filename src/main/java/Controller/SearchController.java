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

    private final AttractionDAO attractionDAO = new AttractionDAO();
    private final VueAttraction vueAttraction = new VueAttraction();

    @FXML public void initialiaze() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
        img.setImage(image);
        setupListeners();
    }


    public void setupListeners () {
        searchField.setOnAction(e -> {
            performSearch();
        });

        checkBoxAvailable.selectedProperty().addListener((obs, oldVal, newVal) -> {
            performSearch();
        });

        comboBoxPrice.setOnAction(e -> {
            performSearch();
        });

        comboBoxDuration.setOnAction(e -> {
            performSearch();
        });
    }

    private void performSearch () {
        String searchText = searchField.getText();
        boolean placesDispo = checkBoxAvailable.isSelected();
        String prix = comboBoxPrice.getValue();
        String duree = comboBoxDuration.getValue();

        ArrayList<Attraction> resultats = attractionDAO.searchAttractions(searchText, placesDispo, prix, duree);
        vueAttraction.displayAttractionList(resultats);
    }



}
