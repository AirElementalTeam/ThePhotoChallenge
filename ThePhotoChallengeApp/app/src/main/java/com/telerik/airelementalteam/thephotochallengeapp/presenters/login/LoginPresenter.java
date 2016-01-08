package com.telerik.airelementalteam.thephotochallengeapp.presenters.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import Common.Validator;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.IPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;

public class LoginPresenter implements IPresenter {
    private Activity activity;
    private Validator validator;
    private FirebaseConnection firebase;

    public LoginPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseConnection(this.activity);
        validator = new Validator(this.activity);
    }

    public boolean succesfullLogin(String email, String password) {
        validator.validateLogin(email, password);
        firebase.openConnection(activity);
        if(firebase.loginUser(email, password)){
            return true;
        }
        else {
            validator.invalidCredentials();
            return false;
        }
    }

    @Override
    public void bind(Object object, Activity activity) {

    }

    @Override
    public void go(Activity activity) {

    }
}
