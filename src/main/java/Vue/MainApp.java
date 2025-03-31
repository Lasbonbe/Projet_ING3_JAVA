package Vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/Vue/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);

        // Correct the image path
        Image image = new Image(new File("src/main/resources/imgs/img.png").toURI().toString());
        ImageView im = new ImageView();
        im.setImage(image);
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}