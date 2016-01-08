package com.telerik.airelementalteam.thephotochallengeapp.presenters.home;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.IPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;

import Common.Validator;

public class HomePresenter implements IPresenter{

    private Activity activity;
    private Validator validator;
    private FirebaseConnection firebase;

    public HomePresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseConnection(this.activity);
        validator = new Validator(this.activity);
    }

    public void logoutUser(){firebase.logoutUser();}

    @Override
    public void bind(Object object, Activity activity) {

    }

    @Override
    public void go(Activity activity) {

    }
}
