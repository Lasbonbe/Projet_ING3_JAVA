package Vue.Calendar;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Classe représentant un bouton pour un jour impossible dans le calendrier.
 * Hérite de la classe ButtonFreeCalendar.
 */
public class ButtonImpossibleDay extends ButtonFreeCalendar {

    /**
     * Méthode constructeur de la classe ButtonImpossibleDay.
     *
     * @param value La valeur du bouton.
     */
    public ButtonImpossibleDay(String value) {
        super(value);
        this.root = new StackPane();
        this.button = new Button(value);

        this.button.getStyleClass().add("button-impossible-day");
        this.button.setOnAction(null);

        Rectangle buttonBackground = new Rectangle(80, 80);
        buttonBackground.getStyleClass().add("button-impossible-day-background");

        this.root.getStyleClass().add("Vue/css/calendar.css");
        this.root.getChildren().add(buttonBackground);
        this.root.getChildren().add(this.button);
    }

    public StackPane getRoot() {
        return this.root;
    }
}
