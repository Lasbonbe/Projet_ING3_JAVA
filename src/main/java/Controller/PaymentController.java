package Controller;

import DAO.PanierDAO;
import Modele.Attraction;
import Modele.Client;
import Modele.Schedule;
import Modele.Session;
import Vue.MainApp;
import Vue.Transition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller de la page de paiement.
 * Permet de gérer le paiement d'une réservation.
 */
public class PaymentController {
    @FXML private ImageView backButton;
    @FXML private ImageView quitButton;
    @FXML private Button ordersButton;
    @FXML private TextField titulaireField;
    @FXML private TextField numCarteField;
    @FXML private TextField dateExpField;
    @FXML private TextField CryptoField;
    @FXML private TextField adressField;
    private Client client;
    private Schedule schedule;
    private Attraction attraction;
    private boolean isUpdatingDateExp = false;

    /**
     * Initialisation de la vue de paiement.
     * Charge les images et initialise les champs de texte.
     */
    @FXML public void initialize() {
        try {
            backButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/PREVIOUS_BUTTON.png")).toExternalForm()
            ));
            quitButton.setImage(new Image(
                    Objects.requireNonNull(getClass().getResource("/imgs/QUIT_BUTTON.png")).toExternalForm()
            ));
        } catch (JavaFXImageException e) {
            System.err.println("Erreur au chargement des images : " + e.getMessage());
        }
    }

    /**
     * Méthode pour initialiser le paiement avec un client, une attraction et un calendrier.
     *
     * @param schedule  Le calendrier de la réservation.
     * @param attraction L'attraction réservée.
     * @param client    Le client effectuant la réservation.
     */
    public void setSchedule(Schedule schedule, Attraction attraction, Client client) {
        this.schedule = schedule;
        this.attraction = attraction;
        this.client = client;
        setupTextFields();
    }

    /**
     * Méthode pour initialiser les champs de texte.
     * Ajoute des écouteurs pour vérifier la validité des champs.
     */

    private void setupTextFields() {
        ordersButton.setDisable(true);
        ChangeListener<String> listener = (observable, oldValue, newValue) -> checkFieldsFull();

        titulaireField.textProperty().addListener(listener);

        numCarteField.textProperty().addListener(listener);
        numCarteField.textProperty().addListener((observable, oldValue, newValue) -> {
            String tempNumCarte = newValue.replaceAll("\\D", "");
            if (!newValue.equals(tempNumCarte)) {
                numCarteField.setText(tempNumCarte);
                return;
            }
            if (!isValidCard(tempNumCarte)) {
                ordersButton.setDisable(true);
            }
        });
        limitTextLength(numCarteField, 19);

        dateExpField.textProperty().addListener(listener);
        dateExpField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdatingDateExp) return;
            String digits = newValue.replaceAll("\\D", "");
            String formatted = digits;
            if (digits.length() >= 3) {
                formatted = digits.substring(0, 2) + "/" + digits.substring(2);
            }
            if (!newValue.equals(formatted)) {
                isUpdatingDateExp = true;
                dateExpField.setText(formatted);
                dateExpField.positionCaret(formatted.length());
                isUpdatingDateExp = false;
            }
        });
        limitTextLength(dateExpField, 5);

        CryptoField.textProperty().addListener(listener);
        CryptoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                CryptoField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        limitTextLength(CryptoField, 3);

        adressField.textProperty().addListener(listener);
    }

    /**
     * Méthode pour vérifier si le numéro de carte est valide.
     *
     * @param numCarte Le numéro de carte à vérifier.
     * @return true si le numéro de carte est valide, false sinon.
     */
    private boolean isValidCard(String numCarte) {
        boolean valid = false;
        if ((numCarte.startsWith("34") || numCarte.startsWith("37")) && numCarte.length() == 15) {
            valid = true;
        } else if (numCarte.length() == 16) {
            if (Integer.parseInt(numCarte.substring(0, 2)) >= 51 && (Integer.parseInt(numCarte.substring(0, 2)) <= 55)) {
                valid = true;
            }
            if (Integer.parseInt(numCarte.substring(0, 4)) >= 2221 && Integer.parseInt(numCarte.substring(0, 4)) <= 2720) {
                valid = true;
            }
            if (numCarte.startsWith("4")) {
                valid = true;
            }
        } else if (numCarte.startsWith("4") && (numCarte.length() == 13 || numCarte.length() == 19)) {
            valid = true;
        }
        return valid;
    }

    /**
     * Méthode pour vérifier si tous les champs sont remplis.
     * Active ou désactive le bouton de commande en fonction de la validité des champs.
     */
    private void checkFieldsFull() {
        boolean allFilled = !titulaireField.getText().trim().isEmpty() &&
                !numCarteField.getText().trim().isEmpty() &&
                !dateExpField.getText().trim().isEmpty() &&
                !CryptoField.getText().trim().isEmpty() &&
                !adressField.getText().trim().isEmpty();

        ordersButton.setDisable(!allFilled);
    }

    /**
     * Méthode pour limiter la longueur du texte dans un champ de texte.
     *
     * @param textField Le champ de texte à limiter.
     * @param maxLength La longueur maximale autorisée.
     */
    private void limitTextLength(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * QUIT BUTTON
     */
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

    /**
     * BACK BUTTON
     */
    @FXML
    private void backClick() {
        try {
            if (schedule != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/panier-view.fxml"));
                Parent calendarView = loader.load();

                PanierController controller = loader.getController();
                controller.setSchedule(schedule, attraction, client);

                Transition.slideTransition(MainApp.rootPane, calendarView, 1000, "RIGHT");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/panier-view.fxml"));
                Parent panierView = loader.load();

                PanierController controller = loader.getController();
                controller.setSchedule(client);

                Transition.slideTransition(MainApp.rootPane, panierView, 1000, "DOWN");
            }

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue de connexion : " + e.getMessage());
        }
    }

    /**
     * PAYMENT BUTTON
     */
    @FXML private void paymentClick() {
        PanierDAO panierDAO = new PanierDAO();
        panierDAO.payerPanier(client.getUserID());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paiement");
        alert.setHeaderText(null);
        alert.setContentText("Paiement effectué.");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

}

