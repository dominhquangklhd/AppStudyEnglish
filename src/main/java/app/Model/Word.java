package app.Model;

public class Word {
    private String wordTarget;
    private String wordExplain;

    public Word() {
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public String getWordTarget() {
        return this.wordTarget;
    }

    public String getWordExplain() {
        return this.wordExplain;
    }

    public void setWordTarget(String s) {
        this.wordTarget = s;
    }

    public void setWordExplain(String s) {
        this.wordExplain = s;
    }

    @Override
    public String toString() {
        return "English: " + wordTarget + "  -  Vietnamese: " + wordExplain;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Word) {
            return this.getWordTarget().equals(((Word) o).getWordTarget())
                    && this.getWordExplain().equals(((Word) o).getWordExplain());
        }
        else {
            return false;
        }
    }
}