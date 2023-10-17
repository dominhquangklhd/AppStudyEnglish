package app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
            Scene scene = new Scene(root);

            stage.setWidth(1200);
            stage.setHeight(735);
            stage.setResizable(false);
            stage.setScene (scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
