import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class GGTranslateAPI {

    static boolean isEnglish = false;

    public static String translate(String lang1, String lang2, String text) throws IOException {

        if (isEnglish == true) {
            lang1 = "en";
        } else {
            lang2 = "vi";
        }

        String api = "https://script.google.com/macros/s/AKfycbyIiW5R7AElT4UMDPcZ6KPzWEfrT9z5YDt907aplU6YPn-wytciCKQhfV42WQadIU59/exec" + "?q="
                + URLEncoder.encode(text, "UTF-8")
                + "&target=" + lang2
                + "&source=" + lang1;

        URL url = new URL(api);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder output = new StringBuilder();

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader buffer = new BufferedReader(reader);
        String input;

        while((input = buffer.readLine()) != null) {
            output = output.append(input);
        }
        buffer.close();
        return output.toString();
    }
}
