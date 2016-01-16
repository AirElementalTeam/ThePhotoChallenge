package com.telerik.airelementalteam.thephotochallengeapp.presenters.splashscreen;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

public class SplashScreenPresenter {
    private Activity activity;
    private FirebaseAdapter firebase;


    public SplashScreenPresenter(Activity activity){
        this.activity = activity;
        firebase = new FirebaseAdapter();
    }

    public boolean isAuthUser(){
        System.out.println("SPLASHSCREEN CRASH");
        System.out.println("firebase.getRefDB()------" + firebase.getRefDB());
       // if(!firebase.authUser()){
       //     return false;
       // }
        return true;
    }

}
