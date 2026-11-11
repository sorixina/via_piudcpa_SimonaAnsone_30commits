package ocstoragesystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Methods {

    public static void openHomepage() {
        try {
            Parent root = FXMLLoader.load(
                Methods.class.getResource("/ocstoragesystem/homepage.fxml")
            );

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Base -- a character database");

            Image icon = new Image(
                Methods.class.getResourceAsStream("/ocstoragesystem/images/favicon.png")
            );
            stage.getIcons().add(icon);

            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static CharacterDataFeatured getRandomCharacter() {

        String sql = "SELECT name, description, image FROM characters ORDER BY RAND() LIMIT 1";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {

                String name = rs.getString("name");
                String description = rs.getString("description");

                byte[] imgBytes = rs.getBytes("image");
                Image img = null;

                if (imgBytes != null) {
                    img = new Image(new ByteArrayInputStream(imgBytes));
                }

                return new CharacterDataFeatured(name, description, img);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public static List<CharacterProfileData> getCharactersByTag(String tag) {
        List<CharacterProfileData> list = new ArrayList<>();

        String sql = "SELECT * FROM characters WHERE tag = ? OR gender = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tag);
            stmt.setString(2, tag);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new CharacterProfileData(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("description"),
                        rs.getString("tag"),
                        rs.getBytes("image")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

}
