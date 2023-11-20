package app.Controller;

import app.API.TextToSpeech;
import app.Main;
import app.Model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import app.DB_Connection.*;

public class SearchController extends BaseController implements Initializable {
    //FXML
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
    @FXML
    public ImageView UNsavedIcon;
    @FXML
    public ImageView savedIcon;
    @FXML
    public Label GoSearchLabel;
    @FXML
    public ImageView GoSearchPic;
    @FXML
    public ImageView speaker;
    @FXML
    public Label changeToCam;
    @FXML
    public WebView cambrigde;
    @FXML
    public Line linkLine;

    //Content handlers
    public Label getWordTarget() {
        return wordTarget;
    }


    //Action handlers

    /**
     * Uses API to speak the word that user searched.
     */
    public void speakWord() {
        TextToSpeech.SpeakEnglish = Main.databaseConnection.englishWord;
        TextToSpeech.playVoice(wordTarget.getText());
    }

    /**
     * Sets word from unsaved to saved and vice versa.
     */
    public void saveOrUnsaved () {
        if (Main.databaseConnection.isSaved(wordTarget.getText())) {
            UNsavedIcon.setVisible(true);
            savedIcon.setVisible(false);
            Main.dictionaryManagement.wordSavedList.remove(wordTarget.getText());
            Main.databaseConnection.setUnSave(wordTarget.getText());

            Main.dictionaryManagement.decreaseSavePage();
        } else {
            UNsavedIcon.setVisible(false);
            savedIcon.setVisible(true);
            Main.dictionaryManagement.wordSavedList.add(0,wordTarget.getText());
            Main.databaseConnection.setSave(wordTarget.getText());

            Main.dictionaryManagement.increaseSavePage();
        }
    }

    /**
     * Opens Cambridge dictionary on the internet.
     */
    public void intoCambridge() {
        WebEngine webEngine = cambrigde.getEngine();
        webEngine.load("https://dictionary.cambridge.org/vi/dictionary/english/" + wordTarget.getText());
        cambrigde.setVisible(true);
    }

    /**
     * Sets up before searching
     * 1/ Checks if the word is included in the dictionary
     * 2/ Checks if the word is saved or not ( for design purposes )
     * 3/ Sets the word and its meanings on scene
     * 4/ Adds the word to History list
     */
    //Supporting methods
    public void StartSearching() {
        wordTarget.setText(wordTarget.getText().toLowerCase());
        String explainWord = Main.databaseConnection.findWordInDatabase(wordTarget.getText());
        if (explainWord.isEmpty()) {
            CryIfCannotFindWord();
        } else {
            WebEngine webEngine = wordExplain.getEngine();
            webEngine.loadContent(explainWord);

            int preSize = Main.dictionaryManagement.wordHistoryList.size();
            Main.dictionaryManagement.wordHistoryList.remove(wordTarget.getText());
            if (preSize != Main.dictionaryManagement.wordHistoryList.size()) {
                Main.dictionaryManagement.decreaseHistoryPage();
            }
            Main.dictionaryManagement.addWordtoHistory(wordTarget.getText());
            Main.dictionaryManagement.increaseHistoryPage();
            if (Main.databaseConnection.isSaved(wordTarget.getText())) {
                savedIcon.setVisible(true);
                UNsavedIcon.setVisible(false);
            } else {
                savedIcon.setVisible(false);
                UNsavedIcon.setVisible(true);
            }
            WordFoundSet();
        }
    }

    /**
     * Presents the Cannot Find Word pane when the words searched is not included in the dictionary.
     */
    public void CryIfCannotFindWord() {
        scenePane1.setVisible(false);
        scenePane2.setVisible(false);
        WordNotFoundIMG.setVisible(true);
        WordNotFoundNoti.setVisible(true);
        Advice.setVisible(true);
    }

    /**
     * Presents the Word Found pane when the words searched is included in the dictionary.
     */
    public void WordFoundSet() {
        scenePane1.setVisible(true);
        scenePane2.setVisible(true);
        cambrigde.setVisible(false);
        WordNotFoundIMG.setVisible(false);
        WordNotFoundNoti.setVisible(false);
        Advice.setVisible(false);
    }

    /**
     * Sets the Go Search pane when starting Searching pane.
     */
    public void GoSearch() {
        scenePane1.setVisible(false);
        scenePane2.setVisible(false);
        GoSearchLabel.setVisible(true);
        GoSearchPic.setVisible(true);
        WordNotFoundIMG.setVisible(false);
        WordNotFoundNoti.setVisible(false);
        Advice.setVisible(false);
    }

    public void DragInLink() {
        linkLine.setVisible(true);
    }

    public void DragOutLink() {
        linkLine.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }

}
