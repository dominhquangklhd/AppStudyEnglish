package app.DB_Connection;

import app.Main;
import app.Model.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;



public class DatabaseConnection {
    String url;
    String username;
    String password;
    DictionaryCommandline cmdLine = new DictionaryCommandline();
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    private List<String> wordBySearch = new LinkedList<>();

    public List<String> getWordBySearch() {
        return wordBySearch;
    }

    public void resetWordBySearch() {
        wordBySearch.clear();
    }

    public void createConnection() {
        try {
            // mn chỉnh theo db sql của mn.

            /*url = "jdbc:mysql://localhost:3306/dictionary_manager?autoReconnect=true&useSSL=false";
            username = "root";
            password = "Boquoctrung10012004";*/

            url = "jdbc:mysql://localhost:3306/dict_database";
            username = "root";
            password = "Q25012004kl#";

            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertFromDatabase() {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM av");

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String target = resultSet.getString("word");
                String explain = resultSet.getString("description");
                Word newWord = new Word(target, explain);
                cmdLine.dictionaryManagement.dictionary.words.add(newWord);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Chua hoan thien
    public void exportToDatabase(List<Word> newWords) {
        try {
            String sql = "INSERT INTO av (word, description) VALUES (?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            for (Word w : newWords) {
                String value1 = w.getWordTarget();
                String value2 = w.getWordExplain();
                preparedStatement.setString(1, value1);
                preparedStatement.setString(2, value2);
            }
        } catch (SQLException e) {
            System.out.println("Insert data problems: " + e.getMessage());
        }
    }

    public void filterWord(String searchString) throws SQLException {
        String sql = "SELECT * FROM av WHERE word LIKE ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, searchString + "%");

        resultSet = preparedStatement.executeQuery();

        wordBySearch.clear();

        while (resultSet.next()) {
            String result = resultSet.getString("word");
            wordBySearch.add(result);
        }
    }

    public String findWordInDatabase(String word) {
        String res = "";
        try {
            String sql = "SELECT * FROM av WHERE word = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String pronounce = resultSet.getString("pronounce");
                if (!pronounce.isEmpty()) {
                    res += "/" + pronounce + "/" + "\n";
                }
                String des = resultSet.getString("description");
                res += des + "\n";
            }

            while (resultSet.next()) {
                String des = resultSet.getString("des");
                res += des + "\n";
            }
        } catch (SQLException ex) {
            System.out.println("Can not find this word!");
        }
        return res;
    }

    public void insertIntoTrie() {
        try {
            String sql = "SELECT * FROM av";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String value = resultSet.getString("word");
                Main.trie.insertWord(value);
            }

        } catch (SQLException ex) {
            System.out.println("Can not insert into trie!");
        }
    }

    public void DatabaseClose() {
        try {
            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
