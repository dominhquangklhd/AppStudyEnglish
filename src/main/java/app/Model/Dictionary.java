package app.Model;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public List<Word> words = new ArrayList<>();

    Dictionary() {

    }

    /**
     * Creates a new word.
     * @param tg the word
     * @param exp the explaination for the word
     */
    public void setWord(String tg, String exp) {
        Word word = new Word();
        word.setWordTarget(tg);
        word.setWordExplain(exp);
        words.add(word);
    }
}