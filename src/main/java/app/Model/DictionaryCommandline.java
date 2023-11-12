package app.Model;

import java.io.IOException;

public class DictionaryCommandline {
    public DictionaryManagement dictionaryManagement = new DictionaryManagement();

    /**
     * Imports words from a file into the dictionary.
     * @throws IOException if an io error occurs
     */

    public void importFromFile() throws IOException {
        dictionaryManagement.insertFromFile();
    }

    /**
     * Display all words in the dictionary.
     */
    public void showAllWords() {
        int i = 1;
        System.out.println("No      |  English     |    Vietnamese");
        for (Word w : dictionaryManagement.dictionary.words) {
            String wt = w.getWordTarget();
            String we = w.getWordExplain();
            System.out.printf("%-8d|  %-12s|    %s\n", i, wt, we);
            i++;
        }
        System.out.println("...");
    }

    /**
     * Inserts word, and then show it.
     */

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }
}
