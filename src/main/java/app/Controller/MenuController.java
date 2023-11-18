package app.Controller;

import app.API.GGTranslateAPI;
import app.Main;
import app.Model.Trie;
import app.Model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    //FXML
    @FXML
    public ImageView minimize;
    @FXML
    public ListView wordList;
    @FXML
    public Button paneButton;
    @FXML
    public ImageView setting;
    @FXML
    public AnchorPane settingPane;
    @FXML
    public Label insertWord;
    @FXML
    public Label editWord;
    @FXML
    public Label deleteWord;
    @FXML
    public ImageView intoSearch;
    @FXML
    public ImageView intoTranslate;
    @FXML
    public ImageView intoGame;
    @FXML
    public ImageView intoSave;
    @FXML
    public ImageView intoHistory;
    @FXML
    public ImageView guide;
    @FXML
    public ImageView Out;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField SearchingBar;
    //Nor
    public FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("/FXML/Setting.fxml"));
    public AnchorPane adjustPane;
    boolean settingLoaded = false;

    //Present
    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public void intoTranslate(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Translate.fxml"));
        Parent root = loader.load();

        //Switch scene to HistoryScene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

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

    public void intoGuide() {
        System.out.println("Guide");
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

    public void minimizeStage(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void intoOut() throws IOException {
        Main.dictionaryManagement.historyExportToFile();
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public void intoSetting() throws IOException {
        if (settingPane.isVisible()) {
            scenePane.getChildren().remove(adjustPane);
            settingPane.setVisible(false);
        } else {
            settingLoader.getClass().getResource("/FXML/Setting.fxml");
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

    public void adjustDictionary(MouseEvent event) throws IOException {
        if (event.getSource() == insertWord) {
            ((SettingController) settingLoader.getController()).StartInsert();
        } else if (event.getSource() == editWord) {
            ((SettingController) settingLoader.getController()).StartEdit();
        } else if (event.getSource() == deleteWord) {
            ((SettingController) settingLoader.getController()).StartDelete();
        }
    }

    //Supporting methods
    public void outSearch() {
        wordList.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }
}
