package lk.ijse.gdse72.styleclothesleyeredarchitecture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/WelcomeForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dashboard page");
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/icon/Shopping Icon.png"))
        );
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}