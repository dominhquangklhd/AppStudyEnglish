package app.Controller;

import app.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SettingController {
    //FXML
    @FXML
    public AnchorPane insertPane;
    @FXML
    public AnchorPane editPane;
    @FXML
    public AnchorPane deletePane;
    @FXML
    public AnchorPane InsertwarningPane;
    @FXML
    public TextField wordInsert;
    @FXML
    public TextField typeInsert;
    @FXML
    public TextField ipaInsert;
    @FXML
    public ImageView preType;
    @FXML
    public ImageView nextType;
    @FXML
    public ImageView preDef;
    @FXML
    public ImageView nextDef;
    @FXML
    public TextArea definitionInsert;
    @FXML
    public Label insert;
    @FXML
    public Label edit;
    @FXML
    public TextField wordDelete;
    @FXML
    public Label delete;
    @FXML
    public Label typeLabelinsert;
    @FXML
    public Label defLabelinsert;
    @FXML
    public Text editTitle;
    @FXML
    public Text insertTitle;
    @FXML
    public AnchorPane DeleteWarning;
    @FXML
    public ImageView cancelDelete;
    @FXML
    public AnchorPane EditWarning;
    @FXML
    public ImageView cancelEdit;
    @FXML
    public ImageView insertAnyway;
    @FXML
    public AnchorPane InsertSuccessPane;
    @FXML
    public ImageView insertSuccess;
    @FXML
    public AnchorPane EditSuccessPane;
    @FXML
    public ImageView editSuccess;
    @FXML
    public AnchorPane DeleteSuccessPane;
    @FXML
    public ImageView deleteSucess;
    @FXML
    public AnchorPane RegexWarningPane;

    //Nor
    public List<String> types = new ArrayList<>();
    public List<List<String>> definitions = new ArrayList<>();

    public int typeIndex = 0;
    public int definitionIndex = 0;

    //Control methods
    public void insert() throws SQLException {
        if (!wordInsert.getText().equals("")) {
            if (!wordInsert.getText().matches("^[a-z ]*$")) {
                RegexWarningPane.setVisible(true);
                return;
            }
            String target = wordInsert.getText().toLowerCase();
            String IPA = ipaInsert.getText();
            if (!Main.databaseConnection.hasInDatabase(target)) {
                if (!typeInsert.getText().equals("")){
                    if (typeIndex >= types.size()) {
                        types.add(typeInsert.getText());
                    }
                    if (!definitionInsert.getText().equals("") ) {
                        if (definitionIndex >= definitions.get(typeIndex).size()) {
                            definitions.get(typeIndex).add(definitionInsert.getText());
                        }
                        else if (definitions.get(typeIndex).get(definitionIndex) != definitionInsert.getText()) {
                            definitions.get(typeIndex).remove(definitionIndex);
                            definitions.get(typeIndex).add(definitionIndex, definitionInsert.getText());
                        }
                    }
                }
                Main.databaseConnection.insertToDatabase(target, IPA, types, definitions);
                Main.trie.insertWord(target);
                /*System.out.println(target);
                System.out.println(IPA);
                for (int i = 0; i < types.size(); i++) {
                    System.out.println(types.get(i));
                    for (int j = 0; j < definitions.get(i).size(); j++) {
                        System.out.println(definitions.get(i).get(j));
                    }
                }*/
                InsertSuccessPane.setVisible(true);
            } else {
                InsertwarningPane.setVisible(true);
            }
        }
    }

    public void cancelInsert() {
        InsertwarningPane.setVisible(false);
        RegexWarningPane.setVisible(false);
    }

    public void lookUp(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Search.fxml"));
        Parent root = loader.load();
        ((SearchController) loader.getController()).getWordTarget().setText(wordInsert.getText());
        ((SearchController) loader.getController()).StartSearching();

        //Switch scene to SearchScene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene (scene);
        stage.show();
    }


    public void edit() throws SQLException {
        if (!wordInsert.getText().equals("")) {
            if (!wordInsert.getText().matches("^[a-z ]*$")) {
                RegexWarningPane.setVisible(true);
                return;
            }
            if (!Main.databaseConnection.hasInDatabase(wordInsert.getText())) {
                EditWarning.setVisible(true);
            } else {
                Main.databaseConnection.deleteWordInDatabase(wordInsert.getText());
                String target = wordInsert.getText().toLowerCase();
                String IPA = ipaInsert.getText();
                if (!typeInsert.getText().equals("")){
                    if (typeIndex >= types.size()) {
                        types.add(typeInsert.getText());
                    }
                    if (!definitionInsert.getText().equals("") ) {
                        if (definitionIndex >= definitions.get(typeIndex).size()) {
                            definitions.get(typeIndex).add(definitionInsert.getText());
                        }
                        else if (definitions.get(typeIndex).get(definitionIndex) != definitionInsert.getText()) {
                            definitions.get(typeIndex).remove(definitionIndex);
                            definitions.get(typeIndex).add(definitionIndex, definitionInsert.getText());
                        }
                    }
                }
                Main.databaseConnection.insertToDatabase(target, IPA, types, definitions);
                Main.trie.insertWord(target);
                EditSuccessPane.setVisible(true);
            }
        }
    }

    public void cancelEdit() {
        EditWarning.setVisible(false);
    }

    public void delete() throws SQLException {
        String target = wordDelete.getText().toLowerCase();
        if (!wordDelete.getText().equals("")) {
            if (Main.databaseConnection.hasInDatabase(target)) {
                Main.databaseConnection.deleteWordInDatabase(target);
                DeleteSuccessPane.setVisible(true);
            } else {
                DeleteWarning.setVisible(true);
            }
        }
        if (Main.dictionaryManagement.wordHistoryList.contains(target)) {
            Main.dictionaryManagement.wordHistoryList.remove(target);
        }
        if (Main.dictionaryManagement.wordSavedList.contains(target)) {
            Main.dictionaryManagement.wordSavedList.remove(target);
        }
    }

    //Supporting methods
    public void StartInsert() {
        insertPane.setVisible(true);
        insertTitle.setVisible(true);
        insert.setVisible(true);
        edit.setVisible(false);
        InsertwarningPane.setVisible(false);
        EditWarning.setVisible(false);
        InsertSuccessPane.setVisible(false);
        EditSuccessPane.setVisible(false);
        editTitle.setVisible(false);
        deletePane.setVisible(false);

        wordInsert.setText("");
        ipaInsert.setText("");
        typeInsert.setText("");
        definitionInsert.setText("");
        typeLabelinsert.setText("Type 1");
        defLabelinsert.setText("Definition 1");
        preDef.setVisible(false);
        preType.setVisible(false);

        types.clear();
        definitions.clear();
        typeIndex = 0;
        definitionIndex = 0;

        List<String> tmp = new ArrayList<>();
        definitions.add(tmp);
    }

    public void StartEdit() {
        insertPane.setVisible(true);
        insertTitle.setVisible(false);
        insert.setVisible(false);
        edit.setVisible(true);
        InsertwarningPane.setVisible(false);
        EditWarning.setVisible(false);
        InsertSuccessPane.setVisible(false);
        EditSuccessPane.setVisible(false);
        editTitle.setVisible(true);
        deletePane.setVisible(false);

        wordInsert.setText("");
        ipaInsert.setText("");
        typeInsert.setText("");
        definitionInsert.setText("");
        typeLabelinsert.setText("Type 1");
        defLabelinsert.setText("Definition 1");
        preDef.setVisible(false);
        preType.setVisible(false);

        types.clear();
        definitions.clear();
        typeIndex = 0;
        definitionIndex = 0;

        List<String> tmp = new ArrayList<>();
        definitions.add(tmp);
    }

    public  void StartDelete() {
        insertPane.setVisible(false);
        DeleteWarning.setVisible(false);
        deletePane.setVisible(true);
        DeleteSuccessPane.setVisible(false);

        wordDelete.setText("");

        types.clear();
        definitions.clear();
    }

    public void toOtherType(MouseEvent event) {
        preDef.setVisible(false);
        defLabelinsert.setText("Definition 1");

        boolean deleteType = false;

        if (typeInsert.getText().equals("")) {
            if (typeIndex < types.size()) {
                types.remove(typeIndex);
                definitions.remove(typeIndex);
            }
            deleteType = true;
        } else {
            if (!definitionInsert.getText().equals("") ) {
                if (definitionIndex >= definitions.get(typeIndex).size()) {
                    definitions.get(typeIndex).add(definitionInsert.getText());
                }
                else if (definitions.get(typeIndex).get(definitionIndex) != definitionInsert.getText()) {
                    definitions.get(typeIndex).remove(definitionIndex);
                    definitions.get(typeIndex).add(definitionIndex, definitionInsert.getText());
                }
            }
            if (typeIndex <= types.size() - 1) {
                types.remove(typeIndex);
                types.add(typeIndex, typeInsert.getText());
            } else {
                types.add(typeInsert.getText());
            }
        }

        definitionIndex = 0;

        if (event.getSource().equals(nextType)) {
            if (!deleteType) typeIndex++;
            if (typeIndex > 0) {
                preType.setVisible(true);
            }
            typeLabelinsert.setText("Type " + String.valueOf(typeIndex + 1));
            if (typeIndex > types.size() - 1) {
                typeInsert.clear();
                definitionInsert.clear();
                List<String> tmp = new ArrayList<>();
                definitions.add(tmp);
            } else {
                typeInsert.setText(types.get(typeIndex));
                definitionInsert.setText(definitions.get(typeIndex).get(0));
            }
        } else {
            typeIndex--;
            nextType.setVisible(true);
            if (typeIndex == 0) {
                preType.setVisible(false);
            }
            typeLabelinsert.setText("Type " + String.valueOf(typeIndex + 1));
            typeInsert.setText(types.get(typeIndex));
            if (definitionIndex == 0 && definitionInsert.getText().equals(""))
                return;
            definitionInsert.setText(definitions.get(typeIndex).get(0));
        }
    }

    public void toOtherDef(MouseEvent event) {
        boolean deleteDef = false;

        if (definitionInsert.getText().equals("")) {
            if (definitionIndex < definitions.get(typeIndex).size()) {
                definitions.get(typeIndex).remove(definitionIndex);
            }
            deleteDef = true;
        } else {
            if (definitionIndex <= definitions.get(typeIndex).size() - 1) {
                definitions.get(typeIndex).remove(definitionIndex);
                definitions.get(typeIndex).add(definitionIndex, definitionInsert.getText());
            } else definitions.get(typeIndex).add(definitionInsert.getText());
        }

        if (event.getSource().equals(nextDef)) {
            if (!deleteDef) definitionIndex++;
            if (definitionIndex > 0) {
                preDef.setVisible(true);
            }
            defLabelinsert.setText("Definition " + String.valueOf(definitionIndex + 1));
            if (definitionIndex > definitions.get(typeIndex).size() - 1) {
                definitionInsert.clear();
            } else {
                definitionInsert.setText(definitions.get(typeIndex).get(definitionIndex));
            }
        } else {
            definitionIndex--;
            if (definitionIndex == 0) {
                preDef.setVisible(false);
            }
            defLabelinsert.setText("Definition " + String.valueOf(definitionIndex + 1));
            definitionInsert.setText(definitions.get(typeIndex).get(definitionIndex));
        }
    }
}
