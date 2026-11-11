package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignupPageController {

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button signupbtn;

    @FXML
    private TextField usernamefield;

    @FXML
    private void initialize() {
        signupbtn.setOnAction(e -> handleSignup());
    }

    private void handleSignup() {
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("fill in all of the fields!");
            return;
        }

        try (Connection conn = DatabaseConnector.getConnection()) {

            String checkSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                showError("username already exists. you weren't quick enough!");
                return;
            }

            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);

            insertStmt.executeUpdate();

            showSuccess("signed up successfully!!! you're free to log in now!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("couldn't sign up D:");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("signed up successfully!!!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
