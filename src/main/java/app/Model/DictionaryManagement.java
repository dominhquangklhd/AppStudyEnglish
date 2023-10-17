package app.Model;

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public Dictionary dictionary = new Dictionary();

    public DictionaryManagement() {

    }
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

    public void createNewFile() throws IOException {
        File dicFile = new File("txt/dictionaries.txt");
        if (dicFile.exists()) {
            System.out.println("Exist!");
        } else {
            dicFile.createNewFile();
            System.out.println("Created!");
        }
    }

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
                case 0 -> System.out.println("Exist!");
                case 1 -> addNewWord();
                case 2 -> deleteWord();
                case 3 -> updateWord();
                case 4 -> showAllWords();
                case 5 -> dictionaryLookup();
                case 6 -> dictionarySearcher();
                case 7 -> System.out.println("Time to train by a game!");
                case 8 -> insertFromFile();
                case 9 -> dictionaryExportToFile();
                default -> System.out.println("Action not supported");
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage() + " is not supported");
            System.exit(1);
        }

        sc.close();
    }
}
