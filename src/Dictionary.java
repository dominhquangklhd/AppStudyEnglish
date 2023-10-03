import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public List<Word> words = new ArrayList<>();

    Dictionary() {

    }

    public void setWord(String tg, String exp) {
        Word word = new Word();
        word.setWordTarget(tg);
        word.setWordExplain(exp);
        words.add(word);
    }
}
