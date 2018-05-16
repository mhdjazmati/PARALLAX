package project.parallax.emarti.logic.models;

/**
 * Created by MohammadSommakia on 4/12/2018.
 */

public class ApartmentModel {

    private String apartmentId;
    private String ApartmentPhotoUrl;
    private String BuildingName;
    private String FloorNumber;
    private String ApartmentNumber;
    private String AboutApartment;
    private String UserName;
    private String Password;

    public ApartmentModel(String apartmentId, String imageUrl, String floorNumber, String buildingName, String apartmentNumber,
                          String aboutApartment, String userName, String password) {
        this.apartmentId = apartmentId;
        this.ApartmentPhotoUrl = imageUrl;
        this.FloorNumber = floorNumber;
        this.BuildingName = buildingName;
        this.ApartmentNumber = apartmentNumber;
        this.AboutApartment = aboutApartment;
        this.UserName = userName;
        this.Password = password;
    }
    public ApartmentModel()
    {

    }


    public String getApartmentPhotoUrl() {
        return ApartmentPhotoUrl;
    }

    public void setApartmentPhotoUrl(String imageUrl) {
        this.ApartmentPhotoUrl = imageUrl;
    }

    public String getFloorNumber() {
        return FloorNumber;
    }

    public void setFloorNumber(String name) {
        this.FloorNumber = name;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String address) {
        this.BuildingName = address;
    }


    public String getApartmentNumber() {
        return ApartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.ApartmentNumber = apartmentNumber;
    }

    public String getAboutApartment() {
        return AboutApartment;
    }

    public void setAboutApartment(String aboutApartment) {
        this.AboutApartment = aboutApartment;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }
}

