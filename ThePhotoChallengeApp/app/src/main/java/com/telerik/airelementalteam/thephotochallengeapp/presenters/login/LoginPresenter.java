package com.telerik.airelementalteam.thephotochallengeapp.presenters.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.DatabaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseConnection;
import Common.Validator;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.IPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.HomeActivity;
import com.telerik.airelementalteam.thephotochallengeapp.views.RegisterActivity;

import java.util.Arrays;

public class LoginPresenter implements IOnTaskFinishedListener {
    private Activity activity;
    private Validator validator;
    private FirebaseConnection firebase;

    private ProgressDialog progressDialog;
    DatabaseAdapter helper;

    public LoginPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseConnection();
        validator = new Validator(this.activity);
        helper = new DatabaseAdapter(this.activity);
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

        helper.openDB();
        String[] names = helper.getAllNames();

        if(!(Arrays.asList(names).contains(email)))
        {
            long id = helper.insertData(email, password);
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
