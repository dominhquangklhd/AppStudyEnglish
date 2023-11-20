package app.Controller;

import app.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    //FXML
    @FXML
    public ImageView minimize;
    @FXML
    public ImageView home;
    @FXML
    public AnchorPane scenePane;
    @FXML
    public Pane guidePane;
    @FXML
    public ImageView out;
    @FXML
    public ImageView intoHistory;
    @FXML
    public ImageView intoSearch;
    @FXML
    public ImageView intoSave;
    @FXML
    public ImageView intoGame;
    @FXML
    public Label wordTarget;
    @FXML
    public WebView wordExplain;
    @FXML
    public TextField SearchingBar;
    @FXML
    public ListView wordList;
    @FXML
    public AnchorPane settingPane;
    @FXML
    public ImageView setting;
    @FXML
    public Button paneButton;
    @FXML
    public Label insertWord;
    @FXML
    public Label editWord;
    @FXML
    public Label deleteWord;

    //Nor
    public AnchorPane adjustPane;
    boolean settingLoaded = false;

    public FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("/FXML/Setting.fxml"));
    public FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
    public FXMLLoader saveLoader = new FXMLLoader(getClass().getResource("/FXML/Save.fxml"));
    public FXMLLoader transLoader = new FXMLLoader(getClass().getResource("/FXML/Translate.fxml"));
    public FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("/FXML/History.fxml"));
    public FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/FXML/Menu.fxml"));
    public FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));

    protected Stage stage;

    /**
     * Minimizes the window.
     *
     * @param event the event when user click the minimize button.
     */
    public void minimizeStage(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Presents a short guide to use this app.
     */
    public void intoGuide() {
        guidePane.setVisible(true);
        guidePane.toFront();
    }

    /**
     * Back to the main menu.
     *
     * @param event the event when user click the home button
     * @throws IOException when cannot load the FXMLLoader homeLoader
     */
    public void backHome(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = homeLoader.load();
        guidePane.setVisible(false);
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(Main.root);
        Main.scene.setRoot(Main.root);
        //stage.setScene(Main.scene);
        //stage.show();
    }

    /**
     * Closes the app.
     *
     * @throws IOException when cannot invoke the method historyExportToFile
     */
    public void intoOut() throws IOException {
        // Common code for exiting
        Main.dictionaryManagement.historyExportToFile();
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    /**
     * Switches the scene to Menu Game.
     *
     * @param event the event when user click the Game button
     * @throws IOException when cannot load the FXMLLoader gameLoader
     */
    public void intoGame(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = gameLoader.load();

        Main.scene.setRoot(Main.root);
    }

    /**
     * Switches the scene to History.
     * Supporting methods:
     * 1/ Set the recent Page of History list to 1
     *
     * @param event the event when user click the History button
     * @throws IOException when cannot load the FXMLLoader historyLoader
     */
    public void intoHistory(MouseEvent event) throws IOException {
        Main.dictionaryManagement.recentHistoryPage = 1;

        ((Pane) Main.root).getChildren().clear();
        Main.root = historyLoader.load();
        ((HistoryController) historyLoader.getController()).StartHistory();

        Main.scene.setRoot(Main.root);

    }

    /**
     * Switches the scene to Translate.
     *
     * @param event the event when user click the Translate button
     * @throws IOException when cannot load the FXMLLoader transLoader
     */
    public void intoTranslate(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = transLoader.load();

        Main.scene.setRoot(Main.root);

    }

    /**
     * Switches the scene to Search.
     * Supporting methods:
     * 1/ Check if the word exists in the dictionary.
     *
     * @param event the event when user click the Search button
     * @throws IOException when cannot load the FXMLLoader searchLoader
     */
    public void intoSearch(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = searchLoader.load();
        ((SearchController) searchLoader.getController()).GoSearch();

        //Switch scene to SearchScene
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(Main.root);
        Main.scene.setRoot(Main.root);
        //stage.setScene (Main.scene);
        //stage.show();
    }

    /**
     * Switches the scene to Save List.
     * Supporting methods:
     * 1/ Set the recent page of Save list and set words to labels.
     *
     * @param event the event when user click the Save button
     * @throws IOException when cannot load the FXMLLoader saveLoader
     */
    public void intoSave(MouseEvent event) throws IOException {
        Main.dictionaryManagement.recentSavePage = 1;
        ((Pane) Main.root).getChildren().clear();
        Main.root = saveLoader.load();
        ((SaveController) saveLoader.getController()).StartSave();

        Main.scene.setRoot(Main.root);
    }

    /**
     * Switches the scene to Search when user enter a word on searching bar.
     * Words included in the dictionary will be displayed in the listview below Searching bar.
     * If the dictionary does not have thar word, there will be a notification to user.
     *
     * @param event the event when user enter a word on searching bar
     * @throws IOException when cannot load the FXMLLoader searchLoader
     */
    public void intoWord(KeyEvent event) throws IOException {
        ObservableList<String> items = FXCollections.observableArrayList();

        //Creates list view when the text on searching bar changes.
        SearchingBar.textProperty().addListener((observable, oldValue, newValue) -> {
            wordList.getItems().clear();
            Main.trie.resetWordList();

            SearchingBar.setText(newValue);
            Main.trie.search(SearchingBar.getText().toLowerCase());

            wordList.setVisible(!Main.trie.getWordsBySearching().isEmpty());

            items.addAll(Main.trie.getWordsBySearching());
            wordList.setItems(items);
        });

        //Creates event when click the worldList.
        wordList.setOnMouseClicked(MouseEvent -> {
            String selectedWord = (String) wordList.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                SearchingBar.setText(selectedWord);
            }
        });

        //Goes down the wordlist.
        if (event.getCode() == KeyCode.DOWN
                && wordList.getSelectionModel().getSelectedIndex() < wordList.getItems().size() - 1) {
            // Move selection down
            if (!wordList.getSelectionModel().isEmpty()) {
                wordList.getSelectionModel().selectNext();
            } else {
                wordList.getSelectionModel().selectFirst();
            }
        }

        //Goes up the wordlist
        if (event.getCode() == KeyCode.UP && wordList.getSelectionModel().getSelectedIndex() > 0) {
            // Move selection up
            wordList.getSelectionModel().selectPrevious();
        }

        //Changes to searching scene when entering the word to search.
        if (event.getCode() == KeyCode.ENTER) {
            if (!wordList.getSelectionModel().isEmpty()) {
                SearchingBar.setText((String) wordList.getSelectionModel().getSelectedItem());
            }
            wordList.setVisible(false);
            ((Pane) Main.root).getChildren().clear();
            Main.root = searchLoader.load();
            ((SearchController) searchLoader.getController()).getWordTarget().setText(SearchingBar.getText());
            ((SearchController) searchLoader.getController()).StartSearching();

            Main.scene.setRoot(Main.root);
        }
    }

    /**
     * Get into Setting section where you can Insert/ Edit/ Delete words in the dictionary.
     *
     * @throws IOException when cannot load the FXMLLoader settingLoader
     */
    public void intoSetting() throws IOException {
        if (settingPane.isVisible()) {
            scenePane.getChildren().remove(adjustPane);
            settingPane.setVisible(false);
        } else {
            settingPane.setVisible(true);
            if (!settingLoaded) {
                adjustPane = settingLoader.load();
                settingLoaded = true;
            }
            scenePane.getChildren().add(adjustPane);
            adjustPane.setLayoutX(106);
            adjustPane.setLayoutY(106);
            ((SettingController) settingLoader.getController()).StartInsert();
        }
    }

    /**
     * Changes the method to adjust the dictionary among Insert, Edit and Delete.
     *
     * @param event the event when user click the method label
     * @throws IOException when cannot load the FXMLLoader settingLoader
     */
    public void adjustDictionary(MouseEvent event) throws IOException {
        if (event.getSource() == insertWord) {
            ((SettingController) settingLoader.getController()).StartInsert();
        } else if (event.getSource() == editWord) {
            ((SettingController) settingLoader.getController()).StartEdit();
        } else if (event.getSource() == deleteWord) {
            ((SettingController) settingLoader.getController()).StartDelete();
        }
    }

    /**
     * Unable the wordList ( a listview ) when user click other nodes.
     */
    public void outSearch() {
        wordList.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

