package com.telerik.airelementalteam.thephotochallengeapp.presenters.user;

import android.app.Activity;

import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;

import Common.Validator;

public class UserPresenter {
    private Activity activity;
    private FirebaseAdapter firebase;

    public UserPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void sendFriendRequest(String email) {
        String currentUserUID = firebase.currentUserUID();

        //escapeEmail
        //find user uid by email
        //find user by uid
        //create friend request (sent for current user/ recieved for other user)
    }
}
