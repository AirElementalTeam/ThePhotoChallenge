package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

public class MainPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;

    //you were doiung presenter for logout!
    public MainPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void logoutUser(){firebase.logoutUser();}
}
