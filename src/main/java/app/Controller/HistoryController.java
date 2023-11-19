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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController extends BaseController implements Initializable {
    //FXML
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
    public ImageView delete1;
    @FXML
    public ImageView delete2;
    @FXML
    public ImageView delete3;
    @FXML
    public ImageView delete4;
    @FXML
    public ImageView delete5;
    @FXML
    public ImageView delete6;
    @FXML
    public ImageView delete7;
    @FXML
    public ImageView delete8;
    @FXML
    public ImageView delete9;
    @FXML
    public ImageView delete10;
    @FXML
    public ImageView delete11;
    @FXML
    public ImageView delete12;
    @FXML
    public ImageView delete13;
    @FXML
    public ImageView delete14;
    @FXML
    public ImageView delete15;
    @FXML
    public ImageView delete16;
    @FXML
    public ImageView nextPage;
    @FXML
    public ImageView prePage;
    @FXML
    public Label deletaAll;

    //Nor
    private int number_of_page = 0;
    private  int recentPage = 1;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));


    //Content Handlers
    public void toNextPage() {
        Main.dictionaryManagement.recentHistoryPage++;
        StartHistory();
    }

    public void toPreviousPage() {
        Main.dictionaryManagement.recentHistoryPage--;
        StartHistory();
    }

    public void delete(MouseEvent event) {
        int i = getLabeldelete((ImageView) event.getSource());
        Main.dictionaryManagement.wordHistoryList.remove(label(i-1).getText());
        Main.dictionaryManagement.decreaseHistoryPage();
        StartHistory();
    }

    public void reSearchWord(MouseEvent event) throws IOException {
        Label tmp = (Label) event.getSource();
        String w = "";
        for (int i = 0; i < Main.dictionaryManagement.wordHistoryList.size(); i++) {
            if (tmp.getText().equals(Main.dictionaryManagement.wordHistoryList.get(i))) {
                w = Main.dictionaryManagement.wordHistoryList.get(i);
                break;
            }
        }

        ((Pane) Main.root).getChildren().clear();
        Main.root = loader.load();
        if (w.equals("_____")) {
            ((SearchController) loader.getController()).CryIfCannotFindWord();
        } else {
            ((SearchController) loader.getController()).getWordTarget().setText(w);
            ((SearchController) loader.getController()).StartSearching();
        }

        //Switch scene to SearchScene
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        Main.scene.setRoot(Main.root);
        //stage.setScene (Main.scene);
        //stage.show();
    }

    public void deleteAll() {
        Main.dictionaryManagement.wordHistoryList.clear();
        StartHistory();
    }



    //Supporting methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }

    public void StartHistory() {
        if (Main.dictionaryManagement.recentHistoryPage > Main.dictionaryManagement.number_of_Historypage
                && Main.dictionaryManagement.number_of_Historypage != 0) {
            Main.dictionaryManagement.recentHistoryPage = Main.dictionaryManagement.number_of_Historypage;
        }
        number_of_page = Main.dictionaryManagement.number_of_Historypage;
        recentPage = Main.dictionaryManagement.recentHistoryPage;
        if (number_of_page <= 1) {
            nextPage.setVisible(false);
        } else {
            if (recentPage == 1) {
                prePage.setVisible(false);
                nextPage.setVisible(true);
            } else if (recentPage > 1 && recentPage < number_of_page) {
                prePage.setVisible(true);
                nextPage.setVisible(true);
            } else {
                prePage.setVisible(true);
                nextPage.setVisible(false);
            }
        }
        int start = 16*(recentPage - 1);
        int remainLabel = -1;
        for (int i = start; i < Main.dictionaryManagement.wordHistoryList.size(); i++) {
            if (i >= start + 16) {
                break;
            } else {
                label(i % 16).setText(Main.dictionaryManagement.wordHistoryList.get(i));
                remainLabel = i % 16;
            }
        }
        for (int i = remainLabel + 1; i < 16; i++) {
            label(i).setText("_____");
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

    public int getLabeldelete(ImageView source) {
        if (source == delete1) return 1;
        else if (source == delete2) return 2;
        else if (source == delete3) return 3;
        else if (source == delete4) return 4;
        else if (source == delete5) return 5;
        else if (source == delete6) return 6;
        else if (source == delete7) return 7;
        else if (source == delete8) return 8;
        else if (source == delete9) return 9;
        else if (source == delete10) return 10;
        else if (source == delete11) return 11;
        else if (source == delete12) return 12;
        else if (source == delete13) return 13;
        else if (source == delete14) return 14;
        else if (source == delete15) return 15;
        else return 16;
    }

}
