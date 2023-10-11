import java.io.IOException;

public class DictionaryCommandline {
    public DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public DictionaryCommandline() {

    }

    public void importFromFile() throws IOException {
        dictionaryManagement.insertFromFile();
    }

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

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }
}
