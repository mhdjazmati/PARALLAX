package project.parallax.emarti.logic.models;

/**
 * Created by MohammadSommakia on 4/23/2018.
 */

public class BuildingCommitteeModel {
    private String userName;
    private String imageProfile;
    private String email;
    private String id;

    public BuildingCommitteeModel(String userName, String imageProfile, String email) {
        this.userName = userName;
        this.imageProfile = imageProfile;
        this.email = email;
    }
    public BuildingCommitteeModel(String userName, String imageProfile, String email, String id) {
        this.userName = userName;
        this.id = id;
        this.imageProfile = imageProfile;
        this.email = email;
    }
    public BuildingCommitteeModel()
    {

    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
