package Vue;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Calendar extends Application {


    @Override
    public void start(Stage stage) {
        BoutonCalendar button1 = new BoutonCalendar("20");
        stage.setTitle("Calendar");
        stage.setScene(button1.getScene());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
