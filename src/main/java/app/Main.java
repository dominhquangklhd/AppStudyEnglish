package app;

import app.DB_Connection.DatabaseConnection;
import app.Model.DictionaryManagement;
import app.Model.Trie;
import app.Model.Word;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    public static final Trie trie = new Trie();
    public static final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void start(Stage stage) {

        try {
            databaseConnection.createConnection();
            databaseConnection.insertIntoTrie();

            dictionaryManagement.insertFromFile();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Menu.fxml"));
            Scene scene = new Scene(root);

            stage.setWidth(1200);
            stage.setHeight(735);
            stage.setResizable(false);
            stage.setScene (scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
