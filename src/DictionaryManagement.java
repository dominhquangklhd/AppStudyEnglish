import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public Dictionary dictionary = new Dictionary();

    public void insertFromCommandline() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the number of words you want to add: ");
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

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                //System.out.println(parts[0] + " " + parts[1]);
                Word newWord = new Word(parts[0], parts[1]);
                dictionary.words.add(newWord);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the number of word you need to add: ");
            int n = sc.nextInt();
            for (int i = 1; i <= n; i++) {
                System.out.print("Enter a word you want to add\n" + "English: ");
                String wordTarget = sc.nextLine();
                System.out.print("Vietnamese: ");
                String wordExplain = sc.nextLine();
                Word word = new Word(wordTarget, wordExplain);
                dictionary.words.add(word);
                dictionaryExportToFile();
            }
        }
    }

    public void updateWord() {

    }

    public void deleteWord() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your word you want to remove\nEnglish: ");
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
        System.out.println("Dictionary searcher!");
        System.out.println("Enter a word you want to search: ");
        String word = sc.nextLine();
        int k = word.length();
        for (Word w : dictionary.words) {
            String tmp = w.getWordTarget().substring(0, k - 1);
            if (tmp.equals(word)) {
                System.out.print(w.getWordTarget() + " ");
            }
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
                e.printStackTrace();
            }
        }
        bufferedWriter.close();
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
        int n = sc.nextInt();
        switch (n) {
            case 0:
                System.out.println("Exist!");
                break;
            case 1:
                addNewWord();
                break;
            case 2:
                deleteWord();
                break;
            case 3:
                updateWord();
                break;
            case 4:
                DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
                dictionaryCommandline.showAllWords();
                break;
            case 5:
                dictionaryLookup();
                break;
            case 6:
                dictionarySearcher();
                break;
            case 7:
                System.out.println("Time to train by a game!");
                break;
            case 8:
                insertFromFile();
                break;
            case 9:
                dictionaryExportToFile();
                break;
            default:
                System.out.println("Action not supported");
                break;
        }
        sc.close();
    }
}
