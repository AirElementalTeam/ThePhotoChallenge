package com.telerik.airelementalteam.thephotochallengeapp.presenters.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.DatabaseAdapter;

import Common.Validator;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;

import java.util.Arrays;

public class LoginPresenter implements IOnTaskFinishedListener {
    private Activity activity;
    private Validator validator;
    private FirebaseAdapter firebase;
    private DatabaseAdapter SQLite;

    private ProgressDialog progressDialog;


    public LoginPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        validator = new Validator(this.activity);
        SQLite = new DatabaseAdapter(this.activity);
    }

    public void attemptLogin(String email, String password) {
        boolean validResult = validator.validateLogin(email, password);
        if(!validResult)
        {
            return;
        }

        progressDialog = ProgressDialog.show(activity, "Authenticating...", null);
        progressDialog.show();
        firebase.openConnection();
        firebase.loginUser(email, password, this);

        SQLite.openDB();
        String[] names = SQLite.getAllNames();

        if(!(Arrays.asList(names).contains(email)))
        {
            long id = SQLite.insertData(email, password);
        }

    }

    @Override
    public void onSuccess() {
        progressDialog.hide();
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onError() {
        progressDialog.hide();
        validator.invalidCredentials();
    }
}
