package Controller;

import DAO.ClientDAO;
import Modele.Client;
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
 * Controller de la page d'administration des utilisateurs.
 * Permet de gérer les utilisateurs (ajout, modification, suppression).
 */
public class AdminUserPageController implements Initializable {
    @FXML
    private TableView<Client> usersTable;
    @FXML
    private TableColumn<Client, Integer> colID;
    @FXML
    private TableColumn<Client, String> colNom;
    @FXML
    private TableColumn<Client, String> colPrenom;
    @FXML
    private TableColumn<Client, String> colEmail;
    @FXML
    private TableColumn<Client, String> colDateNaissance;

    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView quitButton;
    @FXML
    private ImageView nextButton;
    @FXML
    private ImageView previousButton;

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private final ClientDAO clientDAO = new ClientDAO();

    /**
     * Initialisation de la page d'administration des utilisateurs, methode implementée de l'interface Initializable.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // 1) Config des colonnes
        colID.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getUserID()).asObject()
        );
        colNom.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getLastName())
        );
        colPrenom.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getLastName())
        );
        colEmail.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEmail())
        );
        colDateNaissance.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getBirthDate().toString())
        );

        List<Client> list = clientDAO.getAllClient();
        usersTable.setItems(FXCollections.observableArrayList(list));

        backgroundImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/ADMIN_USERS_PANEL.png")).toExternalForm()));
        nextButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/NEXT_BUTTON.png")).toExternalForm()));
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));

        usersTable.setPrefHeight(700);
        usersTable.setMaxHeight(700);
        usersTable.setMinHeight(700);
        usersTable.setPrefWidth(1500);
        usersTable.setMaxWidth(1500);
        usersTable.setMinWidth(1500);
    }


    /**
     * Methode qui gere le click sur le bouton "Modifier" de la page d'administration des utilisateurs.
     *
     * @param event Bah c'est un event
     */
    @FXML private void editUserButtonOnClick(ActionEvent event) {
        try {
            Client selectedUser = usersTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) return;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/user-edit-view.fxml"));
                Parent view = loader.load();
                Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode qui gere le click sur le bouton "Supprimer" de la page d'administration des utilisateurs.
     *
     * @param event
     */
    @FXML private void deleteUserButtonOnClick(ActionEvent event) {
        Client selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;
        clientDAO.deleteClientByID(selectedUser.getUserID());
        usersTable.getItems().remove(selectedUser);
    }

    /**
     * Bouton NEXT, fait le lien USER-VIEW -> RESERVATION-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML
    private void nextClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-reservation-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "LEFT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bouton PREVIOUS, fait le lien USER-VIEW -> PROMOTIONS-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML private void previousClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-promotion-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bouton QUIT.
     *
     * @param event Bah c'est l'event
     */
    @FXML private void logoutClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "DOWN");
        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

}