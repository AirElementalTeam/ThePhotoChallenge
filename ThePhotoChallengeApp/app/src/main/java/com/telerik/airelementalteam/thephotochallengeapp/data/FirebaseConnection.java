package com.telerik.airelementalteam.thephotochallengeapp.data;

import android.app.Activity;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.AsyncTaskInteractor;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;

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

    private Activity activity;
    private boolean boolResult;
    private AsyncTaskInteractor interactor;

    public FirebaseConnection(Activity activity) {
        this.activity = activity;
        Firebase.setAndroidContext(activity);
        this.refDB = new Firebase(firebaseConnection);
        this.refUsers = new Firebase(usersConnection);
        this.refChallanges = new Firebase(challengesConnection);
        this.refPhotos = new Firebase(photosConnection);
        this.refThemes = new Firebase(themesConnection);
        this.interactor = new AsyncTaskInteractor();
    }


    public FirebaseConnection openConnection(Activity activity){
        return new FirebaseConnection(activity);
    }

    public void registerUser(String email, String password, IOnTaskFinishedListener listener){
        System.out.println("Inside registerUser method in FirebaseConnection class");
        interactor.asyncRegisterUser(refDB, listener, email, password);
        System.out.println("Before exiting registerUser method in FirebaseConnection class");
    }

    public boolean loginUser(String email, String password){

        refDB.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                boolResult = true;
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                boolResult = false;
            }
        });
        return boolResult;
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
