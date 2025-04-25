package Controller;

import Modele.Attraction;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import java.awt.*;


public class InformationController {
    @FXML public ImageView img;
    @FXML public Label attractionName;
    @FXML public ImageView attractionImg;
    @FXML public TextArea attractionDescription;
    @FXML public Label attractionType;
    @FXML public Label attractionPrice;
    @FXML public Label attractionDuration;
    @FXML public Button bookingButton;

    @FXML public void initialize(Attraction attraction) {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/CALENDAR.png").toExternalForm());
        img.setImage(image);

        setupInformation(attraction);
    }

    public void setupInformation (Attraction attraction) {
        attractionName.setText(attraction.getName());

        Image imageAttraction = new Image(getClass().getResource(attraction.getImagePath()).toExternalForm());
        attractionImg.setImage(imageAttraction);

        attractionDescription.setText(attraction.getDescription());
        attractionType.setText("Type de manège : " + attraction.getType());
        attractionPrice.setText("Prix d'un tour/personne : " + String.valueOf(attraction.getPrice()) + "€");
        attractionDuration.setText("Durée d'un tour : " + String.valueOf(attraction.getDuration()) + " minutes");
    }

}