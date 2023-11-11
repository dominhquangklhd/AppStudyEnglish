package app.Controller;

import app.DB_Connection.DatabaseConnection;
import javafx.animation.TranslateTransition;
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

    private EventHandler<ActionEvent> buttonClickHandler;
    private DatabaseConnection databaseGame = new DatabaseConnection();
    private Stage stage;
    private Scene scene;
    private Image image;
    private List<String> ansGame;
    private static int curQ = 0;
    private int numScore = 0;
    private int numAns = 0;

    HashMap<Button, Boolean> buttonHashMap = new HashMap<>();
    private List<TranslateTransition> shakeList = new LinkedList<>();

    @FXML
    public void intoCheckAnsPane() {
        finishPane.setVisible(false);
        ansPane.setVisible(true);

        numAns = 0;
        ansLabel.setText(ansGame.get(numAns));
        setAnsPic();
    }

    //in check ans pane.
    public void setAnsPic() {
        String prepareLink = "file:src/main/resources/Image/GameIMG/";
        Image tmpIMG = new Image(prepareLink + ansGame.get(numAns) + ".jpg");
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

    @FXML
    void backToMenuGame(MouseEvent event) throws IOException {
        resetDataGame();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    @FXML
    void continueGame(MouseEvent event) {
        mainPaneGame.setDisable(false);
        menuSettingPane.setVisible(false);
    }

    @FXML
    void restartGame(MouseEvent event) throws SQLException {
        mainPaneGame.setDisable(false);
        buttonNext.setVisible(true);
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

        databaseGame.DatabaseClose();
        databaseGame = new DatabaseConnection();
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

    // set up question.
    public void questionCommon() {
        String prepareLink = "file:src/main/resources/Image/GameIMG/";
        image = new Image(prepareLink + ansGame.get(curQ) + ".jpg");
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

            buttonHashMap.put(button, true);
            button.setOnAction(buttonClickHandler);

            dataChars.getChildren().add(button);
        }

        numQ.setText(curQ + 1 + "/10");
    }

    @FXML
    public void startGameIMG() throws SQLException {
        databaseGame.createConnection();
        databaseGame.dataGameIMG();
        ansGame = databaseGame.getAnsGameIMG();
        score.setText(numScore + "");

        introducePane.setVisible(false);
        mainPaneGame.setVisible(true);

        questionCommon();
    }

    @FXML
    public void nextQuestion() {
        if (curQ < DatabaseConnection.NUmOfQuestionGameIMG - 1) {
            curQ++;
            if (curQ == DatabaseConnection.NUmOfQuestionGameIMG - 1) {
                buttonNext.setText("finish");
            }
            questionCommon();
        } else {
            showFinishGame();
        }
    }

    public void showFinishGame() {
        mainPaneGame.setDisable(true);
        finishPane.setVisible(true);
        scoreFinish.setText(numScore + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         mainPaneGame.setVisible(false);
         menuSettingPane.setVisible(false);
         introducePane.setVisible(true);
         finishPane.setVisible(false);
         ansPane.setVisible(false);
    }
}
