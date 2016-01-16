package com.telerik.airelementalteam.thephotochallengeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Challenge {
    private String id;
    private String title;
    private String theme;
    private String dueDate;
    private boolean isPublic;
    private String creatorID;

    public Challenge() {
    }

    public Challenge(String title, String theme, String dueDate) {
        this.title = title;
        this.theme = theme;
        this.dueDate = dueDate;
        this.isPublic = true;
    }

    public Challenge(String title, String theme, boolean isPublic) {
        this.title = title;
        this.theme = theme;
        this.isPublic = isPublic;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String themeID) {
        this.theme = themeID;
    }

    public String getCreatorID() {
        return this.creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}