package app.Controller;

import app.API.GGTranslateAPI;
import app.API.TextToSpeech;
import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    @FXML
    public ListView wordList;
    @FXML
    public ImageView minimize;
    @FXML
    public AnchorPane scenePane;
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
    @FXML
    public ImageView intoSave;
    @FXML
    public Button paneButton;
    @FXML
    public TextArea textBar;
    @FXML
    public ImageView switchButton;
    @FXML
    public ImageView translateButton;
    @FXML
    public TextArea translationBar;
    @FXML
    public Label ViLeft;
    @FXML
    public Label EnRight;
    @FXML
    public Label ViRight;
    @FXML
    public Label EnLeft;
    @FXML
    public Label copyTranslation;
    @FXML
    public Label countWord;
    @FXML
    public ImageView translationSpeaker;
    @FXML
    public ImageView textSpeaker;
    @FXML
    public ImageView EnFlagRight;
    @FXML
    public ImageView ViFlagLeft;
    @FXML
    public ImageView ViFlagRight;
    @FXML
    public ImageView EnFlagLeft;

    //Nor
    private String translation = "";

    //Present
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Content Handlers
    public void intoSearch(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
        Parent root = loader.load();
        ((SearchController) loader.getController()).GoSearch();

        //Switch scene to SearchScene
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

    @FXML
    public void intoGame(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
        Parent root = loader.load();

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

    public void backHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
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

    public void switchLanguage() {
        GGTranslateAPI.isEnglish = !GGTranslateAPI.isEnglish;
        if (GGTranslateAPI.isEnglish) {
            EnLeft.setVisible(true);
            EnFlagLeft.setVisible(true);
            ViRight.setVisible(true);
            ViFlagRight.setVisible(true);
            EnRight.setVisible(false);
            EnFlagRight.setVisible(false);
            ViLeft.setVisible(false);
            ViFlagLeft.setVisible(false);
        } else {
            EnLeft.setVisible(false);
            EnFlagLeft.setVisible(false);
            ViRight.setVisible(false);
            ViFlagRight.setVisible(false);
            EnRight.setVisible(true);
            EnFlagRight.setVisible(true);
            ViLeft.setVisible(true);
            ViFlagLeft.setVisible(true);
        }
        translate();
    }

    public void copyTranslation() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(translation);
        clipboard.setContents(strSel, null);
    }

    public void countWord() {
        countWord.setText(Integer.toString(textBar.getText().length()) + "/2000");
    }

    public void translate() {
        translationBar.setText(GGTranslateAPI.translate(textBar.getText()));
        translation = translationBar.getText();
    }

    public void speakText() {
        TextToSpeech.SpeakEnglish = GGTranslateAPI.isEnglish;
        TextToSpeech.playVoice(textBar.getText());
    }

    public void speakTranslation() {
        TextToSpeech.SpeakEnglish = !GGTranslateAPI.isEnglish;
        TextToSpeech.playVoice(translationBar.getText());
    }

    //Supporting methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }

    public void outSearch() {
        wordList.setVisible(false);
    }

    public void doNothing() {
        translationBar.setText(translation);
    }
}
