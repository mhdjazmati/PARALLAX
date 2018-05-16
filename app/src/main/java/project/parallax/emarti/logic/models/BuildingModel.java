package project.parallax.emarti.logic.models;

/**
 * Created by MohammadSommakia on 4/19/2018.
 */

public class BuildingModel {
    private int BuildingPhotoUrl;
    private String BuildingName;
    private boolean isChecked = false;
    private String key;
    private String NumberOfFloors;

    public BuildingModel(int buildingPhotoUrl, String numberOfFloors, String buildingName, boolean isChecked, String key) {
        this.BuildingPhotoUrl = buildingPhotoUrl;
        this.NumberOfFloors = numberOfFloors;
        this.BuildingName = buildingName;
        this.isChecked = isChecked;
        this.key = key;
    }


    public BuildingModel(String numberOfFloors, String buildingName) {
        this.BuildingName = buildingName;
        this.NumberOfFloors = numberOfFloors;

    }

    public BuildingModel() {

    }

    public int getBuildingPhotoUrl() {
        return BuildingPhotoUrl;
    }

    public void setBuildingPhotoUrl(int buildingPhotoUrl) {
        BuildingPhotoUrl = buildingPhotoUrl;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getNumberOfFloors() {
        return NumberOfFloors;
    }

    public void setNumberOfFloors(String numberOfFloors) {
        NumberOfFloors = numberOfFloors;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
