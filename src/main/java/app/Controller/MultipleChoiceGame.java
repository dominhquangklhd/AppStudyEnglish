package app.Controller;

import app.DB_Connection.DatabaseConnection;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MultipleChoiceGame implements Initializable {

    @FXML
    private Label ansALabel;

    @FXML
    private Label ansBLabel;

    @FXML
    private Label ansCLabel;

    @FXML
    private Label ansDLabel;

    @FXML
    private Pane introducePane;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane pausePane;

    @FXML
    private Pane finishPane;

    @FXML
    private Pane checkAnsPane;

    @FXML
    private Label quesLabel;

    @FXML
    private Label numQLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label finishScoreLabel;

    @FXML
    private Label finishQuesLabel;

    @FXML
    private Label finishAnsLabel;

    private Stage stage;
    private Scene scene;
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;
    private List<List<String>> dataGame = new LinkedList<>();
    private List<String> ansDetails = new LinkedList<>();
    private List<String> quesInGame = new LinkedList<>();
    private int numQ = 0;
    private int score = 0;
    private int numQCheckAns = 0;
    private final String green = "#A6FF96";
    private final String red = "#FF7676";
    private boolean canClick = true;

    @FXML
    void startGame(MouseEvent event) throws SQLException {
        databaseConnection.createConnection();
        databaseConnection.gameDataBaseMultipleChoice();

        dataGame = databaseConnection.getListDB_MC();

        setUpQuestion();

        introducePane.setVisible(false);
        mainPane.setVisible(true);
    }

    @FXML
    void intoCheckAns() {
        checkAnsPane.setVisible(true);
        finishPane.setVisible(false);

        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    @FXML
    void preAns() {
        if (numQCheckAns > 0) numQCheckAns--;
        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    @FXML
    void nextAns() {
        if (numQCheckAns < DatabaseConnection.NumOfQuestionMC - 1) numQCheckAns++;
        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    @FXML
    void exitCheckAnsPane() {
        checkAnsPane.setVisible(false);
        finishPane.setVisible(true);
    }

    public void setUpQuestion() {
        if (numQ == DatabaseConnection.NumOfQuestionMC) {
            numQ = 0;
            mainPane.setDisable(true);
            finishPane.setVisible(true);
            scoreLabel.setText(score + "");
            finishScoreLabel.setText(score + "");
        } else {
            question = dataGame.get(numQ).get(0);
            A = dataGame.get(numQ).get(1);
            B = dataGame.get(numQ).get(2);
            C = dataGame.get(numQ).get(3);
            D = dataGame.get(numQ).get(4);
            answer = dataGame.get(numQ).get(5);

            quesInGame.add(question);
            switch (answer) {
                case "a" -> ansDetails.add(A);
                case "b" -> ansDetails.add(B);
                case "c" -> ansDetails.add(C);
                default -> ansDetails.add(D);
            }

            quesLabel.setText(question);
            ansALabel.setText(A);
            ansBLabel.setText(B);
            ansCLabel.setText(C);
            ansDLabel.setText(D);

            ansALabel.setVisible(true);
            ansBLabel.setVisible(true);
            ansCLabel.setVisible(true);
            ansDLabel.setVisible(true);

            BackgroundFill backgroundFill1 = new BackgroundFill(Color.web("#BEADFA"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background1 = new Background(backgroundFill1);
            ansALabel.setBackground(background1);

            BackgroundFill backgroundFill2 = new BackgroundFill(Color.web("#E1AA74"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background2 = new Background(backgroundFill2);
            ansBLabel.setBackground(background2);

            BackgroundFill backgroundFill3 = new BackgroundFill(Color.web("#D6D46D"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background3 = new Background(backgroundFill3);
            ansCLabel.setBackground(background3);

            BackgroundFill backgroundFill4 = new BackgroundFill(Color.web("#CDFAD5"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background4 = new Background(backgroundFill4);
            ansDLabel.setBackground(background4);

            scoreLabel.setText(score + "");
            numQLabel.setText(numQ + 1 + "/" + DatabaseConnection.NumOfQuestionMC);

            numQ++;
        }
    }

    @FXML
    void continueGame(MouseEvent event) {
        pausePane.setVisible(false);
        mainPane.setDisable(false);
    }

    @FXML
    void exitGame(MouseEvent event) throws IOException {
        databaseConnection.DatabaseClose();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }

    @FXML
    void pauseGame(MouseEvent event) {
        pausePane.setVisible(true);
        mainPane.setDisable(true);
    }

    @FXML
    void backToMenu(MouseEvent event) throws IOException {
        exitGame(event);
    }

    @FXML
    void restartGame(MouseEvent event) throws SQLException {
        finishPane.setVisible(false);
        mainPane.setDisable(false);

        score = 0;
        numQCheckAns = 0;
        numQ = 0;
        databaseConnection.DatabaseClose();
        dataGame.clear();
        ansDetails.clear();
        quesInGame.clear();
        databaseConnection = new DatabaseConnection();
        startGame(event);
    }

    public void rightAns(Label curLabel) {
        if (canClick) {
            score += 10;
        }
        BackgroundFill backgroundFill = new BackgroundFill(Color.web(green), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        curLabel.setBackground(background);
    }

    public void wrongAns(Label curLabel, Label rightLabel) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.web(red), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        curLabel.setBackground(background);

        BackgroundFill backgroundFill2 = new BackgroundFill(Color.web(green), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background2 = new Background(backgroundFill2);
        rightLabel.setBackground(background2);
    }

    public void delayClicked() {
        if (canClick) {
            canClick = false;

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                canClick = true;
                setUpQuestion();
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    @FXML
    void checkAnsA() {
        if (answer.equals("a")) {
            ansBLabel.setVisible(false);
            ansCLabel.setVisible(false);
            ansDLabel.setVisible(false);

            rightAns(ansALabel);
        } else {
            if (answer.equals("b")) {
                ansCLabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansALabel, ansBLabel);
            } else if (answer.equals("c")) {
                ansBLabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansALabel, ansCLabel);
            } else {
                ansCLabel.setVisible(false);
                ansBLabel.setVisible(false);
                wrongAns(ansALabel, ansDLabel);
            }
        }
        delayClicked();
    }

    @FXML
    void checkAnsB() throws InterruptedException {
        if (answer.equals("b")) {
            ansALabel.setVisible(false);
            ansCLabel.setVisible(false);
            ansDLabel.setVisible(false);

            rightAns(ansBLabel);
        } else {
            if (answer.equals("a")) {
                ansCLabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansBLabel, ansALabel);
            } else if (answer.equals("c")) {
                ansALabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansBLabel, ansCLabel);
            } else {
                ansCLabel.setVisible(false);
                ansALabel.setVisible(false);
                wrongAns(ansBLabel, ansDLabel);
            }
        }
        delayClicked();
    }

    @FXML
    void checkAnsC() {
        if (answer.equals("c")) {
            ansBLabel.setVisible(false);
            ansALabel.setVisible(false);
            ansDLabel.setVisible(false);

            rightAns(ansCLabel);
        } else {
            if (answer.equals("a")) {
                ansBLabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansCLabel, ansALabel);
            } else if (answer.equals("b")) {
                ansALabel.setVisible(false);
                ansDLabel.setVisible(false);
                wrongAns(ansCLabel, ansBLabel);
            } else {
                ansALabel.setVisible(false);
                ansBLabel.setVisible(false);
                wrongAns(ansCLabel, ansDLabel);
            }
        }
        delayClicked();
    }

    @FXML
    void checkAnsD() {
        if (answer.equals("d")) {
            ansBLabel.setVisible(false);
            ansALabel.setVisible(false);
            ansCLabel.setVisible(false);

            rightAns(ansDLabel);
        } else {
            if (answer.equals("a")) {
                ansCLabel.setVisible(false);
                ansBLabel.setVisible(false);
                wrongAns(ansDLabel, ansALabel);
            } else if (answer.equals("c")) {
                ansBLabel.setVisible(false);
                ansALabel.setVisible(false);
                wrongAns(ansDLabel, ansCLabel);
            } else {
                ansCLabel.setVisible(false);
                ansALabel.setVisible(false);
                wrongAns(ansDLabel, ansBLabel);
            }
        }
        delayClicked();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        introducePane.setVisible(true);
        mainPane.setVisible(false);
        pausePane.setVisible(false);
        finishPane.setVisible(false);
        checkAnsPane.setVisible(false);
    }
}
