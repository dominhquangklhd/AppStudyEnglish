package app.API;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javazoom.jl.player.Player;
import java.util.Scanner;

public class TextToSpeech {
    public static boolean SpeakEnglish = true;

    /**
     * Speaks the text in Vietnamese or English.
     *
     * @param text text The text to speak
     */

    public static void playVoice(String text) {
        try {
            String lang;

            if (SpeakEnglish) {
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

            InputStream speaker = connection.getInputStream();
            Player player = new Player(speaker);
            player.play();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}