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
        System.out.println("Inside attemptRegistration method of RegisterPresenter");
        boolean validResult = validator.validateRegister(email, name, password, confirmPassword);
        System.out.println("After validator back in attemptRegistration. VALIDATOR RESULT: ----> " + validResult);
        if(!validResult){
            return;
        }
        firebase.openConnection(activity);
        System.out.println("After opened connection to Firebase");
        boolean result = firebase.registerUser(email, password);
        System.out.println("Back in RegisterPresenter -> registerUser returned " + result);
        if(result) {
            System.out.println("Success" + result);
            onSuccesRegistration();
        }
        else {
            System.out.println("Fail" + result);
            validator.TerribleError();
        }
    }

    private void onSuccesRegistration() {
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }
}
