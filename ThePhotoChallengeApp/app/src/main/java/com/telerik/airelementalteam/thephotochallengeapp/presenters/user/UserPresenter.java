package com.telerik.airelementalteam.thephotochallengeapp.presenters.user;

import android.app.Activity;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.UserFragment;

import Common.Converter;


public class UserPresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private FirebaseAdapter firebase;
    private Converter converter;
    private UserFragment fragment;

    private String currentUserUID;
    //private String otherUserUID;

    private boolean friendRequestSend;
    private boolean friendRequestReceived;
    private boolean friends;

    public UserPresenter(Activity activity, UserFragment fragment){
        this.activity = activity;
        this.fragment = fragment;
        this.firebase = new FirebaseAdapter();
        this.converter = new Converter();
    }

    public void sendFriendRequest(String email) {
        //find users by mail
        currentUserUID = firebase.currentUserUID();
        String escapedMail = converter.escapeEmail(email);
        Firebase refUsers = firebase.getRefUsers();
        Firebase refUsersByEmail = firebase.getRefUsersByEmail();
        Query fromUser = refUsers.child(currentUserUID);
        Query toUser = refUsersByEmail.child(escapedMail);
        System.out.println("________________________________");
        System.out.println(toUser);
        System.out.println("________________________________");
        firebase.sendAndReceiveFriendRequest(this, fromUser, toUser);
    }

    public void confirmFriendship(String uid) {
        firebase.confirmFriendsWith(uid, this);
    }

    public void getUserInfo(String uid) {
        firebase.getFriendshipState(this, uid);
    }

    @Override
    public void onSuccess() {
        System.out.println("onSuccess in UserPresenter");
        System.out.println("friendRequestSend  ------> " + friendRequestSend);
        System.out.println("friendRequestReceived  ------> " + friendRequestReceived);
        System.out.println("friends  ------> " + friends);

        //friend request send
        if(friendRequestSend) {

            this.fragment.requestSendLayout();
            this.friends = false;
        } else if(friendRequestReceived) {
            this.fragment.requestReceivedLayout();
        } else if(friends) {
            this.fragment.friendLayout();
            this.friendRequestSend = false;
        } else {
            this.fragment.notFriendLayout();
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



    public void setFriendRequestReceived(boolean friendRequestReceived) {
        this.friendRequestReceived = friendRequestReceived;
    }
}
