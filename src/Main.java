import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.importFromFile();
        dictionaryCommandline.dictionaryManagement.dictionaryAdvanced();
        //dictionaryCommandline.showAllWords();
    }
}
