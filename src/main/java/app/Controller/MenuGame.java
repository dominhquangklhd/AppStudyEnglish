package app.Controller;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuGame extends BaseController {
    //FXML
    //Nor
    @FXML
    private Button buttonGameImg;

    private FXMLLoader GameIMGloader = new FXMLLoader(getClass().getResource("/FXML/GameIMG.fxml"));
    private FXMLLoader MCloader = new FXMLLoader(getClass().getResource("/FXML/MultipleChoiceGame.fxml"));

    public void intoGameImg(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = GameIMGloader.load();

        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        Main.scene.setRoot(Main.root);
        //stage.setScene (Main.scene);
        //stage.show();
    }

    public void intoMCGame(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = MCloader.load();

        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        Main.scene.setRoot(Main.root);
        //stage.setScene (Main.scene);
        //stage.show();
    }

}

