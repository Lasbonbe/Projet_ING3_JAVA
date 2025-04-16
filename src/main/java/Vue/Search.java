package Vue;

import Controller.SearchController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Search extends Application {

    private TextField searchField;
    private CheckBox checkBoxAvailable;
    private ComboBox<String> comboBoxPrice;
    private ComboBox<String> comboBoxDuration;

    @Override
    public void start(Stage stage) {
        try {
            try {
                Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.setMaximized(true);

            VBox searchArea = getSearchArea();
            BorderPane borderPane = new BorderPane();
            borderPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            borderPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            searchArea.setAlignment(Pos.CENTER);
            searchArea.setPadding(new Insets(30, 0, 0, 0));
            borderPane.setStyle("-fx-background-color: black;");
            borderPane.setCenter(searchArea);
            Scene scene = new Scene(borderPane, Color.WHITE);
            stage.setTitle("Rechercher");
            stage.setScene(scene);
            stage.show();

            new SearchController(this);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private VBox getSearchArea() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        Rectangle searchRectangle = new Rectangle(400, 50);
        searchRectangle.setStroke(Color.GRAY);
        searchRectangle.setFill(Color.WHITE);
        searchRectangle.setStrokeWidth(2);

        searchField = new TextField();
        searchField.setPrefSize(400, 50);
        searchField.setPromptText("Rechercher une attraction");
        searchField.setFont(Font.font("JetBrains Mono ExtraBold", 16));
        searchField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        StackPane typingArea = new StackPane();
        typingArea.getChildren().addAll(searchRectangle, searchField);

        hBox.getChildren().add(typingArea);

        // Boîte qui contiendra toutes les éléments de filtres (checkbox, listes déroulantes etc...)
        HBox allFilter = new HBox(50);
        allFilter.setAlignment(Pos.CENTER);
        allFilter.setPadding(new Insets(20, 0, 0, 0));

        //Création de la checkbox
        HBox checkBoxAndLabel = new HBox(5);
        checkBoxAvailable = new CheckBox();
        Label checkBoxLabel = new Label("Places disponibles");
        checkBoxLabel.setFont(Font.font("JetBrains Mono ExtraBold", FontWeight.NORMAL, 14));
        checkBoxLabel.setTextFill(Color.web("white"));
        checkBoxAndLabel.getChildren().addAll(checkBoxAvailable, checkBoxLabel);
        checkBoxAndLabel.setAlignment(Pos.CENTER_LEFT);

        //Création des listes déroulantes
        comboBoxPrice = new ComboBox<>();
        comboBoxPrice.getItems().addAll("Choisir un prix", "1€-5€, parfait pour les creuvards", "6€-10€, Ah ouai t'es aisé", "+10€, Excuse nous Elon");
        comboBoxPrice.setValue("Choisir un prix");

        comboBoxDuration = new ComboBox<>();
        comboBoxDuration.getItems().addAll("Choisir une durée de manège", "1-5 minutes", "6-10 minutes", "+10 minutes");
        comboBoxDuration.setValue("Choisir une durée");

        //Ajout de tous ces filtres
        allFilter.getChildren().addAll(checkBoxAndLabel, comboBoxPrice, comboBoxDuration);

        VBox searchArea = new VBox(20);
        searchArea.setAlignment(Pos.CENTER);
        searchArea.getChildren().addAll(hBox, allFilter);
        return searchArea;
    }

    public TextField getSearchField() { return searchField; }

    public CheckBox getCheckBoxAvailable() { return checkBoxAvailable; }

    public ComboBox<String> getComboBoxPrice() { return comboBoxPrice; }

    public ComboBox<String> getComboBoxDuration() { return comboBoxDuration; }

    public static void main(String[] args) {
        launch(args);
    }
}