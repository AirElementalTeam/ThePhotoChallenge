package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnChildrenListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.views.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainPresenter implements IOnTaskFinishedListener, IOnChildrenListener {

    private Activity activity;
    private FirebaseAdapter firebase;

    private String currentUserName;
    private String currentUserEmail;
    String tempName;
    String tempEmail;

    private String friendRequestFromUserName;
    private String friendRequestFromUserEmail;
    String tempfriendRequestFromUserName;
    String tempfriendRequestFromUserEmail;

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

    public void setFriendRequestFromUserEmail(String friendRequestFromUserEmail) {
        this.friendRequestFromUserEmail = friendRequestFromUserEmail;
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
        System.out.println("After child added on success in main");
    }

    @Override
    public void onError() {

    }

    @Override
    public void childAdded() {
        System.out.println("after child added");

        //react to friend request
        if(!this.friendRequestFromUserName.equals(tempfriendRequestFromUserName) && !this.friendRequestFromUserEmail.equals(tempfriendRequestFromUserEmail)) {
            tempfriendRequestFromUserName = friendRequestFromUserName;
            tempfriendRequestFromUserEmail = friendRequestFromUserEmail;

            //TODO: VERY BIG TODO!!!
            Intent notificationIntent = new Intent(activity.getApplicationContext(), MainActivity.class);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Bundle extras = new Bundle();
            extras.putString("name" ,tempfriendRequestFromUserName);
            extras.putString("email", tempfriendRequestFromUserEmail);
            extras.putString("notification", "notificationFriendRequest");
            //notificationIntent.putExtra("name", this.friendRequestFromUserName);
            //notificationIntent.putExtra("notification", "notificationFriendRequest");
            //notificationIntent.putExtra("email", this.friendRequestFromUserEmail);
            notificationIntent.putExtras(extras);

            System.out.println("PUTTING EXTRA");
            System.out.println(notificationIntent.getExtras().toString());

            Random generator = new Random();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("New friend!")
                    .setContentText(this.friendRequestFromUserName + " wants to be friends.")
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setOngoing(false)
                    .setContentIntent(PendingIntent.getActivity(activity.getApplicationContext(), 0, notificationIntent, 0));
            builder.setOngoing(false);
            builder.setAutoCancel(true);

            Notification not = builder.build();
            not.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager manager = (NotificationManager)this.activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(123, not);
        }
    }
}
