package com.telerik.airelementalteam.thephotochallengeapp.data;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncAuthInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncChallengeInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncFriendshipInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestConfirmedListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.ApprovePhotoPresenter;

import Common.Constants;
import Common.Path;

public class FirebaseAdapter {

    final String userFriends = Constants.FRIENDS;

    private Firebase refDB;
    private Firebase refUsers;
    private Firebase refUsersByEmail;
    private Firebase refChallenges;
    private Firebase refPhotos;
    private Firebase refAllPhotos;

    private AsyncAuthInteractor authInteractor;
    private AsyncFriendshipInteractor friendshipInteractor;
    private AsyncChallengeInteractor challengeInteractor;

    public FirebaseAdapter() {
        this.refDB = new Firebase(Path.TO_REF_DB);
        this.refUsers = new Firebase(Path.TO_USERS);
        this.refUsersByEmail = new Firebase(Path.TO_USERS_BY_EMAIL);
        this.refChallenges = new Firebase(Path.TO_CHALLENGES);
        this.refPhotos = new Firebase(Path.TO_PHOTOS);
        this.refAllPhotos = new Firebase(Path.TO_ALL_PHOTOS);
        this.authInteractor = new AsyncAuthInteractor();
        this.friendshipInteractor = new AsyncFriendshipInteractor();
        this.challengeInteractor = new AsyncChallengeInteractor();
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

    public Firebase refUserFriends() {
        String path = String.format(Path.TO_USER_FRIENDS, currentUserUID());
        return new Firebase(path);
    }

    public Firebase refUserChallenges() {
        String path = String.format(Path.TO_CHALLENGES_BY_USER, currentUserUID());
        return  new Firebase(path);
    }

    public Firebase refUserPhotos(){
        return new Firebase(String.format(Path.TO_USER_PHOTOS, currentUserUID()));
    }

    // authentication methods
    public void registerUser(String name, String email, String password, IOnTaskFinishedListener listener){
        authInteractor.asyncRegisterUser(this, listener, name, email, password);
    }

    public void loginUser(String email, String password, IOnTaskFinishedListener listener){
        authInteractor.asyncLoginUser(this, listener, email, password);
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
        authInteractor.asyncGetUserNameAndMail(this, listener);
    }

    public String currentUserUID() {
        return refDB.getAuth().getUid();
    }

    public void logoutUser(){
        refDB.unauth();
    }

    public void sendAndReceiveFriendRequest(IOnTaskFinishedListener listener, Query fromUser, Query toUser) {
        friendshipInteractor.asyncSendAndReceiveFriendRequest(this, listener, fromUser, toUser);
    }

    public void listenForChanges(IOnFriendRequestListener requestListener, IOnFriendRequestConfirmedListener friendListener) {
        friendshipInteractor.listenForFriendRequest(this, requestListener);
        friendshipInteractor.listenForFriendRequestsConfirm(this, friendListener);
    }

    public void confirmFriendsWith(String uid, IOnTaskFinishedListener listener) {
        String authUID = this.currentUserUID();
        friendshipInteractor.asyncMakeFriends(this, listener, authUID, uid);
    }



    public void createNewChallenge(Challenge newChallenge, IOnTaskFinishedListener listener) {
        challengeInteractor.saveNewChallenge(this, listener, newChallenge);
    }

    public void getChallengeInfo(String challengeID, IOnTaskFinishedListener listener) {
        challengeInteractor.getChallengeInfo(this, listener, challengeID);
    }

    public void savePhoto(Photo photo, IOnTaskFinishedListener listener) {
        challengeInteractor.savePhoto(this, listener, photo);
    }

    public Firebase getRefAllPhotos() {
        return refAllPhotos;
    }

    public void setRefAllPhotos(Firebase refAllPhotos) {
        this.refAllPhotos = refAllPhotos;
    }
}
