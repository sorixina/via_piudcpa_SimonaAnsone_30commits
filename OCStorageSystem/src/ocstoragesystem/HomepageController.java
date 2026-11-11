package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomepageController {

    @FXML
    private Text allcharactersbtn;

    @FXML
    private Label featuredescone;

    @FXML
    private Label featuredesctwo;

    @FXML
    private ImageView featureimageone;

    @FXML
    private ImageView featureimagetwo;

    @FXML
    private Label featurenameone;

    @FXML
    private Label featurenametwo;

    @FXML
    private Button tagfemalebtn;

    @FXML
    private Button taghumanbtn;

    @FXML
    private Button tagmalebtn;

    @FXML
    private Button tagnonhumanbtn;

    @FXML
    private void initialize() {

        CharacterDataFeatured c1 = Methods.getRandomCharacter();
        if (c1 != null) {
            featurenameone.setText(c1.getName());
            featuredescone.setText(c1.getDescription());
            featureimageone.setImage(c1.getImage());
        }

        CharacterDataFeatured c2 = Methods.getRandomCharacter();
        if (c2 != null) {
            featurenametwo.setText(c2.getName());
            featuredesctwo.setText(c2.getDescription());
            featureimagetwo.setImage(c2.getImage());
        }

        allcharactersbtn.setOnMouseClicked(e -> {
            openWindow("/ocstoragesystem/characterslist.fxml");
            closeHomepage();
        });

        taghumanbtn.setOnAction(e -> {
            openTagWindow("human");
            closeHomepage();
        });

        tagnonhumanbtn.setOnAction(e -> {
            openTagWindow("non-human");
            closeHomepage();
        });

        tagmalebtn.setOnAction(e -> {
            openTagWindow("male");
            closeHomepage();
        });

        tagfemalebtn.setOnAction(e -> {
            openTagWindow("female");
            closeHomepage();
        });
    }

    private void openWindow(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Base -- a character database");

            Image icon = new Image(
                    OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png"));
            stage.getIcons().add(icon);

            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openTagWindow(String tag) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ocstoragesystem/tagslist.fxml"));
            Parent root = loader.load();

            TagsListController controller = loader.getController();
            controller.setTag(tag);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Base -- a character database");

            Image icon = new Image(
                    OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png"));
            stage.getIcons().add(icon);

            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeHomepage() {
        Stage homepageStage = (Stage) allcharactersbtn.getScene().getWindow();
        homepageStage.close();
    }
}
