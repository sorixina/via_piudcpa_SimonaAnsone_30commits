package ocstoragesystem;

import javafx.scene.image.Image;

public class CharacterDataFeatured {

    private String name;
    private String description;
    private Image image;

    public CharacterDataFeatured(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }
}
