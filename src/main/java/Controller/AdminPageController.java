package Controller;

import DAO.ClientDAO;
import DAO.ReservationDAO;

import Modele.User;

import Vue.MainApp;
import Vue.Transition;
import com.jcraft.jsch.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer>    colId;
    @FXML private TableColumn<User, String>     colNom;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, Integer> colReservations;

    @FXML private ImageView quitButton;
    @FXML private ImageView nextButton;
    @FXML private ImageView previousButton;

    @FXML private TextArea consoleOutput;
    @FXML private ImageView backgroundImage;

    private ChannelShell shellChannel;
    private OutputStream  sshStdIn;

    /**
     * Initialisation de la page d'administration.
     **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colNom.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getFirstName();
            String lastName = cellData.getValue().getLastName();
            return new SimpleStringProperty(firstName + " " + lastName.toUpperCase());
        });
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        usersTable.setItems(FXCollections.observableArrayList(new ClientDAO().getAllClient()));
        ReservationDAO reservationDAO = new ReservationDAO();
        colReservations.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getUserID();
            int count  = reservationDAO.countReservationsByClient(userId);
            return new SimpleIntegerProperty(count).asObject();
        });

        Image img = new Image(getClass().getResource("/imgs/ADMIN_PANEL.png").toExternalForm());
        Image nextImg = new Image(getClass().getResource("/imgs/NEXT_BUTTON.png").toExternalForm());
        Image quitImg = new Image(getClass().getResource("/imgs/QUIT_BUTTON.png").toExternalForm());

        Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Medium.ttf"), 20);
        backgroundImage.setImage(img);
        quitButton.setImage(quitImg);
        nextButton.setImage(nextImg);
        previousButton.setImage(new Image(getClass().getResource("/imgs/PREVIOUS_BUTTON.png").toExternalForm()));

        consoleOutput.setEditable(true);
        consoleOutput.requestFocus();
        consoleOutput.addEventFilter(KeyEvent.KEY_TYPED, this::handleTyped);
        consoleOutput.addEventFilter(KeyEvent.KEY_PRESSED, this::handleSpecialKeys);
        consoleOutput.setStyle("-fx-font-family: 'JetBrains Mono Medium'; -fx-font-size: 12; -fx-text-fill: #ffffff; -fx-border-color: white;-fx-border-radius: 5;-fx-background-color: black; -fx-control-inner-background: black; -fx-font-weight: bold;");
        consoleOutput.setPrefHeight(350);
        consoleOutput.setMaxHeight(350);
        consoleOutput.setPrefWidth(1000);

        usersTable.setPrefHeight(750);
        usersTable.setMaxHeight(750);
        usersTable.setPrefWidth(525);
        usersTable.setMaxWidth(525);

        initSSH();
    }
    /**
     * Initialisation de la connexion SSH.
     **/
    private void initSSH() {
        try {
            JSch jsch = new JSch();
            String pwd = new String(Files.readAllBytes(new File("src/main/resources/imgs/cert.txt").toPath()));

            Session session = jsch.getSession("azureuser", "72.145.14.178", 22);
            session.setPassword(pwd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30_000);

            ChannelShell shellChannel = (ChannelShell) session.openChannel("shell");
            shellChannel.setPty(true);
            shellChannel.setPtyType("vt102");

            sshStdIn = shellChannel.getOutputStream();
            InputStream sshStdOut = shellChannel.getInputStream();

            shellChannel.connect();

            Thread reader = getThread(sshStdOut);
            reader.start();

        } catch (Exception e) {
            e.printStackTrace();
            consoleOutput.appendText("[Erreur SSH] " + e.getMessage() + "\n");
        }
    }
    /**
     * Thread pour lire la sortie du shell SSH.
     * params :
     **/
    private Thread getThread(InputStream sshStdOut) {
        Thread reader = new Thread(() -> {
            byte[] buf = new byte[1024];
            int len;
            try {
                while ((len = sshStdOut.read(buf)) != -1) {
                    String txt = new String(buf, 0, len);
                    Platform.runLater(() -> {
                        consoleOutput.appendText(txt);
                        consoleOutput.positionCaret(consoleOutput.getText().length());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        reader.setDaemon(true);
        return reader;
    }

    /**
     * Capture des caractères “normaux” (lettres, chiffres, espaces…) et envoi au serveur.
     */
    private void handleTyped(KeyEvent evt) {
        String ch = evt.getCharacter();
        if (ch.length() > 0 && ch.charAt(0) != 8 /*ignore backspace ici*/) {
            try {
                sshStdIn.write(ch.getBytes());
                sshStdIn.flush();
            } catch (IOException ignored) {}
        }
        evt.consume();
    }

    /**
     * Capture des touches spéciales : ENTER, BACK_SPACE, flèches…
     * - BACK_SPACE supprime le dernier caractère dans la TextArea,
     *   puis envoie 0x08 au serveur.
     * - Tout ça en gros pour gérer le terminal et ces ENCULES de caractères spéciaux
     */
    private void handleSpecialKeys(KeyEvent evt) {
        try {
            KeyCode code = evt.getCode();

            if (code == KeyCode.ENTER) {
                sshStdIn.write('\r');
                evt.consume();
            }
            else if (code == KeyCode.BACK_SPACE) {
                Platform.runLater(() -> {
                    int pos = consoleOutput.getCaretPosition();
                    if (pos > 0) {
                        consoleOutput.deleteText(pos - 1, pos);
                    }
                });
                sshStdIn.write(0x08);
                evt.consume();
            }
            else if (code == KeyCode.UP) {
                sshStdIn.write(new byte[]{0x1b, 0x5b, 0x41}); // ESC [ A
                evt.consume();
            }
            else if (code == KeyCode.DOWN) {
                sshStdIn.write(new byte[]{0x1b, 0x5b, 0x42}); // ESC [ B
                evt.consume();
            }
            else if (code == KeyCode.RIGHT) {
                sshStdIn.write(new byte[]{0x1b, 0x5b, 0x43}); // ESC [ C
                evt.consume();
            }
            else if (code == KeyCode.LEFT) {
                sshStdIn.write(new byte[]{0x1b, 0x5b, 0x44}); // ESC [ D
                evt.consume();
            }
            sshStdIn.flush();

        } catch (IOException ignored) {}
    }

    /**
     * Bouton QUIT
     **/
    @FXML
    void logoutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vue/login-view.fxml"));
            Parent loginView = loader.load();
            Transition.slideTransition(MainApp.rootPane, loginView, 1000, "DOWN");
        } catch (IOException exception) {
            System.out.println("Erreur lors du chargement de la vue : " + exception.getMessage());
        }
    }

    /**
     * Bouton NEXT, fait le lien ADMIN-VIEW -> ATTRACTIONS-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML
    private void nextClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-attraction-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "LEFT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bouton PREVIOUS, fait le lien ADMIN-VIEW -> USERS-VIEW.
     *
     * @param event Bah c'est l'event
     */
    @FXML private void previousClick(ActionEvent event) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vue/admin-user-view.fxml"))
            );
            Transition.slideTransition(MainApp.rootPane, view, 1000, "RIGHT");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
