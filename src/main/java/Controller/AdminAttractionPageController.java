package Controller;

import DAO.AttractionDAO;
import Modele.Attraction;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller de la page d'administration des attractions.
 * Permet de gérer les attractions (ajout, modification, suppression).
 */
public class AdminAttractionPageController implements Initializable {

    @FXML private TableView<Attraction> attractionsTable;
    @FXML private TableColumn<Attraction, Integer> colID;
    @FXML private TableColumn<Attraction, String>  colnom;
    @FXML private TableColumn<Attraction, String>  coltype;
    @FXML private TableColumn<Attraction, Integer> colmax_capacity;
    @FXML private TableColumn<Attraction, Integer> colbase_price;
    @FXML private TableColumn<Attraction, Integer> colduration;
    @FXML private TableColumn<Attraction, String>  coldescription;

    @FXML private ImageView quitButton;
    @FXML private ImageView nextButton;
    @FXML private ImageView previousButton;

    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private final AttractionDAO dao = new AttractionDAO();
    /**
     * Initialisation de la page d'administration des attractions, methode implementée de l'interface Initializable.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // 1) Config des colonnes
        colID.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getAttractionID()).asObject()
        );
        colnom.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName())
        );
        coltype.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getType())
        );
        colmax_capacity.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getCapacity()).asObject()
        );
        colbase_price.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getPrice()).asObject()
        );
        colduration.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getDuration()).asObject()
        );
        coldescription.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDescription())
        );

        List<Attraction> list = dao.getAllAttractions();
        attractionsTable.setItems(FXCollections.observableArrayList(list));

        nextButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/NEXT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));

        attractionsTable.setPrefHeight(600);
        attractionsTable.setMaxHeight(600);
        attractionsTable.setMinHeight(600);
        attractionsTable.setPrefWidth(1500);
        attractionsTable.setMaxWidth(1500);
        attractionsTable.setMinWidth(1500);
    }

    /**
     * Ouvre la page “Ajouter une attraction”
     * @param e l'event
     * */
    @FXML
    private void addAttraction(ActionEvent e) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-add-attraction-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "DOWN");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Ouvre la page “Modifier” pour l’attraction sélectionnée **/
    @FXML
    private void editAttraction(ActionEvent e) {
        Attraction sel = attractionsTable.getSelectionModel().getSelectedItem();
        Session.setSelectedAttraction(sel);
        if (sel == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Vue/admin-edit-attraction-view.fxml")
            );
            Parent view = loader.load();
            // (optionnel) passe “sel” au controller de la page d’édition
            Transition.slideTransition(MainApp.rootPane, view, 1000, "UP");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Supprime l’attraction sélectionnée et rafraîchit la table */
    @FXML
    private void deleteAttraction(ActionEvent e) {
        Attraction sel = attractionsTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        dao.deleteAttractionByID(sel.getAttractionID());
        attractionsTable.getItems().remove(sel);
    }

    /**
     * * Bouton NEXT, fait le lien ATTRACTION-VIEW -> PROMOTION-VIEW.
     * @param e bah c'est l'event
     */
    @FXML
    private void nextClick(ActionEvent e) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-promotion-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "LEFT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Bouton PREVIOUS, fait le lien ATTRACTION-VIEW -> ADMIN-VIEW.
     * @param e bah c'est l'event
     */
    @FXML private void previousClick(ActionEvent e) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    /** Bouton QUIT
     * @param  e ActionEvent - bah c'est l'event
     * */
    @FXML
    private void logoutClick(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "DOWN");
            Session.clearSession();
        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }
}
