import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        //dictionaryCommandline.importFromFile();
        //dictionaryCommandline.dictionaryManagement.dictionaryAdvanced();
        //dictionaryCommandline.showAllWords();
        String abc = "abc";
        String abcd = "abcd";
        String abcde = "abcde";
        Trie trie = new Trie();
        trie.insertWord(abcd);
        trie.insertWord(abc);
        trie.insertWord(abcde);
        trie.search("ab");
    }
}
