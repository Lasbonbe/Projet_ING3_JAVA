package Vue;

import Controller.JavaFXPaneException;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Transition {

    /**
     * Effectue une transition de glissement (slide) entre la vue actuellement affichée
     * dans le conteneur et une nouvelle vue, en fonction de la direction spécifiée.
     *
     * @param container      La zone (Pane) qui contient les vues.
     * @param newView        La nouvelle vue à afficher.
     * @param durationMillis La durée de la transition en millisecondes.
     * @param direction      La direction de la transition (UP, DOWN, LEFT ou RIGHT).
     */
    public static void slideTransition(Pane container, Parent newView, double durationMillis, String direction) {
        if (container == null || newView == null || direction == null) {
            return;
        }

        try {

            String dir = direction.toUpperCase();
            Parent oldView = (Parent) container.getChildren().getFirst();

            // Initialisation et création des transitions selon la direction
            TranslateTransition slideOut;
            TranslateTransition slideIn;

            switch (dir) {
                case "UP":
                    newView.setTranslateY(container.getHeight());
                    slideOut = new TranslateTransition(Duration.millis(durationMillis), oldView);
                    slideOut.setToY(-container.getHeight());
                    slideIn = new TranslateTransition(Duration.millis(durationMillis), newView);
                    slideIn.setToY(0);
                    break;
                case "DOWN":
                    newView.setTranslateY(-container.getHeight());
                    slideOut = new TranslateTransition(Duration.millis(durationMillis), oldView);
                    slideOut.setToY(container.getHeight());
                    slideIn = new TranslateTransition(Duration.millis(durationMillis), newView);
                    slideIn.setToY(0);
                    break;
                case "LEFT":
                    newView.setTranslateX(container.getWidth());
                    slideOut = new TranslateTransition(Duration.millis(durationMillis), oldView);
                    slideOut.setToX(-container.getWidth());
                    slideIn = new TranslateTransition(Duration.millis(durationMillis), newView);
                    slideIn.setToX(0);
                    break;
                case "RIGHT":
                    newView.setTranslateX(-container.getWidth());
                    slideOut = new TranslateTransition(Duration.millis(durationMillis), oldView);
                    slideOut.setToX(container.getWidth());
                    slideIn = new TranslateTransition(Duration.millis(durationMillis), newView);
                    slideIn.setToX(0);
                    break;
                default:
                    System.out.println("Direction de transition inconnue : " + direction);
                    return;
            }

            slideOut.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
            slideIn.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

            container.getChildren().add(newView);

            slideOut.setOnFinished(event -> container.getChildren().remove(oldView));

            slideOut.play();
            slideIn.play();

        } catch (TransitionJavaFxException e) {
            System.out.println("Erreur de transition : " + e.getMessage());
        } catch (JavaFXPaneException e) {
            System.out.println("Erreur de JavaFXPane : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
    }
}