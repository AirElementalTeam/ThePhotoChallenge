package com.telerik.airelementalteam.thephotochallengeapp.presenters.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.views.LoginActivity;

import Common.Validator;

public class RegisterPresenter implements IOnTaskFinishedListener {
    private Activity activity;
    private Validator validator;
    private FirebaseAdapter firebase;

    private ProgressDialog progressDialog;

    public RegisterPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        this.validator = new Validator(this.activity);
    }

    public void attemptRegistration(String email, String name, String password, String confirmPassword){
        boolean validResult = validator.validateRegister(email, name, password, confirmPassword);
        if(!validResult){
            return;
        }

        progressDialog = ProgressDialog.show(activity, "Singing up...", null);
        progressDialog.show();
        firebase.openConnection();
        firebase.registerUser(name, email, password, this);
    }

    @Override
    public void onSuccess() {
        progressDialog.hide();
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onError() {
        progressDialog.hide();
        validator.TerribleError();
    }
}
