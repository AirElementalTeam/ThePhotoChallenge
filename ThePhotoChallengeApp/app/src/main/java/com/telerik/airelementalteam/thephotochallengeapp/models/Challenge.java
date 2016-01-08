package com.telerik.airelementalteam.thephotochallengeapp.models;

import java.util.List;

public class Challenge {
    private String id;
    private String title;
    private String themeID;
    private String creatorID;
    private List<String> participantIDs;
    private List<String> photosIDs;

    public Challenge(){}

    public Challenge(String title, String themeID)
    {
        this.title = title;
        this.themeID = themeID;
    }

    public String getId(){return this.id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){return this.title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThemeID(){return this.themeID;}

    public void setThemeID(String themeID) {
        this.themeID = themeID;
    }

    public String getCreatorID(){return this.creatorID;}

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public List<String> getParticipantIDs() {
        return participantIDs;
    }

    public void setParticipantIDs(List<String> participantIDs) {
        this.participantIDs = participantIDs;
    }

    public List<String> getPhotosIDs() {
        return photosIDs;
    }

    public void setPhotosIDs(List<String> photosIDs) {
        this.photosIDs = photosIDs;
    }
}
