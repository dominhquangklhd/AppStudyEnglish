package app.Model;

import java.io.IOException;
import app.DB_Connection.DatabaseConnection;
public class Main {
    public static void main(String[] args) throws IOException {
        /*DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.importFromFile();
        dictionaryCommandline.dictionaryManagement.dictionaryAdvanced();*/

        Trie test = new Trie();
        test.insertWord("hello");
        test.insertWord("abc");
        test.insertWord("bcs");
        test.insertWord("school");
        test.search("h");
        for (String s : test.getWordsBySearching()) {
            System.out.println(s);
        }
    }
}
