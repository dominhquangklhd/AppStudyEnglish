import java.sql.*;

public class DatabaseConnection {
    String url;
    String username;
    String password;
    Connection conn;
    Statement stmt;
    ResultSet set;
    public Dictionary dictionary = new Dictionary();

    public void createConnection() {
        try {
            url = "jdbc:mysql://localhost:3306/dictionary_manager?autoReconnect=true&useSSL=false";
            username = "root";
            password = "Boquoctrung10012004";


            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertFromDatabase() {
        try {
            set = stmt.executeQuery("SELECT * FROM dictionary");

            while(set.next()) {
                String target = set.getString("word_target");
                String explain = set.getString("word_explain");
                Word newWord = new Word(target, explain);
                dictionary.words.add(newWord);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Chua hoan thien
    public void exportToDatabase() {
        try {
            String sql = "INSERT INTO dictionary VALUES (?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (Word w : dictionary.words) {
                String value1 = w.getWordTarget();
                String value2 = w.getWordExplain();
                preparedStatement.setString(1, value1);
                preparedStatement.setString(2, value2);
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Insert data problems: " + e);
        }
    }

    public void DatabaseClose() {
        try {
            set.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
