package com.telerik.airelementalteam.thephotochallengeapp.presenters.splashscreen;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.IPresenter;

public class SplashScreenPresenter implements IPresenter {
    private Activity activity;
    private FirebaseAdapter firebase;


    public SplashScreenPresenter(Activity activity){
        this.activity = activity;
        firebase = new FirebaseAdapter();
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
