package app.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuController {
    @FXML
    private AnchorPane scenePane;
    @FXML
    public ImageView guide;
    @FXML
    private ImageView intoSearch;
    @FXML
    private ImageView intoTranslate;
    @FXML
    private ImageView Out;
    @FXML
    private ImageView intoGame;
    @FXML
    private ImageView intoHistory;
    @FXML
    private ImageView intoSave;
    @FXML
    private TextField SearchingBar;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void intoSearch(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/FXML/Search.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoTranslate() {
        System.out.println("Translate");
    }

    public void intoGame() {
        System.out.println("Game");
    }

    public void intoSave() {
        System.out.println("Save");
    }

    public void intoHistory() {
        System.out.println("History");
    }

    public void intoGuide() {
        System.out.println("Guide");
    }

    public String getWord() {
        return SearchingBar.getText();
    }

    public void intoWord(KeyEvent event) {
       if (event.getCode() == KeyCode.ENTER) {
           System.out.println(SearchingBar.getText()+"\n");
       }
    }

    public void intoOut() {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }
}
