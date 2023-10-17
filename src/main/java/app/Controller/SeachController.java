package app.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SeachController {
    @FXML
    public ImageView home;
    @FXML
    public AnchorPane scenePane;
    @FXML
    public ImageView out;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void backHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/FXML/Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoOut() {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

}
