package com.telerik.airelementalteam.thephotochallengeapp.presenters.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;

import Common.Validator;

public class RegisterPresenter implements IOnTaskFinishedListener {
    private Activity activity;
    private Validator validator;
    private FirebaseConnection firebase;

    private ProgressDialog progressDialog;

    public RegisterPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseConnection(this.activity);
        this.validator = new Validator(this.activity);
    }

    public void attemptRegistration(String email, String name, String password, String confirmPassword){
        System.out.println("Inside attemptRegistration method of RegisterPresenter");
        boolean validResult = validator.validateRegister(email, name, password, confirmPassword);
        System.out.println("After validator back in attemptRegistration. VALIDATOR RESULT: ----> " + validResult);
        if(!validResult){
            return;
        }

        progressDialog = ProgressDialog.show(activity, "Singing up...", null);
        progressDialog.show();
        firebase.openConnection(activity);
        System.out.println("After opened connection to Firebase");
        firebase.registerUser(email, password, this);
    }

    @Override
    public void onSuccess() {
        progressDialog.hide();
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
    }

    @Override
    public void onError() {
        progressDialog.hide();
        validator.TerribleError();
    }
}
