package app.Model;

import java.io.IOException;

public class MainCommandLine {

    public static void main(String[] args) throws IOException {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();

        while (!DictionaryManagement.exit) {
            dictionaryCommandline.dictionaryManagement.dictionaryAdvanced();
        }
    }
}
