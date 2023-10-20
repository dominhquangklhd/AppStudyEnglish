package app.Controller;

import app.Main;
import app.Model.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    //FXML
    @FXML
    private AnchorPane scenePane;
    @FXML
    private ImageView guide;
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
    //Nor

    //Present
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void intoSearch(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Search.fxml"));
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

    public void intoHistory(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/History.fxml"));
        Parent root = loader.load();
        ((HistoryController) loader.getController()).StartHistory();

        //Switch scene to HistoryScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoGuide() {
        System.out.println("Guide");
    }

    public String getWord() {
        return SearchingBar.getText();
    }

    public void intoWord(KeyEvent event) throws IOException {
       if (event.getCode() == KeyCode.ENTER) {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
           Parent root = loader.load();
           ((SearchController) loader.getController()).getWord().setWordTarget(SearchingBar.getText());
           ((SearchController) loader.getController()).getWordTarget().setText(SearchingBar.getText());
           ((SearchController) loader.getController()).StartSearching();

           //Switch scene to SearchScene
           stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           scene = new Scene(root);
           stage.setScene (scene);
           stage.show();
       }
    }

    public void intoOut() {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    //Supporting methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }
}
