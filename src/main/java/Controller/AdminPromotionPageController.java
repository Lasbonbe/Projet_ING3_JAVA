package Controller;

import DAO.PromotionDAO;
import Modele.Promotion;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.beans.property.SimpleBooleanProperty;
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
 * Controller de la page d'administration des promotions.
 * Permet de gérer les promotions (ajout, modification, suppression).
 */
public class AdminPromotionPageController implements Initializable {

    @FXML private TableView<Promotion> promotionsTable;
    @FXML private TableColumn<Promotion, Integer> colID;
    @FXML private TableColumn<Promotion, String>  colNom;
    @FXML private TableColumn<Promotion, String>  colDescription;
    @FXML private TableColumn<Promotion, String>  colDebut;
    @FXML private TableColumn<Promotion, String>  colFin;
    @FXML private TableColumn<Promotion, Boolean> colPermanente;

    @FXML private ImageView backgroundImage;
    @FXML private ImageView quitButton;
    @FXML private ImageView nextButton;
    @FXML private ImageView previousButton;

    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private final PromotionDAO attractionDAO = new PromotionDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // colonnes
        colID.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getId()).asObject()
        );
        colNom.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName())
        );
        colDescription.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDescription())
        );

        colDebut.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getStartDate() != null
                                ? c.getValue().getStartDate().toString()
                                : "—"
                )
        );

        colFin.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getEndDate() != null
                                ? c.getValue().getEndDate().toString()
                                : "—"
                )
        );
        colPermanente.setCellValueFactory(c ->
                new SimpleBooleanProperty(c.getValue().isPermanent())
        );
        System.out.println(colPermanente.getCellData(0));


        List<Promotion> list = attractionDAO.getAllPromotions();
        promotionsTable.setItems(FXCollections.observableArrayList(list));

        backgroundImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/ADMIN_PROMOTION_PANEL.png")).toExternalForm()));
        nextButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/NEXT_BUTTON.png")).toExternalForm()));
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));

        promotionsTable.setPrefHeight(700);
        promotionsTable.setMaxHeight(700);
        promotionsTable.setMinHeight(700);
        promotionsTable.setPrefWidth(1500);
        promotionsTable.setMaxWidth(1500);
        promotionsTable.setMinWidth(1500);
    }

    /** Ajout d’une nouvelle promotion */
    @FXML
    private void addPromotion(ActionEvent e) {
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource("/Vue/promotion-add-view.fxml")
                    )
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Modification de la promotion sélectionnée */
    @FXML
    private void editPromotion(ActionEvent e) {
        Promotion sel = promotionsTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Vue/promotion-edit-view.fxml")
            );
            Parent view = loader.load();
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Suppression de la promotion sélectionnée */
    @FXML
    private void deletePromotion(ActionEvent e) {
        Promotion sel = promotionsTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        attractionDAO.deletePromotionByID(sel.getId());
        promotionsTable.getItems().remove(sel);
    }


    /**
     * Bouton NEXT, fait le lien PROMOTION-VIEW -> USER-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML
    private void nextClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-user-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "LEFT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bouton PREVIOUS, fait le lien PROMOTION-VIEW -> ATTRACTION-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML private void previousClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-attraction-view.fxml"))
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
