package Controller;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class InformationController {
    @FXML public ImageView img;

    @FXML public void initialize() {
        // Chargement de l'image pour l'ImageView
        Image image = new Image(getClass().getResource("/imgs/main.png").toExternalForm());
        img.setImage(image);
    }

}