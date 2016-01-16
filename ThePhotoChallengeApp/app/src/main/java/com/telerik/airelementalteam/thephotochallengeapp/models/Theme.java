package com.telerik.airelementalteam.thephotochallengeapp.models;

import java.util.List;

public class Theme {
    private String id;
    private String name;
    private String description;
    public List<String> challangesIDs;

    public Theme(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getChallangesIDs() {
        return challangesIDs;
    }

    public void setChallangesIDs(List<String> challangesIDs) {
        this.challangesIDs = challangesIDs;
    }
}
