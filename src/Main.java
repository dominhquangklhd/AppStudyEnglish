import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DictionaryManagement tmp = new DictionaryManagement();
        tmp.createNewFile();
        tmp.insertFromFile();

    }
}
