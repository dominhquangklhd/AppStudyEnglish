package app.Controller;

import app.Main;
import app.Model.DictionaryManagement;
import app.Model.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    //FXML
    @FXML
    public AnchorPane scenePane;
    @FXML
    public Label label_1;
    @FXML
    public Label label_2;
    @FXML
    public Label label_3;
    @FXML
    public Label label_4;
    @FXML
    public Label label_5;
    @FXML
    public Label label_6;
    @FXML
    public Label label_7;
    @FXML
    public Label label_8;
    @FXML
    public Label label_9;
    @FXML
    public Label label_10;
    @FXML
    public Label label_11;
    @FXML
    public Label label_12;
    @FXML
    public Label label_13;
    @FXML
    public Label label_14;
    @FXML
    public Label label_15;
    @FXML
    public Label label_16;
    @FXML
    public ImageView intoHistory;
    @FXML
    public TextField SearchingBar;
    @FXML
    public ImageView home;
    @FXML
    public ImageView out;
    @FXML
    public ImageView intoSearch;

    //Nor

    //Present
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Content Handlers
    public void intoSearch(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Search.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
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

    public void reSearchWord(MouseEvent event) throws  IOException {
        Label tmp = (Label) event.getSource();
        Word w = new Word();
        for (int i = 0; i < Main.dictionaryManagement.HistorySize; i++) {
            if (tmp.getText().equals(Main.dictionaryManagement.wordHistoryList.get(i).getWordTarget())) {
                w = Main.dictionaryManagement.wordHistoryList.get(i);
                break;
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
        Parent root = loader.load();
        ((SearchController) loader.getController()).setWord(w);
        ((SearchController) loader.getController()).getWordTarget().setText(w.getWordTarget());
        ((SearchController) loader.getController()).getWordExplain().setText(w.getWordExplain());
        Main.dictionaryManagement.addWordtoHistory(w);
        ((SearchController) loader.getController()).WordFoundSet();

        //Switch scene to SearchScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void backHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
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

    public void StartHistory() {
        for (int i = 0; i < Main.dictionaryManagement.HistorySize; i++) {
            label(i).setText(Main.dictionaryManagement.wordHistoryList.get(i).getWordTarget());
        }
    }

    public Label label(int i) {
        return switch (i) {
            case 0 -> label_1;
            case 1 -> label_2;
            case 2 -> label_3;
            case 3 -> label_4;
            case 4 -> label_5;
            case 5 -> label_6;
            case 6 -> label_7;
            case 7 -> label_8;
            case 8 -> label_9;
            case 9 -> label_10;
            case 10 -> label_11;
            case 11 -> label_12;
            case 12 -> label_13;
            case 13 -> label_14;
            case 14 -> label_15;
            default -> label_16;
        };
    }
}
