package com.telerik.airelementalteam.thephotochallengeapp.presenters.user;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnChildrenListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.UserFragment;

import java.util.HashMap;

import Common.Converter;


public class UserPresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private FirebaseAdapter firebase;
    private Converter converter;
    private UserFragment fragment;

    private String currentUserUID;
    //private String otherUserUID;

    private boolean friendRequestSend;
    private boolean friends;

    public UserPresenter(Activity activity, UserFragment fragment){
        this.activity = activity;
        this.fragment = fragment;
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
    }

    public void confirmFriendship(String uid) {
        firebase.confirmFriendsWith(uid, this);
    }

    @Override
    public void onSuccess() {
        //friend request send
        if(friendRequestSend) {
            Toast.makeText(activity.getApplicationContext(), "Friend request send", Toast.LENGTH_SHORT).show();
            this.fragment.requestSendLayout();
            this.friends = false;
        }
        if(friends) {
            this.fragment.friendLayout();
            this.friendRequestSend = false;
        }
    }

    @Override
    public void onError() {

    }

    public void setFriendRequestSend(boolean friendRequestSend) {
        this.friendRequestSend = friendRequestSend;
    }

    public void setFriends(boolean friends) {
        this.friends = friends;
    }
}
