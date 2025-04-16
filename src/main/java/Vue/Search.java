package Vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Search extends Application {

    private TextField searchField;
    private CheckBox checkBoxCocher;
    private CheckBox checkBoxCocher2;

    @Override
    public void start(Stage stage) {
        try {
            try {
                Font.loadFont(getClass().getResourceAsStream("/Vue/font/Bungee-Regular.ttf"), 12);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.setMaximized(true);

            VBox searchArea = createSearchArea();
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

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createSearchArea() {
        // Création du champ de recherche
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        // Rectangle décoratif pour le champ de recherche
        Rectangle searchRect = new Rectangle(400, 50);
        searchRect.setStroke(Color.GRAY);
        searchRect.setFill(Color.WHITE);
        searchRect.setStrokeWidth(2);

        // Champ de texte pour la recherche
        searchField = new TextField();
        searchField.setPrefSize(400, 50); // Même taille que le rectangle
        searchField.setPromptText("Rechercher une attraction");
        searchField.setFont(Font.font("Bungee", 16));
        searchField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Superposition du rectangle et du champ texte avec un BorderPane
        BorderPane searchOverlay = new BorderPane();
        searchOverlay.setCenter(searchField);

        // Créer un conteneur principal pour maintenir l'alignement
        StackPane searchContainer = new StackPane();
        searchContainer.getChildren().addAll(searchRect, searchOverlay);

        // Ajouter à votre layout
        hBox.getChildren().add(searchContainer);


        // Création des cases à cocher
        HBox checkBoxContainer = new HBox(50);
        checkBoxContainer.setAlignment(Pos.CENTER);
        checkBoxContainer.setPadding(new Insets(20, 0, 0, 0));

        // Première case à cocher (en italique et plus petite)
        VBox checkBox1Box = new VBox(5);
        checkBoxCocher = new CheckBox();
        Label label1 = new Label("Pour enfants");
        label1.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label1.setTextFill(Color.web("white"));
        checkBox1Box.getChildren().addAll(checkBoxCocher, label1);
        checkBox1Box.setAlignment(Pos.CENTER);

        // Deuxième case à cocher (normal et plus grande)
        VBox checkBox2Box = new VBox(5);
        checkBoxCocher2 = new CheckBox();
        Label label2 = new Label("Place disponible");
        label2.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label2.setTextFill(Color.web("white"));
        checkBox2Box.getChildren().addAll(checkBoxCocher2, label2);
        checkBox2Box.setAlignment(Pos.CENTER);

        // Ajout des cases à cocher au conteneur
        checkBoxContainer.getChildren().addAll(checkBox1Box, checkBox2Box);

        // Conteneur principal
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().addAll(hBox, checkBoxContainer);

        // Ajout des gestionnaires d'événements
        setupEventHandlers();

        return mainContainer;
    }

    private void setupEventHandlers() {
        // Événement sur le champ de recherche
        searchField.setOnAction(e -> {
            String searchText = searchField.getText();
            System.out.println("Recherche effectuée: " + searchText);
            // Vous pouvez ajouter ici la logique de recherche
        });

        // Événements sur les cases à cocher
        checkBoxCocher.selectedProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Première case à cocher: " + newVal);
            // Logique pour la première case à cocher
        });

        checkBoxCocher2.selectedProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Deuxième case à cocher: " + newVal);
            // Logique pour la deuxième case à cocher
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}