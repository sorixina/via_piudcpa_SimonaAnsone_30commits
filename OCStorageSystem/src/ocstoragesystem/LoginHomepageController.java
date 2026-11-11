package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class LoginHomepageController {

    @FXML
    private Button loginbtn;

    @FXML
    private Button signupbtn;

    @FXML
    private void initialize() {

        loginbtn.setOnAction(e -> 
            openNewWindow("/ocstoragesystem/loginpage.fxml")
        );

        signupbtn.setOnAction(e -> 
            openNewWindow("/ocstoragesystem/signuppage.fxml")
        );
    }

    private void openNewWindow(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Base -- a character database");

            Image icon = new Image(
                OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png")
            );
            newStage.getIcons().add(icon);

            newStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
