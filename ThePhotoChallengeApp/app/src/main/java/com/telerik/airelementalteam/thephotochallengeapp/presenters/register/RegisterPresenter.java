package com.telerik.airelementalteam.thephotochallengeapp.presenters.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;

import Common.Validator;

public class RegisterPresenter {
    private Activity activity;
    private Validator validator;
    private FirebaseConnection firebase;

    public RegisterPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseConnection(this.activity);
        validator = new Validator(this.activity);
    }

    public void attemptRegistration(String email, String name, String password, String confirmPassword){
        boolean validResult = validator.validateRegister(email, name, password, confirmPassword);
        if(!validator.validateRegister(email, name, password, confirmPassword)){
            return;
        }
        firebase.openConnection(activity);
        if(firebase.registerUser(email, password)){
            onSuccesRegistration();
        }
        else {
            validator.TerribleError();
        }
    }

    private void onSuccesRegistration() {
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
