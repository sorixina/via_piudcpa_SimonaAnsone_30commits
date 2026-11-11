package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

public class TagsListController {

    @FXML
    private ImageView characterimagefour;

    @FXML
    private ImageView characterimageone;

    @FXML
    private ImageView characterimagethree;

    @FXML
    private ImageView characterimagetwo;

    @FXML
    private Text characternamefour;

    @FXML
    private Text characternameone;

    @FXML
    private Text characternamethree;

    @FXML
    private Text characternametwo;

    @FXML
    private Text gobackbtn;

    @FXML
    private AnchorPane scrollbar;

    @FXML
    private Button tagfemalebtn;

    @FXML
    private Button taghumanbtn;

    @FXML
    private Label taglabel;

    @FXML
    private Button tagmalebtn;

    @FXML
    private Button tagnonhumanbtn;

    public void setTag(String tag) {
        taglabel.setText(tag);
        loadCharactersForTag(tag);
    }

    @FXML
    private void initialize() {
        gobackbtn.setOnMouseClicked(e -> {
            Methods.openHomepage();
            closeThisWindow();
        });
    }

    private void closeThisWindow() {
        Stage stage = (Stage) gobackbtn.getScene().getWindow();
        stage.close();
    }

    private void loadCharactersForTag(String tag) {

        List<CharacterProfileData> list = Methods.getCharactersByTag(tag);

        if (list.isEmpty()) {
            characternameone.setText("No characters");
            characternametwo.setText("");
            characternamethree.setText("");
            characternamefour.setText("");
            return;
        }

        Collections.shuffle(list);

        if (list.size() > 0) {
            CharacterProfileData c = list.get(0);
            characternameone.setText(c.getName());
            if (c.getImageBytes() != null)
                characterimageone.setImage(new Image(new ByteArrayInputStream(c.getImageBytes())));
        }

        if (list.size() > 1) {
            CharacterProfileData c = list.get(1);
            characternametwo.setText(c.getName());
            if (c.getImageBytes() != null)
                characterimagetwo.setImage(new Image(new ByteArrayInputStream(c.getImageBytes())));
        }

        if (list.size() > 2) {
            CharacterProfileData c = list.get(2);
            characternamethree.setText(c.getName());
            if (c.getImageBytes() != null)
                characterimagethree.setImage(new Image(new ByteArrayInputStream(c.getImageBytes())));
        }

        if (list.size() > 3) {
            CharacterProfileData c = list.get(3);
            characternamefour.setText(c.getName());
            if (c.getImageBytes() != null)
                characterimagefour.setImage(new Image(new ByteArrayInputStream(c.getImageBytes())));
        }
    }
}
