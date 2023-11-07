package app;

import app.API.GGTranslateAPI;
import app.Controller.SaveController;
import app.DB_Connection.DatabaseConnection;
import app.Model.DictionaryManagement;
import app.Model.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public static final Trie trie = new Trie();

    public static DatabaseConnection databaseConnection = new DatabaseConnection();

    public static SaveController SaveStage = new SaveController();

    public static GGTranslateAPI TranslateAPI = new GGTranslateAPI();

    @Override
    public void start(Stage stage) {
        try {
            dictionaryManagement.insertFromFile();
            dictionaryManagement.insertHistoryFromFile();
            databaseConnection.createConnection();
            databaseConnection.setSavedWord();
            databaseConnection.insertIntoTrie();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
            Scene scene = new Scene(root);

            stage.setWidth(1200);
            stage.setHeight(700);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene (scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
