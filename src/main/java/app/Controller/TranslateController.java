package app.Controller;

import app.API.GGTranslateAPI;
import app.API.TextToSpeech;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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

    /**
     * Switches the language from "Vietnamese to English" and vice versa.
     */
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

    /**
     * Copies the translated text to clipboard.
     */
    public void copyTranslation() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(translation);
        clipboard.setContents(strSel, null);
    }

    /**
     * Presents the words count of the text target.
     */
    public void countWord() {
        countWord.setText(Integer.toString(textBar.getText().length()) + "/2000");
    }

    /**
     * Translates text using API Translate.
     */
    public void translate() {
        translationBar.setText(GGTranslateAPI.translate(textBar.getText()));
        translation = translationBar.getText();
    }

    /**
     * Uses API to speak the text.
     */
    public void speakText() {
        TextToSpeech.SpeakEnglish = GGTranslateAPI.isEnglish;
        TextToSpeech.playVoice(textBar.getText());
    }

    /**
     * Uses API to speak the translation.
     */
    public void speakTranslation() {
        TextToSpeech.SpeakEnglish = !GGTranslateAPI.isEnglish;
        TextToSpeech.playVoice(translationBar.getText());
    }

    //Supporting methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }

    /**
     * Sets the translation to the translation bar ( to make sure that user cannot edit the translation bar ).
     */
    public void doNothing() {
        translationBar.setText(translation);
    }
}
