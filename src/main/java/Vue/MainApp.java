package Vue;

import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/Vue/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        Group root = new Group();

        Image icon = new Image(new File("@/imgs/img.png").toURI().toString());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}