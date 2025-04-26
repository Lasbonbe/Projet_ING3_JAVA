package Controller;

import DAO.AttractionDAO;
import Modele.Attraction;
import Modele.Session;
import Modele.Client;
import Vue.Calendar.CalendarView;
import Vue.MainApp;
import Vue.Transition;
import Vue.ProfilView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private ImageView img;
    @FXML private ImageView searchButton;
    @FXML private ImageView quitButton;
    @FXML private TextField searchField;
    @FXML private GridPane cardsGrid;

    private List<Attraction> attractionsList;
    private final List<String> colors = Arrays.asList(
            "#ff5c5c", "#ffffff", "#ff8c00",
            "#5ce1e6", "#7cff5c", "#5c75ff"
    );
    // Map pour stocker la couleur associée à chaque type d'attraction
    private final Map<String, String> typeColorMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            img.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/HOME_VIEW.png")).toExternalForm()
            ));
            searchButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/ACCOUNT_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }

        AttractionDAO dao = new AttractionDAO();
        attractionsList = dao.getAllAttractions();

        int colorIndex = 0;
        for (Attraction a : attractionsList) {
            String type = a.getType();
            if (!typeColorMap.containsKey(type)) {
                typeColorMap.put(type, colors.get(colorIndex % colors.size()));
                colorIndex++;
            }
        }

        updateGrid(attractionsList);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String txt = newVal.toLowerCase().trim();
            List<Attraction> filtered = attractionsList.stream()
                    .filter(a -> a.getName().toLowerCase().contains(txt))
                    .collect(Collectors.toList());
            updateGrid(filtered);
        });

        searchField.getParent().toFront();
        cardsGrid.toFront();
        quitButton.toFront();
        searchButton.toFront();
    }

    private void updateGrid(List<Attraction> list) {
        cardsGrid.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            Attraction a = list.get(i);
            Pane card = new Pane();
            card.setPrefSize(220, 120);
            String color = typeColorMap.get(a.getType());
            card.setStyle(
                    "-fx-border-color: " + color + ";" +
                            "-fx-border-width: 3;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-pref-width: 400;"
            );

            Label label = new Label(a.getName());
            label.setStyle(
                    "-fx-font-family: 'JetBrains Mono SemiBold';" +
                            "-fx-font-size: 18px;" +
                            "-fx-text-fill: " + color + ";"
            );
            label.setLayoutX(10);
            label.setLayoutY(10);
            card.getChildren().add(label);

            Button reserveButton = new Button("Reserver");
            reserveButton.setLayoutX(10);
            reserveButton.setLayoutY(40);
            reserveButton.setOnAction(e -> reserve(a));
            reserveButton.setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-text-fill: black;" +
                            "-fx-font-family: 'JetBrains Mono SemiBold';" +
                            "-fx-font-size: 14px;" +
                            "-fx-border-radius: 5;" +
                            "-fx-background-radius: 5;"
            );
            card.getChildren().add(reserveButton);

            Button moreInfoButton = new Button("Plus d'infos");
            moreInfoButton.setLayoutX(100);
            moreInfoButton.setLayoutY(40);
            moreInfoButton.setOnAction(e -> moreInfo(a));
            moreInfoButton.setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-text-fill: black;" +
                            "-fx-font-family: 'JetBrains Mono SemiBold';" +
                            "-fx-font-size: 14px;" +
                            "-fx-border-radius: 5;" +
                            "-fx-background-radius: 5;"
            );
            card.getChildren().add(moreInfoButton);

            cardsGrid.add(card, i % 4, i / 4);
        }
    }

    private void reserve(Attraction a) {
        System.out.println("Réservation pour : " + a.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/calendar-view.fxml"));
            Parent calendarView = loader.load();

            CalendarController controller = loader.getController();
            controller.initialize(a);

            Transition.slideTransition(MainApp.rootPane, calendarView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue du calendrier : " + e.getMessage());
        }
    }

    private void moreInfo(Attraction a) {
        System.out.println("Plus d'infos sur : " + a.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/information-view.fxml"));
            Parent informationView = loader.load();

            InformationController controller = loader.getController();
            controller.initialize(a);

            Transition.slideTransition(MainApp.rootPane, informationView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue des informations : " + e.getMessage());
        }
    }

    @FXML
    private void logoutClick() {
        Session.setUser(null);
        System.out.println("Session utilisateur réinitialisée.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "RIGHT");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }

    @FXML
    public void userIconClick(Client c) {
        System.out.println("Profil de : " + c.getLastName() + " " + c.getFirstName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/profil-view.fxml"));
            Parent profilView = loader.load();

            ProfilController controller = loader.getController();
            controller.initialize(c);

            Transition.slideTransition(MainApp.rootPane, profilView, 1500, "LEFT");

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page de profil : " + e.getMessage());
        }
    }
}