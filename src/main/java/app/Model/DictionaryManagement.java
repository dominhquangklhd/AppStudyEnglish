package app.Model;

import app.DB_Connection.DatabaseConnection;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    public static boolean exit = false;
    public final int HistorySize = 240;
    public Dictionary dictionary = new Dictionary();
    public List<String> wordHistoryList = new ArrayList<>();
    public List<String> wordSavedList = new ArrayList<>();

    public int recentSavePage = 1;
    public int number_of_Savedpage = 0;

    public int recentHistoryPage = 1;
    public int number_of_Historypage = 0;

    public DictionaryManagement() {
    }

    /**
     * Add words to the dictionary.
     */
    public void insertFromCommandline() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of words you want to insert: ");
        int n = in.nextInt();
        in.nextLine();
        while (n > 0) {
            System.out.print("Enter your word: ");
            String wE = in.nextLine();
            System.out.print("Enter the meaning of word in VNese: ");
            String explain = in.nextLine();
            dictionary.setWord(wE, explain);
            n--;
        }
        in.close();
    }

    /**
     * Creates a file to store data for the dictionary.
     * @throws IOException if an I/O error occurs
     */

    public void createNewFile() throws IOException {
        File dicFile = new File("txt/dictionaries.txt");
        if (dicFile.exists()) {
            System.out.println("Exist!");
        } else {
            dicFile.createNewFile();
            System.out.println("Created!");
        }
    }

    /**
     * Creates a dictionary using data in file.
     * @throws IOException
     */

    public void insertFromFile() throws IOException {
        String filePath = "txt/dictionaries.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                //System.out.println(parts[0] + " " + parts[1]);
                Word newWord = new Word(parts[0], parts[1]);
                dictionary.words.add(newWord);
            }


        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Create a history of using dictionary using data in file
     * @throws IOException
     */

    public void insertHistoryFromFile() throws IOException {
        String filePath = "txt/history.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordHistoryList.add(line);
            }
            number_of_Historypage = wordHistoryList.size()/16 + 1;
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Exports history using dictionary to a file.
     * @throws IOException
     */

    public void historyExportToFile() throws IOException {
        File path = new File("txt/history.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        for (String w : wordHistoryList) {
            try {
                bufferedWriter.append(w + "\n");
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        bufferedWriter.close();
    }

    /**
     * Lookup for a word and return the definition of the word.
     */
    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word you want to look up: ");
        String word = sc.nextLine();
        boolean has = false;
        for (Word w : dictionary.words) {
            if (w.getWordTarget().equals(word)) {
                System.out.println("In Vietnamese: " + w.getWordExplain());
                has = true;
            }
        }
        if (!has) {
            System.out.println("The word does not exist in this dictionary!");
        }
    }

    /**
     * Creates a multiple choice game.
     * @throws SQLException if an sql error occurs
     */

    public void gameMultipleChoice() throws SQLException {
        int num = 1;
        int mark = 0;
        String question;
        String A;
        String B;
        String C;
        String D;
        String answer;

        DatabaseConnection gameConnection = new DatabaseConnection();
        gameConnection.createConnection();
        gameConnection.gameDataBaseMultipleChoice();

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose A, B, C or D which is the answer you think right!\n"
                + "Press Enter if you are ready!");
        sc.nextLine();

        for (List<String> ex : gameConnection.getListDB_MC()) {
            question = ex.get(0);
            A = ex.get(1);
            B = ex.get(2);
            C = ex.get(3);
            D = ex.get(4);
            answer = ex.get(5);

            System.out.println(num + "." + question);
            System.out.println("[A] " + A);
            System.out.println("[B] " + B);
            System.out.println("[C] " + C);
            System.out.println("[D] " + D);

            System.out.print("Your answer is: ");
            String tmp = sc.next();
            String yourAns = tmp.toLowerCase();

            if (yourAns.equals(answer)) {
                System.out.println("---CORRECT---");
                mark += 10;
            } else {
                System.out.println("---WRONG---");
            }
            System.out.println();
            num++;
        }

        System.out.println("Total mark: " + mark + "\n");
    }

    public void addWordtoHistory(String w) {
        wordHistoryList.add(0, w);
        if (wordHistoryList.size() >= HistorySize) {
            wordHistoryList.remove(HistorySize);
        }
    }

    public void increaseHistoryPage() {
        if ((wordHistoryList.size() % 16) == 1) {
            number_of_Historypage++;
        }
    }
    public void decreaseHistoryPage() {
        if ((wordHistoryList.size() % 16) == 0) {
            number_of_Historypage--;
        }
    }

    public void increaseSavePage() {
        if ((wordSavedList.size() % 16) == 1) {
            number_of_Savedpage++;
        }
    }

    public void decreaseSavePage() {
        if ((wordSavedList.size() % 16) == 0) {
            number_of_Savedpage--;
        }
    }

    /**
     * Adds a new word to the dictionary.
     * @throws IOException if an I/O error occurs
     */

    public void addNewWord() throws IOException {
        System.out.print("Add new word!\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of word you need to add: ");
        String s = sc.nextLine();

        try {
            int n = Integer.parseInt(s);
            for (int i = 1; i <= n; i++) {
                System.out.print("Enter a word you want to add\n" + "English: ");
                String wordTarget = sc.nextLine();
                System.out.print("Vietnamese: ");
                String wordExplain = sc.nextLine();
                Word word = new Word(wordTarget, wordExplain);
                dictionary.words.add(word);

                dictionaryExportToFile();
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage() + " is not supported!");
            System.exit(1);
        }
    }

    /**
     * Update the definition of a word.
     * @throws IOException if an io error occurs
     */

    public void updateWord() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your word you want to update: ");
        String wordTarget = sc.nextLine();
        System.out.print("New meaning: ");
        String newMeaning = sc.nextLine();
        for (Word word : dictionary.words) {
            if (word.getWordTarget().equals(wordTarget)) {
                word.setWordExplain(newMeaning);
            }
        }
        dictionaryExportToFile();
        sc.close();
    }

    /**
     * Deletes a word in the dictionary.
     * @throws IOException if an I/O error occurs
     */

    public void deleteWord() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your word you want to remove\nEnglish: ");
        String wordTarget = sc.nextLine();
        System.out.print("Vietnamese: ");
        String wordExplain = sc.nextLine();
        Word word = new Word(wordTarget, wordExplain);
        dictionary.words.remove(word);
        System.out.println("Deleted " + word.getWordTarget());
        dictionaryExportToFile();
        sc.close();
    }

    public void removeDuplicates() {

    }

    /**
     * Find words in dictionary that contain the searched word.
     */

    public void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        System.out.println("app.app.Model.Dictionary searcher!");
        System.out.print("Enter a word you want to search: ");
        String word = sc.nextLine();
        int k = word.length();
        boolean found = false;

        for (Word w : dictionary.words) {
            if(w.getWordTarget().length() < k)
                continue;
            String tmp = w.getWordTarget().substring(0, k);
            if (tmp.equals(word)) {
                found = true;
                System.out.print(w.getWordTarget() + " ");
            }
        }

        if (!found) {
            System.out.print("Not result");
        }

        System.out.println();
        sc.close();
    }

    /**
     * Exports the data of dictionary to a file.
     * @throws IOException
     */

    public void dictionaryExportToFile() throws IOException {
        File path = new File("txt/dictionaries.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        for (Word w : dictionary.words) {
            try {
                String res = w.getWordTarget() + "\t" + w.getWordExplain() + "\n";
                bufferedWriter.append(res);
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
                System.exit(1);
            }
        }
        bufferedWriter.close();
    }

    /**
     * Display all words in the dictionary.
     */

    public void showAllWords() {
        int i = 1;
        System.out.println("No      |  English     |    Vietnamese");
        for (Word w : dictionary.words) {
            String wt = w.getWordTarget();
            String we = w.getWordExplain();
            System.out.printf("%-8d|  %-12s|    %s\n", i, wt, we);
            i++;
        }
        System.out.println("...");
    }

    /**
     * Provides an advanced command-line interface for managing a dictionary with various actions.
     * @throws IOException if an I/O error occurs
     */

    public void dictionaryAdvanced() throws IOException {
        System.out.print("Welcome to My Application!\n" +
                "[0] Exit\n" +
                "[1] Add\n" +
                "[2] Remove\n" +
                "[3] Update\n" +
                "[4] Display\n" +
                "[5] Lookup\n" +
                "[6] Search\n" +
                "[7] Game\n" +
                "[8] Import from file\n" +
                "[9] Export to file\n" +
                "Your action: ");
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        try {
            int n = Integer.parseInt(s);
            switch (n) {
                case 0 -> {System.out.println("Exit!"); exit = true;}
                case 1 -> addNewWord();
                case 2 -> deleteWord();
                case 3 -> updateWord();
                case 4 -> showAllWords();
                case 5 -> dictionaryLookup();
                case 6 -> dictionarySearcher();
                case 7 -> gameMultipleChoice();
                case 8 -> insertFromFile();
                case 9 -> dictionaryExportToFile();
                default -> System.out.println("Action not supported!\n");
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage() + " is not supported!\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}