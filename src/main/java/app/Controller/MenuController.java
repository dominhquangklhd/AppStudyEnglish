package app.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends BaseController implements Initializable {
    //FXML
    @FXML
    public Label insertWord;
    @FXML
    public Label editWord;
    @FXML
    public Label deleteWord;
    @FXML
    public ImageView Out;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Nothing
    }
}
