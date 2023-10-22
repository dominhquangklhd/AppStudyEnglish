package app.Model;

import java.io.IOException;
import app.DB_Connection.DatabaseConnection;
public class Main {
    public static void main(String[] args) throws IOException {
        /*DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.importFromFile();
        dictionaryCommandline.dictionaryManagement.dictionaryAdvanced();*/

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createConnection();
        databaseConnection.findWordInDatabase("abutilon");
        databaseConnection.DatabaseClose();
    }
}
