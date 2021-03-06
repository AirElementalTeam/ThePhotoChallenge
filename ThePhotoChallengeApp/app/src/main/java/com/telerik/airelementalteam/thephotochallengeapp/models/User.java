package com.telerik.airelementalteam.thephotochallengeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String uid;
    private String name;
    private String email;
    private HashMap<String, Object> challengesIDs;
    private HashMap<String, Object> challengesByUserIDs;
    private HashMap<String, Object> photosIDs;
    private HashMap<String, Object> favouritePhotosIDs;
    private HashMap<String, Object> friends;
    private HashMap<String, User>friendRequestSend;
    private HashMap<String, User> friendRequestReceived;

    public User(){}

    public User(String uid, String name, String email){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.challengesIDs = new HashMap<>();
        this.challengesByUserIDs = new HashMap<>();
        this.photosIDs = new HashMap<>();
        this.favouritePhotosIDs = new HashMap<>();
        this.friends = new HashMap<>();
        this.friendRequestSend = new HashMap<>();
        this.friendRequestReceived = new HashMap<>();
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

    public HashMap<String, Object> getChallangesIDs(){
        return this.challengesIDs;
    }

    public void setChallangesIDs(HashMap<String, Object> challangesIDs) {
        this.challengesIDs = challangesIDs;
    }

    public HashMap<String, Object> getChallangesByUserIDs(){
        return this.challengesByUserIDs;
    }

    public void setChallangesByUserIDs(HashMap<String, Object> challangesByUserIDs) {
        this.challengesByUserIDs = challangesByUserIDs;
    }

    public HashMap<String, Object> getPhotosIDs(){
        return this.photosIDs;
    }

    public void setPhotosIDs(HashMap<String, Object> photosIDs) {
        this.photosIDs = photosIDs;
    }

    public HashMap<String, Object> getFavouritePhotosIDs(){
        return this.favouritePhotosIDs;
    }

    public void setFavouritePhotosIDs(HashMap<String, Object> favouritePhotosIDs) {
        this.favouritePhotosIDs = favouritePhotosIDs;
    }

    public HashMap<String, Object> getFriends(){
        return this.friends;
    }

    public void setFriends(HashMap<String, Object> friends) {
        this.friends = friends;
    }


    public HashMap<String, User> getFrinedRequestSend() {
        return friendRequestSend;
    }

    public void setFrinedRequestSend(HashMap<String, User> friendRequestSend) {
        this.friendRequestSend = friendRequestSend;
    }

    public HashMap<String, User> getFriendRequestRecieved() {
        return friendRequestReceived;
    }

    public void setFriendRequestRecieved(HashMap<String, User> friendRequestRecieved) {
        this.friendRequestReceived = friendRequestRecieved;
    }

    public String toString() {
        String user = "Name: " + this.getName() + "\n" + "Email: " + this.getEmail() + "\n" + "UID: " + this.getUid() + "\n" + "Friends" + this.getFriends();
        return user;
    }
}