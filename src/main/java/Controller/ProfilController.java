package Controller;

import Modele.Client;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class ProfilController {
    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;
    @FXML public Label attractionName;
    @FXML public ImageView attractionImg;
    @FXML public TextArea attractionDescription;
    @FXML public Label attractionType;
    @FXML public Label attractionPrice;
    @FXML public Label attractionDuration;
    @FXML public Button bookingButton;

    @FXML public void initialize(Client client) {
        try {
            previousButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
    }
}
