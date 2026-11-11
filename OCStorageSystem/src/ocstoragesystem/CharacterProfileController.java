package ocstoragesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharacterProfileController {

    @FXML
    private ImageView characterimage;

    @FXML
    private ListView<String> characterinfo;

    @FXML
    private Label characternamebtn;

    @FXML
    private Button editbtn;

    @FXML
    private TextField editfield;

    @FXML
    private Text gobackbtn;

    // store new image bytes when user selects a new image
    private byte[] newImageBytes = null;

    @FXML
    private void initialize() {

        // go back
        gobackbtn.setOnMouseClicked(e -> {
            Methods.openHomepage();
            closeThisWindow();
        });

        // when selecting a field, clear edit box
        characterinfo.setOnMouseClicked(event -> {
            if (characterinfo.getSelectionModel().getSelectedItem() != null) {
                editfield.setText("");
            }
        });

        // click on the image to choose a new one
        characterimage.setOnMouseClicked(e -> chooseNewImage());

        // edit button
        editbtn.setOnAction(e -> editSelectedField());
    }

    private void chooseNewImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Character Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File file = chooser.showOpenDialog(characterimage.getScene().getWindow());
        if (file != null) {
            try {
                newImageBytes = Files.readAllBytes(file.toPath());
                characterimage.setImage(new Image(file.toURI().toString()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void closeThisWindow() {
        Stage stage = (Stage) gobackbtn.getScene().getWindow();
        stage.close();
    }

    // load data
    public void loadCharacter(String name) {

        String sql = "SELECT * FROM characters WHERE name = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                CharacterProfileData data = new CharacterProfileData(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("description"),
                        rs.getString("tag"),
                        rs.getBytes("image")
                );

                characternamebtn.setText(data.getName());

                if (data.getImageBytes() != null) {
                    characterimage.setImage(
                            new Image(new ByteArrayInputStream(data.getImageBytes()))
                    );
                }

                characterinfo.setItems(buildInfoList(data));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ObservableList<String> buildInfoList(CharacterProfileData data) {
        return FXCollections.observableArrayList(
                "Name: " + data.getName(),
                "Age: " + data.getAge(),
                "Gender: " + data.getGender(),
                "Tag: " + data.getTag(),
                "Description: " + data.getDescription()
        );
    }

    // update db
    private void editSelectedField() {

        // if user selected a new image, save it
        if (newImageBytes != null) {
            updateImageInDatabase(newImageBytes, characternamebtn.getText());
            newImageBytes = null; // reset
            loadCharacter(characternamebtn.getText());
            return;
        }

        String selected = characterinfo.getSelectionModel().getSelectedItem();
        String newValue = editfield.getText();

        if (selected == null || newValue.isEmpty()) {
            return;
        }

        String field = mapSelectedField(selected);
        if (field == null) return;

        updateFieldInDatabase(field, newValue, characternamebtn.getText());
        loadCharacter(characternamebtn.getText());
    }

    private String mapSelectedField(String selected) {
        if (selected.startsWith("Name:")) return "name";
        if (selected.startsWith("Age:")) return "age";
        if (selected.startsWith("Gender:")) return "gender";
        if (selected.startsWith("Tag:")) return "tag";
        if (selected.startsWith("Description:")) return "description";
        return null;
    }

    private void updateFieldInDatabase(String field, String value, String name) {

        String sql = "UPDATE characters SET " + field + " = ? WHERE name = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (field.equals("age")) {
                try {
                    stmt.setInt(1, Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    System.out.println("not an age!");
                    return;
                }
            } else {
                stmt.setString(1, value);
            }

            stmt.setString(2, name);
            stmt.executeUpdate();

            if (field.equals("name")) {
                characternamebtn.setText(value);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // update image in the database
    private void updateImageInDatabase(byte[] imageBytes, String name) {

        String sql = "UPDATE characters SET image = ? WHERE name = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBytes(1, imageBytes);
            stmt.setString(2, name);
            stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
