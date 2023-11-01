
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.player.Player;
import java.util.Scanner;

public class TextToSpeech {
    static boolean isEnglish = true;

    /**
     * Speak the text in Vietnamese or English.
     *
     * @param text text The text to speak
     * @param isEnglish whether the text is English or not
     */

    public static void playVoice(String text, boolean isEnglish) {
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
                    + "&f=48khz_16bit_mono"
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
        String text = "I love you so much";
        playVoice(text, true);
    }
}