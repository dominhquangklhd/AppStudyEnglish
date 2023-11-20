package app.Controller;

import app.DB_Connection.DatabaseConnection;
import app.Main;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
    //FXML
    @FXML
    public Pane sub50Pane;
    @FXML
    public Pane sub100Pane;
    @FXML
    public Pane sub150Pane;

    @FXML
    private AnchorPane scenePane;

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

    //Nor
    private Stage stage;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MenuGame.fxml"));
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

    /**
     * Starts the Multiple Choice game using data from the MySQL database.
     *
     * @throws SQLException when cannot get the data from the database
     */
    @FXML
    void startGame() throws SQLException {
        Main.databaseConnection.gameDataBaseMultipleChoice();

        dataGame = Main.databaseConnection.getListDB_MC();

        setUpQuestion();

        introducePane.setVisible(false);
        mainPane.setVisible(true);
    }

    /**
     * Checks the answer after finishing a game.
     */
    @FXML
    void intoCheckAns() {
        checkAnsPane.setVisible(true);

        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    /**
     * Checks the previous answer.
     */
    @FXML
    void preAns() {
        if (numQCheckAns > 0) numQCheckAns--;
        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    /**
     * Checks the next answer.
     */
    @FXML
    void nextAns() {
        if (numQCheckAns < DatabaseConnection.NumOfQuestionMC - 1) numQCheckAns++;
        finishQuesLabel.setText(quesInGame.get(numQCheckAns));
        finishAnsLabel.setText(ansDetails.get(numQCheckAns));
    }

    /**
     * Stops checking the answers.
     */
    @FXML
    void exitCheckAnsPane() {
        checkAnsPane.setVisible(false);
        finishPane.setVisible(true);
    }

    /**
     * Sets up the question and its answers with data from the database and presents the finish pane
     * when the question index (numQ) is equal to the number of questions.
     */
    public void setUpQuestion() {
        if (numQ == DatabaseConnection.NumOfQuestionMC) {
            //Sets up when finishing the game.
            numQ = 0;
            finishPane.setVisible(true);
            finishScoreLabel.setText("Your score : " + String.valueOf(score));
            if (score <= 50) {
                sub50Pane.setVisible(true);
                sub100Pane.setVisible(false);
                sub150Pane.setVisible(false);
            } else if (score <= 100) {
                sub50Pane.setVisible(false);
                sub100Pane.setVisible(true);
                sub150Pane.setVisible(false);
            } else {
                sub50Pane.setVisible(false);
                sub100Pane.setVisible(false);
                sub150Pane.setVisible(true);
            }
        } else {
            //Sets up the question
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

            //Design.
            BackgroundFill backgroundFill1 = new BackgroundFill(Color.web("rgb(42, 100, 160)"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background1 = new Background(backgroundFill1);
            ansALabel.setBackground(background1);

            BackgroundFill backgroundFill2 = new BackgroundFill(Color.web("rgb(36, 130, 139)"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background2 = new Background(backgroundFill2);
            ansBLabel.setBackground(background2);

            BackgroundFill backgroundFill3 = new BackgroundFill(Color.web("rgb(178, 127, 33)"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background3 = new Background(backgroundFill3);
            ansCLabel.setBackground(background3);

            BackgroundFill backgroundFill4 = new BackgroundFill(Color.web("rgb(118, 46, 59)"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
            Background background4 = new Background(backgroundFill4);
            ansDLabel.setBackground(background4);

            scoreLabel.setText("  Your score : " + String.valueOf(score));
            numQLabel.setText(numQ + 1 + "/" + DatabaseConnection.NumOfQuestionMC);

            numQ++;
        }
    }

    /**
     * Continues the game after pausing.
     */
    @FXML
    void continueGame() {
        pausePane.setVisible(false);
        mainPane.setDisable(false);
    }

    /**
     * Exits the game.
     *
     * @throws IOException when cannot load the FXMLLoader loader
     */
    @FXML
    void exitGame() throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = loader.load();

        Main.scene.setRoot(Main.root);
    }

    /**
     * Pauses the game.
     */
    @FXML
    void pauseGame() {
        pausePane.setVisible(true);
        mainPane.setDisable(true);
    }

    /**
     *Exits the game.
     *
     * @throws IOException contains in the exitGame() method.
     */
    @FXML
    void backToMenu() throws IOException {
        exitGame();
    }

    /**
     *Restarts the game.
     *
     *@throws IOException contains in the startGame() method.
     */
    @FXML
    void restartGame() throws SQLException {
        finishPane.setVisible(false);
        mainPane.setDisable(false);

        score = 0;
        numQCheckAns = 0;
        numQ = 0;

        dataGame.clear();
        ansDetails.clear();
        quesInGame.clear();

        startGame();
    }

    /**
     * Sets the answers to green if it is the right answer.
     *
     * @param curLabel the label contains the word that was chosen
     */
    public void rightAns(Label curLabel) {
        if (canClick) {
            score += 10;
        }
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#419662"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        curLabel.setBackground(background);
    }

    /**
     * Sets the answers to red if it is the wrong answer and sets the right answer to green.
     *
     * @param curLabel the label contains the word that was chosen
     * @param rightLabel the label contains the correct answer
     */
    public void wrongAns(Label curLabel, Label rightLabel) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#fd5b3c"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background = new Background(backgroundFill);
        curLabel.setBackground(background);

        BackgroundFill backgroundFill2 = new BackgroundFill(Color.web("#419662"), CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY);
        Background background2 = new Background(backgroundFill2);
        rightLabel.setBackground(background2);
    }

    /**
     * Has the scene delays after selecting an answer.
     */
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

    /**
     * Checks if the selected choice is A.
     */
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

    /**
     * Checks if the selected choice is B.
     */
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

    /**
     * Checks if the selected choice is C.
     */
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

    /**
     * Checks if the selected choice is D.
     */
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

    /**
     * Minimize the window.
     *
     * @param event the event when user click the minimize button
     */
    public void minimizeStage(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Closes the window.
     */
    public void intoOut() throws IOException {
        Main.dictionaryManagement.historyExportToFile();
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
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
