package com.telerik.airelementalteam.thephotochallengeapp.presenters.user;

import android.app.Activity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

import java.util.HashMap;

import Common.Converter;


public class UserPresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private FirebaseAdapter firebase;
    private Converter converter;

    private String currentUserUID;
    private String otherUserUID;

    public UserPresenter(Activity activity){
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        this.converter = new Converter();
    }

    public void sendFriendRequest(String email) {
        currentUserUID = firebase.currentUserUID();
        String escapedMail = converter.escapeEmail(email);
        Firebase refUsers = firebase.getRefUsers();
        Firebase refUsersByEmail = firebase.getRefUsersByEmail();
        Query fromUser = refUsers.child(currentUserUID);

        Firebase refMail = refUsersByEmail.child(escapedMail);
        Query toUser = refMail.orderByChild(activity.getString(R.string.uid));
        firebase.sendAndReceiveFriendRequest(this, fromUser, toUser);

        //create friend request (sent for current user/ recieved for other user)
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
