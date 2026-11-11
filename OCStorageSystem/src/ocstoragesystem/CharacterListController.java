package ocstoragesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharacterListController {

    @FXML
    private Button addcharacterbtn;

    @FXML
    private Button deletecharacterbtn;

    @FXML
    private Button openprofilebtn;

    @FXML
    private Text gobackbtn;

    @FXML
    private TableView<CharacterData> allcharacterstable;

    @FXML
    private TableColumn<CharacterData, String> tablenamecolumn;

    private CharacterData selectedCharacter;

    public void initialize() {

        // table setup
        tablenamecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadCharacters();

        // click row → store selected
        allcharacterstable.setOnMouseClicked(event -> {
            selectedCharacter = allcharacterstable.getSelectionModel().getSelectedItem();
        });

        // delete selected
        deletecharacterbtn.setOnAction(e -> {
            if (selectedCharacter != null) {
                deleteCharacter(selectedCharacter.getName());
                loadCharacters();
            }
        });

        // open profile
        openprofilebtn.setOnAction(e -> {
            if (selectedCharacter != null) {
                openCharacterProfile(selectedCharacter.getName());
            }
        });

        // go back
        gobackbtn.setOnMouseClicked(event -> {
            Methods.openHomepage();
            Stage stage = (Stage) gobackbtn.getScene().getWindow();
            stage.close();
        });

        // open add character page
        addcharacterbtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addcharacterpage.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Base -- a character database");

                Image icon = new Image(
                        OCMain.class.getResourceAsStream("/ocstoragesystem/images/favicon.png"));
                stage.getIcons().add(icon);

                stage.show();

                Stage current = (Stage) addcharacterbtn.getScene().getWindow();
                current.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadCharacters() {
        ObservableList<CharacterData> list = FXCollections.observableArrayList();

        String sql = "SELECT name FROM characters";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new CharacterData(rs.getString("name")));
            }

            allcharacterstable.setItems(list);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCharacter(String name) {
        String sql = "DELETE FROM characters WHERE name = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openCharacterProfile(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("characterprofile.fxml"));
            Parent root = loader.load();

            CharacterProfileController controller = loader.getController();
            controller.loadCharacter(name);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Character Profile");
            stage.show();

            Stage current = (Stage) openprofilebtn.getScene().getWindow();
            current.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
