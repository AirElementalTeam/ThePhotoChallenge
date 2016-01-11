package com.telerik.airelementalteam.thephotochallengeapp.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String name;
    private String email;
    private List<String> challangesIDs;
    private List<String> challangesByUserIDs;
    private List<String> photosIDs;
    private List<String> favouritePhotosIDs;
    private List<String> friendsIDs;

    public User(){}

    public User(String uid, String name, String email){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.challangesIDs = new ArrayList<>();
        this.challangesByUserIDs = new ArrayList<>();
        this.photosIDs = new ArrayList<>();
        this.favouritePhotosIDs = new ArrayList<>();
        this.friendsIDs = new ArrayList<>();
    }

    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getChallangesIDs(){
        return this.challangesIDs;
    }

    public void setChallangesIDs(List<String> challangesIDs) {
        this.challangesIDs = challangesIDs;
    }

    public List<String> getChallangesByUserIDs(){
        return this.challangesByUserIDs;
    }

    public void setChallangesByUserIDs(List<String> challangesByUserIDs) {
        this.challangesByUserIDs = challangesByUserIDs;
    }

    public List<String> getPhotosIDs(){
        return this.photosIDs;
    }

    public void setPhotosIDs(List<String> photosIDs) {
        this.photosIDs = photosIDs;
    }

    public List<String> getFavouritePhotosIDs(){
        return this.favouritePhotosIDs;
    }

    public void setFavouritePhotosIDs(List<String> favouritePhotosIDs) {
        this.favouritePhotosIDs = favouritePhotosIDs;
    }

    public List<String> getFriendsIDs(){
        return this.friendsIDs;
    }

    public void setFriendsIDs(List<String> friendsIDs) {
        this.friendsIDs = friendsIDs;
    }
}
