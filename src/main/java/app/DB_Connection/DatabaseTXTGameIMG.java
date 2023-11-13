package app.DB_Connection;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DatabaseTXTGameIMG {
    public static final int NUmOfQuestionGameIMG = 10;
    public static final int NumOfTopic = 10;
    public static final int SPORTS = 1;
    public static final int BODY_PARTS = 2;
    public static final int ANIMAL = 3;
    public static final int JOBS = 4;
    public static final int UNIVERSE = 5;
    public static final int FOOD = 6;
    public static final int DRINKS = 7;
    public static final int TECHNOLOGY = 8;
    public static final int HOUSES = 9;
    public static final int SCHOOLS = 10;

    private List<String> listDBGame = new LinkedList<>();
    private List<String> listTopic = new LinkedList<>();
    private int numTopic = 0;

    public List<String> getListDBGame() {
        return listDBGame;
    }

    public List<String> getListTopic() {
        return listTopic;
    }

    public void readFileTXT(int topic) {
        String path = "txt/gameData.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (numTopic == 0) {
                    String[] split = line.split("-");
                    listTopic.addAll(Arrays.asList(split));
                    System.out.println(listTopic);
                }
                if (numTopic != topic) {
                    numTopic++;
                } else {
                    String[] split = line.split("-");
                    listDBGame.addAll(Arrays.asList(split));
                    Collections.shuffle(listDBGame);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
