package Controller;

import DAO.AttractionDAO;
import Modele.Attraction;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminAttractionEditController implements Initializable {

    @FXML private ImageView quitButton, previousButton;
    @FXML private TextField nameField, typeField, maxCapacityField,
            basePriceField, durationField, imageNameField;
    @FXML private TextArea descriptionField;

    private final AttractionDAO attractionDAO = new AttractionDAO();
    private Attraction current;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1) Charge l’attraction sélectionnée
        current = Session.getSelectedAttraction();
        if (current != null) {
            nameField.setText(current.getName());
            typeField.setText(current.getType());
            maxCapacityField.setText(String.valueOf(current.getCapacity()));
            basePriceField.setText(String.valueOf(current.getPrice()));
            durationField.setText(String.valueOf(current.getDuration()));
            descriptionField.setText(current.getDescription());
            imageNameField.setText(current.getImagePath());
        }

        // 2) Images des boutons (facultatif)
        quitButton.setImage(new javafx.scene.image.Image(
                Objects.requireNonNull(
                        getClass().getResource("/imgs/QUIT_BUTTON.png")
                ).toExternalForm()
        ));
        previousButton.setImage(new javafx.scene.image.Image(
                Objects.requireNonNull(
                        getClass().getResource("/imgs/PREVIOUS_BUTTON.png")
                ).toExternalForm()
        ));
    }

    /** Enregistre les modifications et revient à la liste */
    @FXML
    private void saveAttraction(ActionEvent e) {
        // récupère et met à jour l’objet
        current.setName(         nameField.getText().trim());
        current.setType(        typeField.getText().trim());
        current.setCapacity(Integer.parseInt(maxCapacityField.getText().trim()));
        current.setPrice(   Integer.parseInt(basePriceField.getText().trim()));
        current.setDuration(    Integer.parseInt(durationField.getText().trim()));
        current.setDescription( descriptionField.getText().trim());
        current.setImagePath(   imageNameField.getText().trim());

        // persiste
        attractionDAO.editAttraction(current);

        // retour à la page d’admin des attractions
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource("/Vue/admin-attraction-view.fxml")
                    )
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "DOWN");
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
            Transition.slideTransition(MainApp.rootPane, view, 1000, "DOWN");
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