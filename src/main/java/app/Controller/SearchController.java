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

public class SearchController implements Initializable {
    //FXML
    @FXML
    public ImageView minimize;
    @FXML
    public ImageView home;
    @FXML
    public AnchorPane scenePane;
    @FXML
    public ImageView out;
    @FXML
    public Label wordTarget;
    @FXML
    public WebView wordExplain;
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
    @FXML
    public ImageView UNsavedIcon;
    @FXML
    public ImageView savedIcon;
    @FXML
    public ImageView intoSave;
    @FXML
    public Label GoSearchLabel;
    @FXML
    public ImageView GoSearchPic;
    @FXML
    public ListView wordList;
    @FXML
    public Button paneButton;
    @FXML
    public ImageView intoGame;
    @FXML
    public ImageView intoTranslate;
    @FXML
    public ImageView speaker;
    @FXML
    public Label changeToCam;
    @FXML
    public WebView cambrigde;
    @FXML
    public Line linkLine;

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


    //Action handlers
    public void speakWord() {
        TextToSpeech.SpeakEnglish = Main.databaseConnection.englishWord;
        TextToSpeech.playVoice(wordTarget.getText());
    }

    public void backHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void saveOrUnsaved () throws IOException {
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

    @FXML
    public void intoGame(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoWord(KeyEvent event) throws IOException {
        ObservableList<String> items = FXCollections.observableArrayList();

        //lập ra danh sách các từ gợi ý mỗi khi từ trên SearchingBar(TestField) thay đổi.
        SearchingBar.textProperty().addListener((observable, oldValue, newValue) -> {
            wordList.getItems().clear();
            Main.trie.resetWordList();

            SearchingBar.setText(newValue);
            Main.trie.search(SearchingBar.getText().toLowerCase());

            wordList.setVisible(!Main.trie.getWordsBySearching().isEmpty());

            items.addAll(Main.trie.getWordsBySearching());
            wordList.setItems(items);
        });

        // lập ra sự kiện khi mình click chuột vào worldList(ListView).
        wordList.setOnMouseClicked(MouseEvent -> {
            String selectedWord = (String) wordList.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                SearchingBar.setText(selectedWord);
            }

            //wordList.setVisible(false);
        });

        if (event.getCode() == KeyCode.DOWN
                && wordList.getSelectionModel().getSelectedIndex() < wordList.getItems().size() - 1) {
            // Move selection down
            if (!wordList.getSelectionModel().isEmpty()) {
                wordList.getSelectionModel().selectNext();
            } else {
                wordList.getSelectionModel().selectFirst();
            }
        }

        if (event.getCode() == KeyCode.UP && wordList.getSelectionModel().getSelectedIndex() > 0) {
            // Move selection up
            wordList.getSelectionModel().selectPrevious();
        }

        if (event.getCode() == KeyCode.ENTER) {
            if (!wordList.getSelectionModel().isEmpty()) {
                SearchingBar.setText((String) wordList.getSelectionModel().getSelectedItem());
            }
            wordList.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
            Parent root = loader.load();
            ((SearchController) loader.getController()).getWordTarget().setText(SearchingBar.getText());
            ((SearchController) loader.getController()).StartSearching();

            //Switch scene to SearchScene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene (scene);
            stage.show();
        }
    }

    public void intoHistory(MouseEvent event) throws IOException {
        Main.dictionaryManagement.recentHistoryPage = 1;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/History.fxml"));
        Parent root = loader.load();
        ((HistoryController) loader.getController()).StartHistory();

        //Switch scene to HistoryScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoTranslate(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Translate.fxml"));
        Parent root = loader.load();

        //Switch scene to HistoryScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void intoSave(MouseEvent event) throws IOException {
        Main.dictionaryManagement.recentSavePage = 1;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Save.fxml"));
        Parent root = loader.load();
        ((SaveController) loader.getController()).StartSave();

        //Switch scene to HistoryScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    public void minimizeStage(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void intoOut() throws IOException {
        Main.dictionaryManagement.historyExportToFile();
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void intoCambrigde() {
        WebEngine webEngine = cambrigde.getEngine();
        webEngine.load("https://dictionary.cambridge.org/vi/dictionary/english/" + wordTarget.getText());
        cambrigde.setVisible(true);
    }

    //Supporting methods
    public void StartSearching() {
        wordTarget.setText(wordTarget.getText().toLowerCase());
        String explainWord = Main.databaseConnection.findWordInDatabase(wordTarget.getText());
        if (explainWord.isEmpty()) {
            CryIfCannotFindWord();
        } else {
            WebEngine webEngine = wordExplain.getEngine();
            webEngine.loadContent(explainWord);
            //VBox root = new VBox(wordExplain);

            // Apply CSS to change the background color
            //root.setStyle("-fx-background-color: lightblue;");
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
        cambrigde.setVisible(false);
        WordNotFoundIMG.setVisible(false);
        WordNotFoundNoti.setVisible(false);
        Advice.setVisible(false);
    }

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

    public void outSearch() {
        wordList.setVisible(false);
    }
}
