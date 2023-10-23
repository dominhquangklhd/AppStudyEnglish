package app.Controller;

import app.Main;
import app.Model.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import app.DB_Connection.*;

public class SearchController implements Initializable {
    //FXML
    @FXML
    public ImageView home;
    @FXML
    public AnchorPane scenePane;
    @FXML
    public ImageView out;
    @FXML
    public Label wordTarget;
    @FXML
    public Label wordExplain;
    @FXML
    public TextField SearchingBar;
    @FXML
    public AnchorPane scenePane1;
    @FXML
    public AnchorPane scenePane2;
    @FXML
    public Label WordNotFoundNoti;
    @FXML
    public ImageView WordNotFoundIMG;
    @FXML
    public ImageView intoHistory;
    @FXML
    public Label Advice;

    //Nor
    private Word word = new Word();

    //Present
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Content handlers

    public void setWord(Word new_word) {
        word = new_word;
    }

    public Word getWord() {
        return word;
    }

    public Label getWordTarget() {
        return wordTarget;
    }

    public Label getWordExplain() {
        return wordExplain;
    }

    //Action handlers

    public void backHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoWord(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            word.setWordTarget(SearchingBar.getText());
            wordTarget.setText(word.getWordTarget());

            StartSearching();
        }
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

    public void intoOut() {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    //Supporting methods
    /*public void StartSearching() {
        boolean has = false;
        for (Word w : Main.dictionaryManagement.dictionary.words) {
            if (w.getWordTarget().equals(wordTarget.getText())) {
                word.setWordExplain("In Vietnamese: " + w.getWordExplain());
                wordExplain.setText(word.getWordExplain());
                has = true;
                Main.dictionaryManagement.addWordtoHistory(word);
                WordFoundSet();
            }
        }
        if (!has) {
            CryIfCannotFindWord();
        }
    }*/

    public void StartSearching() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createConnection();
        String explainWord = databaseConnection.findWordInDatabase(wordTarget.getText());
        if (explainWord.isEmpty()) {
            CryIfCannotFindWord();
        } else {
            wordExplain.setText(explainWord);
            Main.dictionaryManagement.addWordtoHistory(word);
            WordFoundSet();
        }
    }

    public void CryIfCannotFindWord() {
        scenePane1.setVisible(false);
        scenePane2.setVisible(false);
        WordNotFoundIMG.setVisible(true);
        WordNotFoundNoti.setVisible(true);
        Advice.setVisible(true);
    }

    public void WordFoundSet() {
        scenePane1.setVisible(true);
        scenePane2.setVisible(true);
        WordNotFoundIMG.setVisible(false);
        WordNotFoundNoti.setVisible(false);
        Advice.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }
}
