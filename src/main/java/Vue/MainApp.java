package Vue;

import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/Vue/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        LoginController controller = fxmlLoader.getController();
        System.out.println(controller.img);  // Should not print 'null'

        // Correct the image path
        //Image image = new Image(new File("src/main/resources/imgs/img.png").toURI().toString());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/img.png")));
        ImageView im = new ImageView();
        im.setImage(image);
        primaryStage.getIcons().add(image);

        ImageView displayview = new ImageView();
        displayview.setImage(image);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}