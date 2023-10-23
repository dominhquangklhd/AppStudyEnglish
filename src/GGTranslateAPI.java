import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class GGTranslateAPI {
    public static void CreateTranslator() throws IOException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Nhập vào văn bản muốn xử lí:");
        String text = scan.nextLine();
        System.out.println("Nhập vào ngôn ngữ của văn bản muốn xử lí:");
        String lang1 = scan.nextLine();
        System.out.println("Nhập vào ngôn ngữ muốn dịch sang:");
        String lang2 = scan.nextLine();

        System.out.println("Văn bản được dịch thành: " + translate(lang1, lang2, text));

    }

    public static String translate(String lang1, String lang2, String text) throws IOException {
        String api = "https://script.google.com/macros/s/AKfycbyIiW5R7AElT4UMDPcZ6KPzWEfrT9z5YDt907aplU6YPn-wytciCKQhfV42WQadIU59/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + lang2 +
                "&source=" + lang1;

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
