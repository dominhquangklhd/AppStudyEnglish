package app.Controller;

import app.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuGame extends BaseController {
    private FXMLLoader GameIMGloader = new FXMLLoader(getClass().getResource("/FXML/GameIMG.fxml"));
    private FXMLLoader MCloader = new FXMLLoader(getClass().getResource("/FXML/MultipleChoiceGame.fxml"));

    /**
     * Gets into the Catch Picture game.
     *
     * @throws IOException when cannot load the FXMLLoader GameIMGloader
     */
    public void intoGameImg() throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = GameIMGloader.load();

        Main.scene.setRoot(Main.root);
    }

    /**
     * Gets into the Multiple Choice game.
     *
     * @param event the event when user click the Multiple Choice game image
     * @throws IOException when cannot load the FXMLLoader MCloader
     */
    public void intoMCGame(MouseEvent event) throws IOException {
        ((Pane) Main.root).getChildren().clear();
        Main.root = MCloader.load();

        Main.scene.setRoot(Main.root);
    }

}

