package com.telerik.airelementalteam.thephotochallengeapp.data;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncTaskInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestConfirmedListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.CreateChallengePresenter;

import Common.Constants;

public class FirebaseAdapter {

    final String firebaseConnection = Constants.FIREBASE_PATH;
    final String usersConnection = firebaseConnection + Constants.SLASH + Constants.USERS;
    final String usersByEmailConnection = firebaseConnection + Constants.SLASH + Constants.USERS_BY_EMAIL;
    final String challengesConnection = firebaseConnection + Constants.SLASH + Constants.CHALLENGES;
    final String photosConnection = firebaseConnection + Constants.SLASH + Constants.PHOTOS;
    final String themesConnection = firebaseConnection + Constants.SLASH + Constants.THEMES;
    final String userFriends = Constants.FRIENDS;

    private Firebase refDB;
    private Firebase refUsers;
    private Firebase refUsersByEmail;
    private Firebase refChallenges;
    private Firebase refPhotos;
    private Firebase refThemes;

    private AsyncTaskInteractor interactor;

    public FirebaseAdapter() {
        this.refDB = new Firebase(firebaseConnection);
        this.refUsers = new Firebase(usersConnection);
        this.refUsersByEmail = new Firebase(usersByEmailConnection);
        this.refChallenges = new Firebase(challengesConnection);
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
        return refChallenges;
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

    public void listenForChanges(IOnFriendRequestListener requestListener, IOnFriendRequestConfirmedListener friendListener) {
        interactor.listenForFriendRequest(this, requestListener);
        interactor.listenForFriendRequestsConfirm(this, friendListener);
    }

    public void confirmFriendsWith(String uid, IOnTaskFinishedListener listener) {
        String authUID = this.currentUserUID();
        interactor.asyncMakeFriends(this, listener, authUID, uid);
    }

    public Firebase refFriends() {
        return new Firebase(firebaseConnection + Constants.SLASH + Constants.FRIENDS + Constants.SLASH + currentUserUID() + "-" + Constants.FRIENDS);
    }

    public Firebase refUserChallenges() {
        return  new Firebase(firebaseConnection + Constants.SLASH + Constants.CHALLENGES_BY_USER + Constants.SLASH + currentUserUID() + "-" + Constants.CHALLENGES);
    }

    public void createNewChallenge(Challenge newChallenge, IOnTaskFinishedListener listener) {
        interactor.saveNewChallenge(this, listener, newChallenge);
    }
}
