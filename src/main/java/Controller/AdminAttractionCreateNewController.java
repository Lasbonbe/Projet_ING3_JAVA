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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller de la page d'ajout d'une nouvelle attraction.
 * Permet de créer une nouvelle attraction.
 */
public class AdminAttractionCreateNewController implements Initializable {
    @FXML private ImageView quitButton;
    @FXML private ImageView previousButton;

    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField maxCapacityField;
    @FXML private TextField basePriceField;
    @FXML private TextField durationField;
    @FXML private TextArea descriptionField;
    @FXML private TextField imageNameField;

    private final AttractionDAO attractionDAO = new AttractionDAO();

    /** Initialisation de la vue
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));
        setNumericOnly(durationField);
        setNumericOnly(basePriceField);
        setNumericOnly(maxCapacityField);
    }

    /** Enregistre la nouvelle attraction et revient à la liste
     *
     * @param e ActionEvent - bah c'est l'event
     */
    @FXML
    private void saveAttraction(ActionEvent e) {
        Attraction a = getAttraction();
        attractionDAO.addAttraction(a);

        // Transition retour
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource("/Vue/admin-attraction-view.fxml")
                    )
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Crée une nouvelle attraction à partir des champs du FXML
     * @return Attraction
     */
    private Attraction getAttraction() {
        String nom  = nameField.getText().trim();
        String type = typeField.getText().trim();
        int cap     = Integer.parseInt(maxCapacityField.getText().trim());
        int price   = Integer.parseInt(basePriceField.getText().trim());
        int dur     = Integer.parseInt(durationField.getText().trim());
        String desc = descriptionField.getText().trim();
        String img  = imageNameField.getText().trim();

        // Création et persistance
        Attraction a = new Attraction(0, nom, type, cap, price, dur, desc, img);
        return a;
    }

    /** Méthode pour filtrer les champs de texte afin d'accepter uniquement les chiffres
     * @param textField Champ de texte à filtrer
     */
    private void setNumericOnly(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change : null;
        }));
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
