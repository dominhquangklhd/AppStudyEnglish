package app;

import app.API.GGTranslateAPI;
import app.Controller.SaveController;
import app.DB_Connection.DatabaseConnection;
import app.Model.DictionaryManagement;
import app.Model.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    private double x = 0;
    private double y = 0;

    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public static final Trie trie = new Trie();

    public static DatabaseConnection databaseConnection = new DatabaseConnection();

    public static SaveController SaveStage = new SaveController();

    public static GGTranslateAPI TranslateAPI = new GGTranslateAPI();

    public static Parent root;

    public static Scene scene;

    @Override
    public void start(Stage stage) {
        try {
            dictionaryManagement.insertFromFile();
            dictionaryManagement.insertHistoryFromFile();
            databaseConnection.createConnection();
            databaseConnection.setSavedWord();
            databaseConnection.insertIntoTrie();

            root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
            scene = new Scene(root);
            
            //stage.setWidth(1200);
            //stage.setHeight(700);
            //stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene (scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
