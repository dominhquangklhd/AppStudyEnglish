package app.DB_Connection;

import app.Model.*;
import app.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class DatabaseConnection {
    private final int NumOfQuestionMC = 10;
    String url;
    String username;
    String password;
    DictionaryCommandline cmdLine = new DictionaryCommandline();
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Trie trie;

    public boolean englishWord = true;

    private List<String> wordBySearch = new LinkedList<>();

    private List<List<String>> listDB_MC = new LinkedList<>();

    public List<String> getWordBySearch() {
        return wordBySearch;
    }

    public List<List<String>> getListDB_MC() {
        return listDB_MC;
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

            url = "jdbc:mysql://localhost:3306/appEnglish";
            username = "root";
            password = "Minhquanadc@1";

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

    public void setSave(String word) {
        try {
            String sql = "UPDATE av SET isSaved = true WHERE word = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Cannot saved this word");
            System.out.println(e.getMessage());
        }
    }

    public void setUnSave(String word) {
        try {
            String sql = "UPDATE av SET isSaved = false WHERE word = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Cannot unsaved this word");
        }
    }

    public boolean isSaved(String word) {
        try {
            String sql = "SELECT isSaved FROM av WHERE word = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("isSaved").equals("0")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Can not check this word!");
        }
        return false;
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
        } catch (SQLException ex) {
            System.out.println("Can not find this word!");
            System.out.println(ex.getMessage());
        }
        return res;
    }

    public void setSavedWord() {
        try {
            String sql = "SELECT * FROM av WHERE isSaved = true";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("word");
                Main.dictionaryManagement.wordSavedList.add(word);
            }

            Main.dictionaryManagement.number_of_page = Main.dictionaryManagement.wordSavedList.size()/16 + 1;
        } catch (SQLException ex) {
            System.out.println("Can not create Saved list!");
        }
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

    public void gameDataBaseMultipleChoice() throws SQLException {
        String sql = "SELECT * FROM abcdquestion ORDER BY RAND() LIMIT ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, NumOfQuestionMC);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String question = resultSet.getString("question");
            String A = resultSet.getString("A");
            String B = resultSet.getString("B");
            String C = resultSet.getString("C");
            String D = resultSet.getString("D");
            String answer = resultSet.getString("answer");
            List<String> tmp = Arrays.asList(question, A, B, C, D, answer);;

            listDB_MC.add(tmp);
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
