package Vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalculatriceJavaFX extends Application {

    private CustomDiagonalButton[] chiffres;
    private CustomDiagonalButton[] operateurs;
    private GridPane panelChiffres;
    private GridPane panelOperateurs;
    private TextField ecran;
    private float nb1;
    private float nb2;
    private float resultat;
    private String op;

    @Override
    public void start(Stage primaryStage) {
        // Initialisation des variables
        this.nb1 = 0;
        this.nb2 = 0;
        this.resultat = 0;
        this.op = "";

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Calculatrice Stylisée");
        primaryStage.setWidth(450);
        primaryStage.setHeight(600);

        // Création du layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #333333;");

        // Configuration de l'écran d'affichage
        this.ecran = new TextField("0");
        this.ecran.setEditable(false);
        this.ecran.setAlignment(Pos.CENTER_RIGHT);
        this.ecran.setFont(new Font("Arial", 30));
        this.ecran.setStyle("-fx-background-color: #EEEEEE; -fx-text-fill: #333333;");
        BorderPane.setMargin(this.ecran, new Insets(0, 0, 20, 0));
        root.setTop(this.ecran);

        // Configuration du panel des chiffres
        this.panelChiffres = new GridPane();
        this.panelChiffres.setHgap(10);
        this.panelChiffres.setVgap(10);

        // Création des boutons de chiffres stylisés
        this.chiffres = new CustomDiagonalButton[10];
        for (int i = 0; i < this.chiffres.length; i++) {
            final int chiffre = i;
            this.chiffres[i] = new CustomDiagonalButton(80, 80, "" + i, Color.BLUE, Color.YELLOW);

            this.chiffres[i].setOnMouseClicked(e -> {
                if (this.ecran.getText().equals("0")) {
                    this.ecran.setText("" + chiffre);
                } else {
                    this.ecran.setText(this.ecran.getText() + chiffre);
                }
            });
        }

        // Placement des boutons de chiffres (1-9)
        int index = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.panelChiffres.add(this.chiffres[index], col, row);
                index++;
            }
        }

        // Placement du bouton 0
        this.panelChiffres.add(this.chiffres[0], 1, 3);

        // Configuration du panel des opérateurs
        this.panelOperateurs = new GridPane();
        this.panelOperateurs.setHgap(10);
        this.panelOperateurs.setVgap(10);

        // Création des boutons d'opérateurs
        this.operateurs = new CustomDiagonalButton[5];
        String[] operateurSymboles = {"+", "-", "*", "/", "="};

        for (int i = 0; i < this.operateurs.length; i++) {
            final int operateurIndex = i;
            this.operateurs[i] = new CustomDiagonalButton(80, 80, operateurSymboles[i], Color.DARKBLUE, Color.ORANGE);

            if (i < 4) {  // Pour les opérateurs +, -, *, /
                this.operateurs[i].setOnMouseClicked(e -> {
                    this.nb1 = Float.parseFloat(this.ecran.getText());
                    this.op = operateurSymboles[operateurIndex];
                    this.ecran.setText("0");
                });
            } else {  // Pour l'opérateur =
                this.operateurs[i].setOnMouseClicked(e -> {
                    calculerResultat();
                });
            }

            this.panelOperateurs.add(this.operateurs[i], 0, i);
        }

        // Ajout des panneaux au layout principal
        root.setCenter(this.panelChiffres);
        root.setRight(this.panelOperateurs);
        BorderPane.setMargin(this.panelOperateurs, new Insets(0, 0, 0, 20));

        // Configuration des évènements clavier
        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);

        // Affichage de la fenêtre
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleKeyTyped(KeyEvent e) {
        char c = e.getCharacter().charAt(0);

        if (Character.isDigit(c)) {
            int number = Integer.parseInt(c + "");
            if (this.ecran.getText().equals("0")) {
                this.ecran.setText("" + number);
            } else {
                this.ecran.setText(this.ecran.getText() + number);
            }
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            this.nb1 = Float.parseFloat(this.ecran.getText());
            this.op = String.valueOf(c);
            this.ecran.setText("0");
        } else if (c == '=') {
            calculerResultat();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            calculerResultat();
        }
    }

    private void calculerResultat() {
        this.nb2 = Float.parseFloat(this.ecran.getText());

        switch (this.op) {
            case "+":
                this.resultat = this.nb1 + this.nb2;
                break;
            case "-":
                this.resultat = this.nb1 - this.nb2;
                break;
            case "*":
                this.resultat = this.nb1 * this.nb2;
                break;
            case "/":
                if (this.nb2 != 0) {
                    this.resultat = this.nb1 / this.nb2;
                } else {
                    this.ecran.setText("Erreur");
                    return;
                }
                break;
        }

        this.ecran.setText("" + this.resultat);
    }

    // Classe interne pour notre bouton personnalisé
    public class CustomDiagonalButton extends StackPane {

        public CustomDiagonalButton(double width, double height, String text, Color color1, Color color2) {
            setPrefSize(width, height);

            // Triangle supérieur
            Polygon triangleTop = new Polygon();
            triangleTop.getPoints().addAll(new Double[]{
                    0.0, 0.0,    // Point en haut à gauche
                    width, 0.0,  // Point en haut à droite
                    width, height  // Point en bas à droite
            });
            triangleTop.setFill(color1);

            // Triangle inférieur
            Polygon triangleBottom = new Polygon();
            triangleBottom.getPoints().addAll(new Double[]{
                    0.0, 0.0,    // Point en haut à gauche
                    0.0, height, // Point en bas à gauche
                    width, height  // Point en bas à droite
            });
            triangleBottom.setFill(color2);

            // Texte du bouton
            Text textNode = new Text(text);
            textNode.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            textNode.setFill(Color.WHITE);

            // Ajout des éléments au StackPane
            getChildren().addAll(triangleBottom, triangleTop, textNode);

            // Ajout d'effets au survol
            setOnMouseEntered(e -> {
                setScaleX(1.05);
                setScaleY(1.05);
                setCursor(javafx.scene.Cursor.HAND);
            });

            setOnMouseExited(e -> {
                setScaleX(1.0);
                setScaleY(1.0);
            });

            // Ajout d'effet au clic
            setOnMousePressed(e -> {
                setTranslateY(2);
            });

            setOnMouseReleased(e -> {
                setTranslateY(0);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}