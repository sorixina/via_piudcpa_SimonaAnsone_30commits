package ocstoragesystem;

public class CharacterProfileData {

    private String name;
    private int age;
    private String gender;
    private String description;
    private String tag;
    private byte[] imageBytes;

    public CharacterProfileData(String name, int age, String gender,
                                String description, String tag, byte[] imageBytes) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.tag = tag;
        this.imageBytes = imageBytes;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getDescription() { return description; }
    public String getTag() { return tag; }
    public byte[] getImageBytes() { return imageBytes; }
}
