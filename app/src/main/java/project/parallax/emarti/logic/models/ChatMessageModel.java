package project.parallax.emarti.logic.models;

import java.util.ArrayList;

/**
 * Created by MohammadSommakia on 4/22/2018.
 */

public class ChatMessageModel {
    private String Message;
    private String SendingTime;
    private String senderId;
    private ArrayList<String> BuildingNameList;
    private String receiverId;
    private String BuildingsList;


    public ChatMessageModel(String message, String senderId, ArrayList<String> receiverId, String sendingTime) {
        this.Message = message;
        this.senderId = senderId;
        this.BuildingNameList = receiverId;
        this.SendingTime = sendingTime;
    }

    public ChatMessageModel(String message, String senderId, String sendingTime) {
        this.Message = message;
        this.SendingTime = sendingTime;
        this.senderId = senderId;
    }

    public ChatMessageModel() {

    }

    public ChatMessageModel(String message, String currentUserId, String otherUserId, String date) {
        this.Message = message;
        this.senderId = currentUserId;
        this.SendingTime = date;
        this.receiverId = otherUserId;
    }

    public ChatMessageModel(String message, String buildingsReceived, String date,String currentUserId, int i) {
        this.BuildingsList = buildingsReceived;
        this.Message = message;
        this.SendingTime = date;
        this.senderId = currentUserId;

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }


    public String getSendingTime() {
        return SendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.SendingTime = sendingTime;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public ArrayList<String> getBuildingNameList() {
        return BuildingNameList;
    }

    public void setBuildingNameList(ArrayList<String> buildingNameList) {
        BuildingNameList = buildingNameList;
    }

    public String getBuildingsList() {
        return BuildingsList;
    }

    public void setBuildingsList(String buildingsList) {
        BuildingsList = buildingsList;
    }
}