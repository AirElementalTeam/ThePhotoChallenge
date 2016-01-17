package com.telerik.airelementalteam.thephotochallengeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Challenge {
    private String id;
    private String title;
    private String theme;
    private String dueDate;
    private String creatorID;
    private String creatorName;
    private int participantsCount;
    private int photosCount;

    public Challenge(){}

    public Challenge(String title, String theme, String dueDate)
    {
        this.title = title;
        this.theme = theme;
        this.dueDate = dueDate;
        this.participantsCount = 1;
    }

    public String getId(){return this.id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){return this.title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme(){return this.theme;}

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getCreatorID(){return this.creatorID;}

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }
}
