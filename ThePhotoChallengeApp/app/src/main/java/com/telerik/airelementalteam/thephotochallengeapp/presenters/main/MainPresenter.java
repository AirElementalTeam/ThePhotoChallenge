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

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestConfirmedListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestListener;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.views.MainActivity;

import java.util.Random;

public class MainPresenter implements IOnTaskFinishedListener, IOnFriendRequestListener, IOnFriendRequestConfirmedListener {

    private Activity activity;
    private FirebaseAdapter firebase;

    private String currentUserName;
    private String currentUserEmail;
    String tempName;
    String tempEmail;

    private String newFriendName;
    private String newFriendEmail;
    private String newFriendUID;
    private String friendRequestFromUserName;
    private String friendRequestFromUserEmail;
    private String friendRequestFromUserUID;
    String tempfriendRequestFromUserName;
    String tempfriendRequestFromUserEmail;
    String tempfriendRequestFromUserUID;


    public MainPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void getNameAndMail(){
        firebase.currentUserNameAndMail(this);
    }

    public void listenForChanges() {
        firebase.listenForChanges(this, this);
    }

    public void logoutUser(){firebase.logoutUser();}

    @Override
    public void onSuccess() {
        //add name and mail to the drawer
        TextView nameText = (TextView) this.activity.findViewById(R.id.header_username);
        TextView emailText = (TextView) this.activity.findViewById(R.id.header_email);
        if(nameText != null && emailText != null && !this.currentUserName.equals(tempName) && !this.currentUserEmail.equals(tempEmail)) {
            tempName = currentUserName;
            tempEmail = currentUserEmail;
            nameText = (TextView) this.activity.findViewById(R.id.header_username);
            nameText.setText(currentUserName);
            emailText = (TextView) this.activity.findViewById(R.id.header_email);
            emailText.setText(currentUserEmail);
        }
        //System.out.println("After child added on success in main");
    }

    @Override
    public void onError() {

    }

    public void setFriendRequestFromUserUID(String friendRequestFromUserUID) {
        this.friendRequestFromUserUID = friendRequestFromUserUID;
    }

    @Override
    public void friendRequestReceived() {
        System.out.println("Inside friendRequestReceived");
        System.out.println("friendRequestFromUserName   -  " + friendRequestFromUserName);
        System.out.println("tempfriendRequestFromUserName   -  " + tempfriendRequestFromUserName);
        System.out.println("friendRequestFromUserUID   -  " + friendRequestFromUserUID);
        System.out.println("tempfriendRequestFromUserUID   -  " + tempfriendRequestFromUserUID);
        if(!this.friendRequestFromUserName.equals(tempfriendRequestFromUserName)
                && !this.friendRequestFromUserEmail.equals(tempfriendRequestFromUserEmail)
                && !this.friendRequestFromUserUID.equals(tempfriendRequestFromUserUID)) {
            tempfriendRequestFromUserName = friendRequestFromUserName;
            tempfriendRequestFromUserEmail = friendRequestFromUserEmail;
            tempfriendRequestFromUserUID = friendRequestFromUserUID;

            Intent notificationIntent = new Intent(activity.getApplicationContext(), MainActivity.class);

            Bundle extras = new Bundle();
            extras.putString("name", tempfriendRequestFromUserName);
            extras.putString("email", tempfriendRequestFromUserEmail);
            extras.putString("uid", tempfriendRequestFromUserUID);
            extras.putString("notification", "notificationFriendRequest");
            notificationIntent.putExtras(extras);

            Random generator = new Random();
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("New friend!")
                    .setAutoCancel(true)
                    .setContentText(this.friendRequestFromUserName + " wants to be friends.")
                    .setContentIntent(PendingIntent.getActivity(activity.getApplicationContext(), Math.abs(generator.nextInt()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            builder.setOngoing(false);
            builder.setAutoCancel(true);

            Notification not = builder.build();
            not.defaults = Notification.DEFAULT_LIGHTS;
            not.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
            NotificationManager manager = (NotificationManager)this.activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Math.abs(generator.nextInt()), not);
        }
    }

    @Override
    public void onNewFriend() {
        Intent notificationIntent = new Intent(activity.getApplicationContext(), MainActivity.class);

        //WTF
        System.out.println("I AM INSIDE FRIEND CONFIRM NOTIFICATION FACTORY");
        Bundle extras = new Bundle();
        extras.putString("name" ,newFriendName);
        extras.putString("email", newFriendEmail);
        extras.putString("uid", newFriendUID);
        extras.putString("notification", "notificationFriendRequestConfirmed");
        notificationIntent.putExtras(extras);

        Random generator = new Random();
        System.out.println("PUTTING EXTRA");
        System.out.println(notificationIntent.getExtras().toString());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New friend!")
                .setAutoCancel(true)
                .setContentText("You and " + this.newFriendName + " are now friends")
                .setContentIntent(PendingIntent.getActivity(activity.getApplicationContext(), Math.abs(generator.nextInt()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setOngoing(false);
        builder.setAutoCancel(true);

        Notification not = builder.build();
        not.defaults = Notification.DEFAULT_LIGHTS;
        not.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager)this.activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, not);

    }

    public String getNewFriendName() {
        return newFriendName;
    }

    public void setNewFriendName(String newFriendName) {
        this.newFriendName = newFriendName;
    }

    public String getNewFriendEmail() {
        return newFriendEmail;
    }

    public void setNewFriendEmail(String newFriendEmail) {
        this.newFriendEmail = newFriendEmail;
    }

    public String getNewFriendUID() {
        return newFriendUID;
    }

    public void setNewFriendUID(String newFriendUID) {
        this.newFriendUID = newFriendUID;
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
}
