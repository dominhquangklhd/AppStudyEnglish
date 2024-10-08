package app.API;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class GGTranslateAPI {

    public static boolean isEnglish = true;

    /**
     * Translates Vietnamese text to English or vice versa.
     * @param text the text to translate
     * @return the translated text
     */

    public static String translate(String text) {
        try {
            String lang1,lang2;
            if (isEnglish == true) {
                lang1 = "en";
                lang2 = "vi";
            } else {
                lang1 = "vi";
                lang2 = "en";
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
                output.append(input);
            }
            buffer.close();
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR TRANSLATING";
        }
    }
}
