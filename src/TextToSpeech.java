
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.player.Player;
import java.util.Scanner;

public class TextToSpeech {
    static boolean isEnglish = false;

    public static void playVoice(String text) {
        try {
            String lang;

            if (isEnglish == true) {
                lang = "en-us";
            } else {
                lang = "vi-vn";
            }

            String api = "http://api.voicerss.org/?key=802ae04d7bab4141af14e5465b231bba"
                    + "&hl=" + lang
                    + "&c=MP3"
                    + "&src=" + text.replace(" ", "-");

            URL url = new URL(api);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream audio = connection.getInputStream();
            new Player(audio).play();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String text = scan.nextLine();
        playVoice(text);
    }
}