package app.Controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveController extends BaseController implements Initializable {
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
    public ImageView prePage;
    @FXML
    public ImageView nextPage;

    int recentPage = 1;
    int number_of_page = 0;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));


    //Content Handler

    /**
     * Gets into the next Save page.
     */
    public void toNextPage() {
        Main.dictionaryManagement.recentSavePage++;
        StartSave();
    }

    /**
     * Gets into the previous Save page.
     */
    public void toPreviousPage() {
        Main.dictionaryManagement.recentSavePage--;
        StartSave();
    }

    /**
     * Researches a word in Save list.
     *
     * @param event the event when user click on a word to research
     * @throws IOException when cannot load the FXMLLoader loader
     */
    public void reSearchWord(MouseEvent event) throws  IOException {
        Label tmp = (Label) event.getSource();
        String w = "";
        for (int i = 0; i < Main.dictionaryManagement.wordSavedList.size(); i++) {
            if (tmp.getText().equals(Main.dictionaryManagement.wordSavedList.get(i))) {
                w = Main.dictionaryManagement.wordSavedList.get(i);
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

        Main.scene.setRoot(Main.root);
    }


    //Supporting methods

    /**
     * Prepares before start Save scene:
     * 1/ Sets the number of Save page and the recent page.
     * 2/ Sets words to its specified label.
     */
    public void StartSave() {
        number_of_page = Main.dictionaryManagement.number_of_Savedpage;
        recentPage = Main.dictionaryManagement.recentSavePage;
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
        int remainLabel = 0;
        for (int i = start; i < Main.dictionaryManagement.wordSavedList.size(); i++) {
            if (i >= start + 16) {
                break;
            } else {
                label(i % 16).setText(Main.dictionaryManagement.wordSavedList.get(i));
                remainLabel = i % 16;
            }
        }
        for (int i = remainLabel + 1; i < 16; i++) {
            label(i).setText("_____");
        }
    }

    /**
     * Returns the label by index.
     *
     * @param i the index of the label
     * @return the label that specified by index
     */
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }

}
