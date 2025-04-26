// File: src/main/java/Controller/AdminPromotionCreateNewController.java
package Controller;

import DAO.AttractionDAO;
import DAO.PromotionDAO;
import Modele.Attraction;
import Modele.Promotion;
import Vue.MainApp;
import Vue.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class AdminPromotionCreateNewController implements Initializable {

    // Champs de base
    @FXML private TextField nameField;
    @FXML private TextField percentageField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private CheckBox permanentCheckBox;

    // Jours de la semaine
    @FXML private CheckBox mondayCB;
    @FXML private CheckBox tuesdayCB;
    @FXML private CheckBox wednesdayCB;
    @FXML private CheckBox thursdayCB;
    @FXML private CheckBox fridayCB;
    @FXML private CheckBox saturdayCB;
    @FXML private CheckBox sundayCB;

    // Liste des attractions
    @FXML private ListView<String> attractionsList;

    // Navigation
    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;

    private final PromotionDAO promotionDAO   = new PromotionDAO();
    private final AttractionDAO attractionDAO = new AttractionDAO();

    // Stocker toutes les attractions
    private ObservableList<Attraction> allAttractions;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // Filtrage du champ pour n'accepter que des chiffres
        UnaryOperator<Change> filter = change -> change.getControlNewText().matches("\\d*") ? change : null;
        percentageField.setTextFormatter(new TextFormatter<>(filter));

        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));

        // Récupération de toutes les attractions à afficher
        allAttractions = FXCollections.observableArrayList(attractionDAO.getAllAttractions());
        ObservableList<String> attractionNames = FXCollections.observableArrayList();
        for (Attraction attraction : allAttractions) {
            attractionNames.add(attraction.getName());
        }
        attractionsList.setItems(attractionNames);
        attractionsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void savePromotion(ActionEvent event) {
        try {
            validateInputs();
            Promotion promo = getPromotion();
            int promoId = promotionDAO.addPromotionReturnID(promo);
            updatePromotionAssociations(promoId);
            navigateToView("/Vue/admin-promotion-view.fxml", 1000, "DOWN");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de validation", e.getMessage());
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la sauvegarde de la promotion");
            e.printStackTrace();
        }
    }

    private void validateInputs() {
        if (nameField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la promotion est requis");
        }
        if (descriptionArea.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("La description de la promotion est requise");
        }
        if (attractionsList.getSelectionModel().getSelectedItems().isEmpty()) {
            throw new IllegalArgumentException("Au moins une attraction doit être sélectionnée");
        }
        if (percentageField.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Le pourcentage de la promotion est requis");
        }
    }

    private void updatePromotionAssociations(int promoId) {
        List<String> days = new ArrayList<>();
        if (mondayCB.isSelected())    days.add("Monday");
        if (tuesdayCB.isSelected())   days.add("Tuesday");
        if (wednesdayCB.isSelected()) days.add("Wednesday");
        if (thursdayCB.isSelected())  days.add("Thursday");
        if (fridayCB.isSelected())    days.add("Friday");
        if (saturdayCB.isSelected())  days.add("Saturday");
        if (sundayCB.isSelected())    days.add("Sunday");
        promotionDAO.clearPromotionDays(promoId);
        promotionDAO.addPromotionDays(promoId, days);

        List<Integer> attIds = attractionsList.getSelectionModel()
                .getSelectedItems()
                .stream()
                .map(attractionName -> {
                    for (Attraction attraction : allAttractions) {
                        if (attraction.getName().equals(attractionName)) {
                            return attraction.getAttractionID();
                        }
                    }
                    return null;
                })
                .collect(Collectors.toList());
        promotionDAO.clearPromotionAttractions(promoId);
        promotionDAO.addPromotionAttractions(promoId, attIds);
    }

    private Promotion getPromotion() {
        String name = nameField.getText().trim();
        String desc = descriptionArea.getText().trim();
        int percentage = Integer.parseInt(percentageField.getText().trim());
        LocalDate dtStart = startDatePicker.getValue();
        LocalDate dtEnd = endDatePicker.getValue();
        boolean permanent = permanentCheckBox.isSelected();

        if (!permanent) {
            if (dtStart == null || dtEnd == null) {
                throw new IllegalArgumentException("Les dates de début et de fin sont requises pour une promotion non permanente");
            }
            if (dtStart.isAfter(dtEnd)) {
                throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
            }
        }
        return new Promotion(0, name, percentage, desc, dtStart, dtEnd, permanent);
    }

    private void navigateToView(String fxmlPath, int duration, String direction) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Transition.slideTransition(MainApp.rootPane, view, duration, direction);
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void previousClick(ActionEvent event) {
        try {
            navigateToView("/Vue/admin-promotion-view.fxml", 1000, "DOWN");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void logoutClick(ActionEvent event) {
        try {
            navigateToView("/Vue/login-view.fxml", 1000, "DOWN");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}