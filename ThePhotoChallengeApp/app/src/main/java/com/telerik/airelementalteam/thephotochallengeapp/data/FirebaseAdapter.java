package com.telerik.airelementalteam.thephotochallengeapp.data;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncTaskInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;

public class FirebaseAdapter {

    final String firebaseConnection = "https://thephotobag.firebaseio.com";
    final String usersConnection = firebaseConnection + "/Users";
    final String usersByEmailConnection = firebaseConnection + "/UsersByEmail";
    final String challengesConnection = firebaseConnection + "/Challanges";
    final String photosConnection = firebaseConnection + "/Photos";
    final String themesConnection = firebaseConnection + "/Themes";
    final String userFriends = "Friends";

    private Firebase refDB;
    private Firebase refUsers;
    private Firebase refUsersByEmail;
    private Firebase refChallanges;
    private Firebase refPhotos;
    private Firebase refThemes;

    private AsyncTaskInteractor interactor;

    public FirebaseAdapter() {
        this.refDB = new Firebase(firebaseConnection);
        this.refUsers = new Firebase(usersConnection);
        this.refUsersByEmail = new Firebase(usersByEmailConnection);
        this.refChallanges = new Firebase(challengesConnection);
        this.refPhotos = new Firebase(photosConnection);
        this.refThemes = new Firebase(themesConnection);
        this.interactor = new AsyncTaskInteractor();
    }

    public FirebaseAdapter openConnection(){
        return new FirebaseAdapter();
    }

    public Firebase getRefDB() {
        return this.refDB;
    }

    public Firebase getRefUsers() {
        return refUsers;
    }

    public Firebase getRefUsersByEmail() {
        return refUsersByEmail;
    }

    public Firebase getRefChallanges() {
        return refChallanges;
    }

    public Firebase getRefPhotos() {
        return refPhotos;
    }

    public Firebase getRefThemes() {
        return refThemes;
    }

    // authentication methods
    public void registerUser(String name, String email, String password, IOnTaskFinishedListener listener){
        interactor.asyncRegisterUser(this, listener, name, email, password);
    }

    public void loginUser(String email, String password, IOnTaskFinishedListener listener){
        interactor.asyncLoginUser(this, listener, email, password);
    }

    public boolean authUser() {
        AuthData auth = refDB.getAuth();
        if (auth != null) {
            //user is logged in
            return true;
        } else {
            //user is not log in
            return false;
        }
    }

    public void currentUserNameAndMail(IOnTaskFinishedListener listener){
        interactor.asyncGetUserNameAndMail(this, listener);
    }

    public String currentUserUID() {
        return refDB.getAuth().getUid();
    }

    public Firebase currentUserFriends() {
        String UID = this.currentUserUID();
        return this.refUsers.child(UID).child(userFriends);
    }

    public void logoutUser(){
        refDB.unauth();
    }

    public void sendAndReceiveFriendRequest(IOnTaskFinishedListener listener, Query fromUser, Query toUser) {
        interactor.asyncSendAndReceiveFriendRequest(this, listener, fromUser, toUser);
    }

    public void listenForChanges(IOnTaskFinishedListener listener) {
        interactor.listenForFriendRequest(this, listener);
    }
}
