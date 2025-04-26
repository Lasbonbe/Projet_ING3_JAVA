package Controller;

import DAO.ClientDAO;
import Modele.Client;
import Vue.VueClient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;


/**
 * Controller de la page de recherche de clients.
 * Permet de rechercher des clients dans la base de données.
 */
public class ClientSearchController {
    @FXML public ImageView img;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> comboBoxAge;
    @FXML private ComboBox<String> comboBoxOrder;
    @FXML private ComboBox<String> comboBoxOrderTime;
    @FXML private Button searchButton;
    @FXML private VBox searchArea;
    ArrayList<Client> searchResults = null;

    private final ClientDAO clientDAO = new ClientDAO();
    private final VueClient vueClient = new VueClient();


    /**
     * Initialise la page de recherche de clients.
     * Charge l'image d'arrière-plan et configure les listeners pour les champs de recherche.
     */
    @FXML public void initialize() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
        img.setImage(image);
        setupListeners();
    }

    /**
     * Configure les listeners pour les champs de recherche et le bouton de recherche.
     */
    public void setupListeners() {
        searchField.setOnAction(e -> {
            performSearch();
        });

        searchButton.setOnAction(e -> {
            performSearch();
        });
    }

    /**
     * Effectue la recherche de clients en fonction des critères spécifiés.
     * Affiche les résultats de la recherche dans la vue client.
     */
    private void performSearch() {
        String searchPrompt = searchField.getText();
        String filterAge = comboBoxAge.getValue();
        String filterOrderTime = comboBoxOrderTime.getValue();

        searchResults = clientDAO.searchClient(searchPrompt, filterAge, filterOrderTime);
        if (searchResults.isEmpty()) {
            System.out.println("Aucun client ne correspond à votre recherche");
        } else {
            vueClient.displayClientList(searchResults);
        }
    }
}