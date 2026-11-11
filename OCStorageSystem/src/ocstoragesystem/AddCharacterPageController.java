package ocstoragesystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCharacterPageController {

    @FXML
    private TextField agefield;

    @FXML
    private ImageView characterimage;

    @FXML
    private Button createbtn;

    @FXML
    private TextField descriptionfield;

    @FXML
    private ComboBox<String> gendercombobox;

    @FXML
    private ComboBox<String> tagcombobox;

    @FXML
    private Text gobackbtn;

    @FXML
    private TextField namefield;

    @FXML
    private Label uploadimagebutton;

    private File selectedImageFile;

    @FXML
    public void initialize() {

        gendercombobox.getItems().addAll("male", "female");
        tagcombobox.getItems().addAll("human", "non-human");

        gobackbtn.setOnMouseClicked(event -> {
            Methods.openHomepage();
            closeWindow();
        });

        uploadimagebutton.setOnMouseClicked(event -> chooseImage());

        createbtn.setOnAction(event -> createCharacter());
    }

    private void closeWindow() {
        Stage stage = (Stage) createbtn.getScene().getWindow();
        stage.close();
    }

    private void chooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Character Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedImageFile = chooser.showOpenDialog(uploadimagebutton.getScene().getWindow());

        if (selectedImageFile != null) {
            characterimage.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    private void createCharacter() {

        String name = namefield.getText();
        String ageText = agefield.getText();
        String gender = gendercombobox.getValue();
        String description = descriptionfield.getText();
        String tag = tagcombobox.getValue();

        if (!validateFields(name, ageText, gender, description, tag)) {
            System.out.println("fill in ALL fields please!");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            System.out.println("gotta be a number!");
            return;
        }

        String sql = "INSERT INTO characters (name, age, gender, description, image, tag) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, description);

            if (selectedImageFile != null) {
                byte[] bytes = Files.readAllBytes(selectedImageFile.toPath());
                stmt.setBytes(5, bytes);
            } else {
                stmt.setNull(5, java.sql.Types.BLOB);
            }

            stmt.setString(6, tag);

            stmt.executeUpdate();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ocstoragesystem/characterslist.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Base -- a character database");

            Image icon = new Image(
                    OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png"));
            stage.getIcons().add(icon);

            stage.show();

            closeWindow();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateFields(String name, String age, String gender, String description, String tag) {
        return !(name.isEmpty() || age.isEmpty() || gender == null || description.isEmpty() || tag == null);
    }
}
