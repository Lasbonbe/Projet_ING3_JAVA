// File: src/main/java/Controller/AdminPromotionEditController.java
package Controller;

import DAO.AttractionDAO;
import DAO.PromotionDAO;
import Modele.Attraction;
import Modele.Promotion;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class AdminPromotionEditController implements Initializable {

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

    // Liste des attractions affichées par leur nom
    @FXML private ListView<String> attractionsList;

    // Navigation
    @FXML private ImageView previousButton;
    @FXML private ImageView quitButton;

    private final PromotionDAO promotionDAO   = new PromotionDAO();
    private final AttractionDAO attractionDAO = new AttractionDAO();
    private Promotion current;

    // Contient toutes les attractions
    private ObservableList<Attraction> allAttractions;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // Filtrer le champ percentage pour n'accepter que des chiffres
        UnaryOperator<Change> filter = change -> change.getControlNewText().matches("\\d*") ? change : null;
        percentageField.setTextFormatter(new TextFormatter<>(filter));

        // Charger les boutons d'images
        quitButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()));
        previousButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()));

        // Récupérer toutes les attractions et afficher leurs noms
        allAttractions = FXCollections.observableArrayList(attractionDAO.getAllAttractions());
        ObservableList<String> attractionNames = FXCollections.observableArrayList();
        for (Attraction attraction : allAttractions) {
            attractionNames.add(attraction.getName());
        }
        attractionsList.setItems(attractionNames);
        attractionsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Charger la promotion sélectionnée dans la session et pré-remplir les champs
        current = Session.getSelectedPromotion();
        if (current != null) {
            nameField.setText(current.getName());
            percentageField.setText(String.valueOf(current.getPercentage()));
            descriptionArea.setText(current.getDescription());
            if (current.getStartDate() != null) {
                startDatePicker.setValue(current.getStartDate());
            }
            if (current.getEndDate() != null) {
                endDatePicker.setValue(current.getEndDate());
            }
            permanentCheckBox.setSelected(current.isPermanent());

            // Cocher les jours de la semaine associés à la promotion
            List<String> days = promotionDAO.getPromotionDays(current.getId());
            mondayCB.setSelected(days.contains("Monday"));
            tuesdayCB.setSelected(days.contains("Tuesday"));
            wednesdayCB.setSelected(days.contains("Wednesday"));
            thursdayCB.setSelected(days.contains("Thursday"));
            fridayCB.setSelected(days.contains("Friday"));
            saturdayCB.setSelected(days.contains("Saturday"));
            sundayCB.setSelected(days.contains("Sunday"));
            // Cocher les attractions associées à la promotion
            List<Integer> attIds = promotionDAO.getPromotionAttractions(current.getId());
            for (Attraction attraction : allAttractions) {
                if (attIds.contains(attraction.getAttractionID())) {
                    attractionsList.getSelectionModel().select(attraction.getName());
                }
            }
        }
    }

    @FXML
    private void savePromotion(ActionEvent event) {
        try {
            validatePromotionInput();
            updatePromotionFromInput();
            updatePromotionAssociations();
            navigateToView("/Vue/admin-promotion-view.fxml", 1000, "DOWN");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de la promotion");
            e.printStackTrace();
        }
    }

    private void validatePromotionInput() {
        if (nameField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Le nom de la promotion est requis");
        if (descriptionArea.getText().trim().isEmpty())
            throw new IllegalArgumentException("La description de la promotion est requise");
        if (percentageField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Le pourcentage de la promotion est requis");
        if (!permanentCheckBox.isSelected()) {
            LocalDate dtStart = startDatePicker.getValue();
            LocalDate dtEnd = endDatePicker.getValue();
            if (dtStart == null || dtEnd == null)
                throw new IllegalArgumentException("Les dates de début et de fin sont requises pour une promotion non permanente");
            if (dtStart.isAfter(dtEnd))
                throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        } else {
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        }
        if (attractionsList.getSelectionModel().getSelectedItems().isEmpty())
            throw new IllegalArgumentException("Au moins une attraction doit être sélectionnée");
    }

    private void updatePromotionFromInput() {
        current.setName(nameField.getText().trim());
        current.setPercentage(Integer.parseInt(percentageField.getText().trim()));
        current.setDescription(descriptionArea.getText().trim());
        current.setStartDate(startDatePicker.getValue());
        current.setEndDate(endDatePicker.getValue());
        current.setPermanent(permanentCheckBox.isSelected());
        promotionDAO.editPromotion(current);
    }

    private void updatePromotionAssociations() {
        // Mettre à jour les jours
        List<String> days = new ArrayList<>();
        if (mondayCB.isSelected())    days.add("Monday");
        if (tuesdayCB.isSelected())   days.add("Tuesday");
        if (wednesdayCB.isSelected()) days.add("Wednesday");
        if (thursdayCB.isSelected())  days.add("Thursday");
        if (fridayCB.isSelected())    days.add("Friday");
        if (saturdayCB.isSelected())  days.add("Saturday");
        if (sundayCB.isSelected())    days.add("Sunday");
        promotionDAO.clearPromotionDays(current.getId());
        promotionDAO.addPromotionDays(current.getId(), days);

        // Mettre à jour les attractions associées à partir des noms sélectionnés
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
        promotionDAO.clearPromotionAttractions(current.getId());
        promotionDAO.addPromotionAttractions(current.getId(), attIds);
    }

    private void navigateToView(String fxmlPath, int duration, String direction) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Transition.slideTransition(MainApp.rootPane, view, duration, direction);
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
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
            navigateToView("/Vue/login-view.fxml", 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}