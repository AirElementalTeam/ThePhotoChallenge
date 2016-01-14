package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

public class MainPresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private FirebaseAdapter firebase;

    private String currentUserName;
    private String currentUserEmail;

    public MainPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    public void getNameAndMail(){
        firebase.currentUserNameAndMail(this);
    }

    public void listenForChanges() {
        firebase.listenForChanges(this);
    }

    public void logoutUser(){firebase.logoutUser();}

    @Override
    public void onSuccess() {
        if(this.currentUserName != null && this.currentUserEmail != null) {
            TextView nameText = (TextView) this.activity.findViewById(R.id.header_username);
            nameText.setText(currentUserName);
            TextView emailText = (TextView) this.activity.findViewById(R.id.header_email);
            emailText.setText(currentUserEmail);
        }
    }

    @Override
    public void onError() {

    }
}
