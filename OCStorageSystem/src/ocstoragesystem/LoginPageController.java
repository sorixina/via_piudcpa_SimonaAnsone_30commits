package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPageController {

    @FXML
    private Button loginbtn;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private TextField usernamefield;

    @FXML
    private void initialize() {
        loginbtn.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        try (Connection conn = DatabaseConnector.getConnection()) {

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                openHomepage();
            } else {
                showErrorPopup("Incorrect username or password.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openHomepage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ocstoragesystem/homepage.fxml"));

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Base -- a character database");

            Image icon = new Image(
                OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png")
            );
            newStage.getIcons().add(icon);

            newStage.show();

            Stage currentStage = (Stage) loginbtn.getScene().getWindow();
            currentStage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
