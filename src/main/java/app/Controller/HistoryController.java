package app.Controller;

import app.Main;
import app.Model.DictionaryManagement;
import app.Model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    //FXML
    @FXML
    public ListView wordList;
    @FXML
    public ImageView minimize;
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
    @FXML
    public ImageView intoSave;
    @FXML
    public Button paneButton;

    //Nor

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
            Main.trie.search(SearchingBar.getText());

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

        if (event.getCode() == KeyCode.ENTER) {
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

    public void reSearchWord(MouseEvent event) throws  IOException {
        Label tmp = (Label) event.getSource();
        String w = "";
        for (int i = 0; i < Main.dictionaryManagement.HistorySize; i++) {
            if (tmp.getText().equals(Main.dictionaryManagement.wordHistoryList.get(i))) {
                w = Main.dictionaryManagement.wordHistoryList.get(i);
                break;
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
        Parent root = loader.load();
        if (w.equals("_____")) {
            ((SearchController) loader.getController()).CryIfCannotFindWord();
        } else {
            ((SearchController) loader.getController()).getWordTarget().setText(w);
            ((SearchController) loader.getController()).StartSearching();
        }

        //Switch scene to SearchScene
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
            label(i).setText(Main.dictionaryManagement.wordHistoryList.get(i));
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

    public void outSearch() {
        wordList.setVisible(false);
    }
}
