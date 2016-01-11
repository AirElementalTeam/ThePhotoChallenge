package com.telerik.airelementalteam.thephotochallengeapp.data;

import android.app.Activity;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncTaskInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class FirebaseConnection {
    final String firebaseConnection = "https://thephotobag.firebaseio.com";
    final String usersConnection = firebaseConnection + "/Users";
    final String challengesConnection = firebaseConnection + "/Challanges";
    final String photosConnection = firebaseConnection + "/Photos";
    final String themesConnection = firebaseConnection + "/Themes";

    private Firebase refDB;
    private Firebase refUsers;
    private Firebase refChallanges;
    private Firebase refPhotos;
    private Firebase refThemes;

    //private Activity activity;
    private AsyncTaskInteractor interactor;

    public FirebaseConnection() {
        this.refDB = new Firebase(firebaseConnection);
        this.refUsers = new Firebase(usersConnection);
        this.refChallanges = new Firebase(challengesConnection);
        this.refPhotos = new Firebase(photosConnection);
        this.refThemes = new Firebase(themesConnection);
        this.interactor = new AsyncTaskInteractor();
    }

    public FirebaseConnection openConnection(){
        return new FirebaseConnection();
    }

    // authentication methods
    public void registerUser(String name, String email, String password, IOnTaskFinishedListener listener){
        interactor.asyncRegisterUser(refDB, refUsers, listener, name, email, password);
    }

    public void loginUser(String email, String password, IOnTaskFinishedListener listener){
        interactor.asyncLoginUser(refDB, listener, email, password);
    }

    public boolean authUser(){
        AuthData auth = refDB.getAuth();
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
}
