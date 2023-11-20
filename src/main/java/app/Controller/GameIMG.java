package app.Controller;

import app.DB_Connection.DatabaseConnection;
import app.DB_Connection.DatabaseTXTGameIMG;
import app.Main;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class GameIMG implements Initializable {

    @FXML
    public AnchorPane scenePane;
    @FXML
    public Pane sub30Pane;
    @FXML
    public Pane sub60Pane;
    @FXML
    public Pane sub100Pane;
    @FXML
    private HBox answerChars;

    @FXML
    private HBox dataChars;

    @FXML
    private ImageView imageView = new ImageView();

    @FXML
    private ImageView actionPerry;

    @FXML
    private ImageView ansIMGView;

    @FXML
    private ImageView preAns;

    @FXML
    private ImageView nextAns;

    @FXML
    private Button buttonNext;

    @FXML
    private Label numQ;

    @FXML
    private Label score;

    @FXML
    private Label scoreFinish;

    @FXML
    private Label ansLabel;

    @FXML
    private Pane mainPaneGame;

    @FXML
    private Pane menuSettingPane;

    @FXML
    private Pane introducePane;

    @FXML
    private Pane finishPane;

    @FXML
    private Pane ansPane;

    @FXML
    private Pane topicPane;

    //Nor
    private EventHandler<ActionEvent> buttonClickHandler;
    private DatabaseTXTGameIMG databaseTXTGameIMG = new DatabaseTXTGameIMG();
    private Stage stage;
    private Scene scene;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
    private Image image;
    private List<String> ansGame = new LinkedList<>();
    private List<String> topicGame = new LinkedList<>();
    private static int curQ = 0;
    private int numScore = 0;
    private int numAns = 0;
    private String topicName = "";

    HashMap<Button, Boolean> buttonHashMap = new HashMap<>();
    private List<TranslateTransition> shakeList = new LinkedList<>();

    @FXML
    public void intoCheckAnsPane() {
        finishPane.setVisible(true);
        ansPane.setVisible(true);

        numAns = 0;
        ansLabel.setText(ansGame.get(numAns));
        setAnsPic();
    }

    //in check ans pane.
    public void setAnsPic() {
        String prepareLink = "file:src/main/resources/Image/GameIMG/";
        Image tmpIMG = new Image(prepareLink + topicName + "/" + ansGame.get(numAns) + ".jpg");
        ansIMGView.setImage(tmpIMG);
    }

    @FXML
    public void backToFinishPane() {
        finishPane.setVisible(true);
        ansPane.setVisible(false);
    }

    @FXML
    public void goNextAns() {
        if (numAns < DatabaseConnection.NUmOfQuestionGameIMG - 1) {
            numAns++;
            ansLabel.setText(ansGame.get(numAns));
            setAnsPic();
        }
    }

    @FXML
    public void goPreAns() {
        if (numAns > 0) {
            numAns--;
            ansLabel.setText(ansGame.get(numAns));
            setAnsPic();
        }
    }

    @FXML
    void intoSettingGame(MouseEvent event) {
        mainPaneGame.setDisable(true);
        menuSettingPane.setVisible(true);
    }

    public void topicCommon(int topic) {
        databaseTXTGameIMG.readFileTXT(topic);

        for (int i = 0; i < DatabaseTXTGameIMG.NUmOfQuestionGameIMG; i++) {
            ansGame.add(databaseTXTGameIMG.getListDBGame().get(i));
        }

        if (topicGame.isEmpty()) {
            for (int i = 0; i < DatabaseTXTGameIMG.NumOfTopic; i++) {
                topicGame.add(databaseTXTGameIMG.getListTopic().get(i));
            }
        }

        topicName = topicGame.get(topic - 1);
        score.setText(numScore + "");

        topicPane.setVisible(false);
        mainPaneGame.setVisible(true);

        questionCommon();
    }

    @FXML
    void intoAnimalTopic() {
        topicCommon(DatabaseTXTGameIMG.ANIMAL);
    }

    @FXML
    void intoSportsTopic() {
        topicCommon(DatabaseTXTGameIMG.SPORTS);
    }

    @FXML
    void intoBodyPartsTopic() {
        topicCommon(DatabaseTXTGameIMG.BODY_PARTS);
    }

    @FXML
    void intoJobsTopic() {
        topicCommon(DatabaseTXTGameIMG.JOBS);
    }

    @FXML
    void intoUniverseTopic() {
        topicCommon(DatabaseTXTGameIMG.UNIVERSE);
    }

    @FXML
    void intoFoodTopic() {
        topicCommon(DatabaseTXTGameIMG.FOOD);
    }

    @FXML
    void intoDrinksTopic() {
        topicCommon(DatabaseTXTGameIMG.DRINKS);
    }

    @FXML
    void intoTechnologyTopic() {
        topicCommon(DatabaseTXTGameIMG.TECHNOLOGY);
    }

    @FXML
    void intoHousesTopic() {
        topicCommon(DatabaseTXTGameIMG.HOUSES);
    }

    @FXML
    void intoSchoolsTopic() {
        topicCommon(DatabaseTXTGameIMG.SCHOOLS);
    }

    @FXML
    void backToMenuGame(MouseEvent event) throws IOException {
        resetDataGame();

        ((Pane) Main.root).getChildren().clear();
        Main.root = loader.load();

        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(root);
        Main.scene.setRoot(Main.root);
        //stage.setScene (scene);
        //stage.show();
    }

    @FXML
    void continueGame(MouseEvent event) {
        mainPaneGame.setDisable(false);
        menuSettingPane.setVisible(false);
    }

    @FXML
    void restartGame(MouseEvent event) {
        mainPaneGame.setVisible(false);
        topicPane.setVisible(true);
        finishPane.setVisible(false);
        resetDataGame();
        startGameIMG();
    }

    public void resetDataGame() {
        mainPaneGame.setDisable(false);
        menuSettingPane.setVisible(false);
        buttonNext.setText("next");

        curQ = 0;
        numScore = 0;

        ansGame.clear();

        databaseTXTGameIMG = new DatabaseTXTGameIMG();
    }

    public void setShakeTransition(TranslateTransition shakeTransition, Button button) {
        shakeTransition.setNode(button);
        shakeTransition.setDuration(Duration.millis(100));
        shakeTransition.setCycleCount(2);
        shakeTransition.setByX(button.getTranslateX() + 5);
        shakeTransition.setAutoReverse(true);
    }

    public void checkAns() {
        if (dataChars.getChildren().isEmpty()) {
            String check = "";
            shakeList.clear();
            for (javafx.scene.Node node : answerChars.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    check += button.getText();

                    // tạo hiệu ứng rung cho các button.
                    TranslateTransition shakeTransition = new TranslateTransition();
                    shakeList.add(shakeTransition);
                    setShakeTransition(shakeTransition, button);
                }
            }

            if (check.equals(ansGame.get(curQ))) {
                numScore += 10;
                score.setText(numScore + "");
                nextQuestion();
            }
            else {
                // rung button.
                for (TranslateTransition t : shakeList) {
                    t.play();
                }
            }
        }
    }

    public void clickMouse() {
        // xử lý click chuột vào chữ cái.
        buttonClickHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getTarget() instanceof Button) {
                    Button clickedButton = (Button) event.getTarget();
                    if (buttonHashMap.get(clickedButton)) {
                        answerChars.getChildren().add(clickedButton);
                        dataChars.getChildren().remove(clickedButton);
                        buttonHashMap.put(clickedButton, false);
                    } else {
                        answerChars.getChildren().remove(clickedButton);
                        dataChars.getChildren().add(clickedButton);
                        buttonHashMap.put(clickedButton, true);
                    }
                }

                checkAns();
            }
        };
    }

    /**
     * Sets up questions.
     */
    public void questionCommon() {
        String prepareLink = "file:src/main/resources/Image/GameIMG/";
        image = new Image(prepareLink + topicName + "/" + ansGame.get(curQ) + ".jpg");
        imageView.setImage(image);

        // trộn chữ.
        List<Character> charList = new ArrayList<>();
        for (char c : ansGame.get(curQ).toCharArray()) {
            charList.add(c);
        }
        Collections.shuffle(charList);

        clickMouse();

        // add button chữ cái.
        answerChars.getChildren().clear();
        dataChars.getChildren().clear();
        for (char c : charList) {
            String charAsString = String.valueOf(c);
            Button button = new Button(charAsString);
            button.setPrefSize(40, 40);
            button.setStyle("-fx-background-color: #F8DE22; " +
                    "-fx-background-radius: 5px; " +
                    //"-fx-border-color: BLACK; " +
                    //"-fx-border-radius: 5px; " +
                    //"-fx-border-width: 1; " +
                    "-fx-cursor: hand;" +
                    "-fx-font-family: 'Arial Rounded MT Bold'; " +
                    "-fx-font-size: 16px;");


            buttonHashMap.put(button, true);
            button.setOnAction(buttonClickHandler);

            dataChars.getChildren().add(button);
        }

        numQ.setText(curQ + 1 + "/" + DatabaseTXTGameIMG.NUmOfQuestionGameIMG);
    }

    @FXML
    public void startGameIMG() {
        introducePane.setVisible(false);
        topicPane.setVisible(true);
    }

    @FXML
    public void nextQuestion() {
        if (curQ < DatabaseConnection.NUmOfQuestionGameIMG - 1) {
            curQ++;
            questionCommon();
        } else {
            showFinishGame();
        }
    }

    public void showFinishGame() {
        finishPane.setVisible(true);
        scoreFinish.setText("Your score : " + String.valueOf(numScore));
        if (numScore <= 30) {
            sub30Pane.setVisible(true);
            sub60Pane.setVisible(false);
            sub100Pane.setVisible(false);
        } else if (numScore <= 60) {
            sub30Pane.setVisible(false);
            sub60Pane.setVisible(true);
            sub100Pane.setVisible(false);
        } else {
            sub30Pane.setVisible(false);
            sub60Pane.setVisible(false);
            sub100Pane.setVisible(true);
        }
    }

    public void minimizeStage(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void intoOut() throws IOException {
        Main.dictionaryManagement.historyExportToFile();
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPaneGame.setVisible(false);
        menuSettingPane.setVisible(false);
        introducePane.setVisible(true);
        finishPane.setVisible(false);
        ansPane.setVisible(false);
        topicPane.setVisible(false);
    }
}