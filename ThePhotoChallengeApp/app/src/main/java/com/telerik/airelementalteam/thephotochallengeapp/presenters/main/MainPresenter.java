package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnChildrenListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

public class MainPresenter implements IOnTaskFinishedListener, IOnChildrenListener {

    private Activity activity;
    private FirebaseAdapter firebase;

    private String currentUserName;
    private String currentUserEmail;
    String tempName;
    String tempEmail;

    private String friendRequestFromUserName;
    String tempfriendRequestFromUserName;

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

    public void setFriendRequestFromUserName(String friendRequestFromUserName) {
        this.friendRequestFromUserName = friendRequestFromUserName;
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
        //add name and mail to the drawer
        if(!this.currentUserName.equals(tempName) && !this.currentUserEmail.equals(tempEmail)) {
            tempName = currentUserName;
            tempEmail = currentUserEmail;
            TextView nameText = (TextView) this.activity.findViewById(R.id.header_username);
            nameText.setText(currentUserName);
            TextView emailText = (TextView) this.activity.findViewById(R.id.header_email);
            emailText.setText(currentUserEmail);
        }

        System.out.println("Aftr child added on success in main");
    }

    @Override
    public void onError() {

    }

    @Override
    public void childAdded() {
        System.out.println("after child added");
        //react to friend request
        if(!this.friendRequestFromUserName.equals(tempfriendRequestFromUserName)) {
            tempfriendRequestFromUserName = friendRequestFromUserName;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
            builder.setContentTitle("New friend!");
            builder.setContentText(this.friendRequestFromUserName + " wants to be friends.");
            builder.setSmallIcon(R.drawable.ic_notification);

            Notification not = builder.build();
            NotificationManager manager = (NotificationManager)this.activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(123, not);
        }
    }
}
