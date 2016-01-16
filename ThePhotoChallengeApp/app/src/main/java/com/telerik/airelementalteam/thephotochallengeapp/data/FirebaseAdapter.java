package com.telerik.airelementalteam.thephotochallengeapp.data;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncAuthInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncChallengesInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncFriendRequestsInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestConfirmedListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;

import Common.Constants;

public class FirebaseAdapter {

    final String firebaseConnection = Constants.FIREBASE_PATH;
    final String usersConnection = firebaseConnection + Constants.SLASH + Constants.USERS;
    final String usersByEmailConnection = firebaseConnection + Constants.SLASH + Constants.USERS_BY_EMAIL;
    final String challengesConnection = firebaseConnection + Constants.SLASH + Constants.CHALLENGES;
    final String userChallengesConnection = firebaseConnection + Constants.SLASH + currentUserUID() + "-" + Constants.CHALLENGES;
    final String photosConnection = firebaseConnection + Constants.SLASH + Constants.PHOTOS;
    final String themesConnection = firebaseConnection + Constants.SLASH + Constants.THEMES;
    final String userFriends = Constants.FRIENDS;

    private Firebase refDB;
    private Firebase refUsers;
    private Firebase refUsersByEmail;
    private Firebase refChallenges;
    private Firebase refUserChallenges;
    private Firebase refPhotos;
    private Firebase refThemes;

    private AsyncAuthInteractor authInteractor;
    private AsyncFriendRequestsInteractor requestsInteractor;
    private AsyncChallengesInteractor challengesInteractor;

    public FirebaseAdapter() {
        this.refDB = new Firebase(firebaseConnection);
        this.refUsers = new Firebase(usersConnection);
        this.refUsersByEmail = new Firebase(usersByEmailConnection);
        this.refChallenges = new Firebase(challengesConnection);
        this.refUserChallenges = new Firebase(userChallengesConnection);
        this.refPhotos = new Firebase(photosConnection);
        this.refThemes = new Firebase(themesConnection);
        this.authInteractor = new AsyncAuthInteractor();
        this.requestsInteractor = new AsyncFriendRequestsInteractor();
        this.challengesInteractor = new AsyncChallengesInteractor();
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
        authInteractor.asyncRegisterUser(this, listener, name, email, password);
    }

    public void loginUser(String email, String password, IOnTaskFinishedListener listener){
        authInteractor.asyncLoginUser(this, listener, email, password);
    }

    public boolean authUser() {
        openConnection();
        System.out.println("authUser");
        System.out.println(refDB);

        AuthData auth = refDB.getAuth();
        System.out.println("auth ----- " + auth);
        if (auth != null) {
            //user is logged in
            return true;
        } else {
            //user is not log in
            return false;
        }
    }

    public void logoutUser(){
        refDB.unauth();
    }

    //current user needed things
    public void currentUserNameAndMail(IOnTaskFinishedListener listener){
        requestsInteractor.asyncGetUserNameAndMail(this, listener);
    }

    public String currentUserUID() {
        return refDB.getAuth().getUid();
    }

    public Firebase currentUserFriends() {
        return new Firebase(firebaseConnection + Constants.SLASH + Constants.FRIENDS + Constants.SLASH + currentUserUID() + "-" + Constants.FRIENDS);
    }

    //friend request handling methods
    public void sendAndReceiveFriendRequest(IOnTaskFinishedListener listener, Query fromUser, Query toUser) {
        requestsInteractor.asyncSendAndReceiveFriendRequest(this, listener, fromUser, toUser);
    }

    //listeners
    public void listenForChanges(IOnFriendRequestListener requestListener, IOnFriendRequestConfirmedListener friendListener) {
        requestsInteractor.listenForFriendRequest(this, requestListener);
        requestsInteractor.listenForFriendRequestsConfirm(this, friendListener);
    }

    public void confirmFriendsWith(String uid, IOnTaskFinishedListener listener) {
        String authUID = this.currentUserUID();
        requestsInteractor.asyncMakeFriends(this, listener, authUID, uid);
    }

    //challenges methods
    public void createNewChallenge(Challenge newChallenge, IOnTaskFinishedListener listener) {
        challengesInteractor.saveChallenge(this, listener, newChallenge);
    }

    public Firebase getRefUserChallenges() {
        return refUserChallenges;
    }

    public void setRefUserChallenges(Firebase refUserChallenges) {
        this.refUserChallenges = refUserChallenges;
    }
}
