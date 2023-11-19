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

public class TranslateController extends BaseController implements Initializable {
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


    //Content Handlers

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

    public void doNothing() {
        translationBar.setText(translation);
    }
}
