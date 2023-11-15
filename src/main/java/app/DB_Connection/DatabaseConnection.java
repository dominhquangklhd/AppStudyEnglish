package app.DB_Connection;

import app.Model.*;
import app.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class DatabaseConnection {
    public static final int NumOfQuestionMC = 15;
    public static final int NUmOfQuestionGameIMG = 10;
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
    private List<String> ansGameIMG = new LinkedList<>();

    public List<String> getAnsGameIMG() {
        return ansGameIMG;
    }

    public List<String> getWordBySearch() {
        return wordBySearch;
    }

    public List<List<String>> getListDB_MC() {
        return listDB_MC;
    }

    public void resetWordBySearch() {
        wordBySearch.clear();
    }

    /**
     * Creates a database connection.
     */

    public void createConnection() {
        try {
            // mn chỉnh theo db sql của mn.

            /*url = "jdbc:mysql://localhost:3306/appenglish?autoReconnect=true&useSSL=false";
            username = "root";
            password = "Boquoctrung10012004";*/

            url = "jdbc:mysql://localhost:3306/appEnglish";
            username = "root";
            password = "Minhquanadc@1";

            /*url = "jdbc:mysql://localhost:3306/dict_database";
            username = "root";
            password = "Q25012004kl#";*/

            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves words from the database and inserts them into the application's dictionary.
     */

    public void insertFromDatabase() {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM dictionary");

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String target = resultSet.getString("target");
                String explain = resultSet.getString("definition");
                Word newWord = new Word(target, explain);
                cmdLine.dictionaryManagement.dictionary.words.add(newWord);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts a new word into the database.
     *
     * @param target      The target word.
     * @param IPA         The International Phonetic Alphabet representation.
     * @param type        The list of word types.
     * @param definition  The list of definitions corresponding to each type.
     * @return True if the word is successfully inserted, false otherwise.
     */

    public boolean insertToDatabase(String target, String IPA, List<String> type, List<List<String>> definition) {
        try {
            String html = "<body style=\"background-color: #FFF8DC;\">" +
                    "<p style=\"font-family: 'Lobster'; font-size: 20px; font-weight: 700;\">";
            html += "@" + target + " /" + IPA + "/" + "<br />";

            int n = type.size();
            for (int i = 0; i < n; i++) {
                html += "* " + type.get(i) + "<br />";
                for (int j = 0; j < definition.get(i).size(); j++) {
                    html += "- " + definition.get(i).get(j) + "<br />";
                }
            }
            html += "</p></body>";

            String sql = "INSERT INTO dictionary (target, definition, isSaved) VALUES (?, ?, false)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, target);
            preparedStatement.setString(2, html);

            try {
                preparedStatement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Marks a word as saved in the database.
     *
     * @param word The word to be saved.
     */

    public void setSave(String word) {
        try {
            String sql = "UPDATE dictionary SET isSaved = true WHERE target = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Cannot saved this word");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Unsave a word.
     * @param word the word to unsave
     */

    public void setUnSave(String word) {
        try {
            String sql = "UPDATE dictionary SET isSaved = false WHERE target = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Cannot unsaved this word");
        }
    }

    /**
     * Check if the word has been saved or not.
     * @param word the word to consider
     * @return true if the word has been saved
     */

    public boolean isSaved(String word) {
        try {
            String sql = "SELECT isSaved FROM dictionary WHERE target = ?";
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

    public boolean hasInDatabase(String word) throws SQLException {
        try {
            String sql = "SELECT COUNT(*) FROM dictionary WHERE target = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can not check this word!");
        }
        return false;
    }

    /**
     * Finds the definition of a word in the database.
     * @param word The word to search for.
     * @return The definition of the word, or an empty string if the word is not found.
     */

    public String findWordInDatabase(String word) {
        String res = "";
        try {
            String sql = "SELECT * FROM dictionary WHERE target = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, word);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String des = resultSet.getString("definition");
                res += des + "\n";
            }
        } catch (SQLException ex) {
            System.out.println("Can not find this word!");
            System.out.println(ex.getMessage());
        }
        return res;
    }

    /**
     * Set the word to be saved if isSaved in database = true.
     */

    public void setSavedWord() {
        try {
            String sql = "SELECT * FROM dictionary WHERE isSaved = true";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String word = resultSet.getString("target");
                Main.dictionaryManagement.wordSavedList.add(word);
            }

            Main.dictionaryManagement.number_of_Savedpage = Main.dictionaryManagement.wordSavedList.size()/16 + 1;
        } catch (SQLException ex) {
            System.out.println("Can not create Saved list!");
        }
    }

    /**
     * Deletes a word from the database.
     *
     * @param word The word to be deleted.
     * @return True if the word is successfully deleted, false otherwise.
     */

    public boolean deleteWordInDatabase(String word) {
        try {
            String sql = "DELETE FROM dictionary WHERE target = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, word);

            int deletedRow = preparedStatement.executeUpdate();
            if (deletedRow <= 0)
                return false;
            insertIntoTrie();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a word in the database.
     *
     * @param target      The target word.
     * @param IPA         The International Phonetic Alphabet representation.
     * @param type        The list of word types.
     * @param definition  The list of definitions corresponding to each type.
     * @return True if the word is successfully updated, false otherwise.
     */

    public boolean updateWordInDatabase(String target, String IPA, List<String> type, List<List<String>> definition) {
        if (!deleteWordInDatabase(target))
            return false;
        if (!insertToDatabase(target, IPA, type, definition))
            return false;
        return true;
    }

    /**
     * Inserts words from the database into the trie data structure.
     */

    public void insertIntoTrie() {
        try {
            String sql = "SELECT * FROM dictionary";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String value = resultSet.getString("target");
                Main.trie.insertWord(value);
            }

        } catch (SQLException ex) {
            System.out.println("Can not insert into trie!");
        }
    }

    /**
     * Fetches multiple-choice game questions from the database and populates the list.
     *
     * @throws SQLException If a database access error occurs.
     */

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
            List<String> tmp = Arrays.asList(question, A, B, C, D, answer);

            listDB_MC.add(tmp);
        }
    }

    /**
     * Gets data for the game from a game database.
     * @throws SQLException if an error occurs
     */

    public void dataGameIMG() throws SQLException {
        String sql = "SELECT * FROM gameimage ORDER BY RAND() LIMIT ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, NUmOfQuestionGameIMG);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String ansIMG = resultSet.getString("nameimg");
            ansGameIMG.add(ansIMG);
        }
    }

    /**
     * Close the database connection.
     */

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
