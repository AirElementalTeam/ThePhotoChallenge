package com.telerik.airelementalteam.thephotochallengeapp.presenters.user;

import android.app.Activity;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

import Common.Converter;


public class UserPresenter {
    private Activity activity;
    private FirebaseAdapter firebase;
    private Converter converter;

    public UserPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        this.converter = new Converter();
    }

    public void sendFriendRequest(String email) {
        String currentUserUID = firebase.currentUserUID();

        //escapeEmail
        //find user uid by email
        //find user by uid
        //create friend request (sent for current user/ recieved for other user)
    }
}
