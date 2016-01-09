package com.telerik.airelementalteam.thephotochallengeapp.presenters.splashscreen;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.IPresenter;

public class SplashScreenPresenter implements IPresenter {
    private Activity activity;
    private FirebaseConnection firebase;


    public SplashScreenPresenter(Activity activity){
        this.activity = activity;
        firebase = new FirebaseConnection();
    }

    public boolean isAuthUser(){
        firebase.openConnection();
        if(!firebase.authUser()){
            return false;
        }
        return true;
    }

    @Override
    public void bind(Object object, Activity activity) {

    }

    @Override
    public void go(Activity activityTo) {

    }
}
