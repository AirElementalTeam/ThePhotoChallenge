package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnChildrenListener;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.user.UserPresenter;

public class UserFragment extends Fragment implements IOnChildrenListener {

    private UserPresenter presenter;
    private String email;
    private String name;
    private String uid;
    private boolean friendRequestReceived;
    private boolean friendRequestSend;
    private boolean notFriend;
    private boolean isFriend;

    private TextView noFriendText;
    private TextView friendRequestText;
    private TextView friendsText;
    private AppCompatButton addFriendButton;
    private AppCompatButton confirmFriendButton;
    private AppCompatButton declineFriendButton;

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new UserPresenter(this.getActivity(), this);
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextView nameText = (TextView) view.findViewById(R.id.friend_item_name);
        TextView emailText = (TextView) view.findViewById(R.id.friend_item_email);
        //System.out.println(this.name);
        nameText.setText(this.name);
        emailText.setText(this.email);

        noFriendText = (TextView) view.findViewById(R.id.private_user_text);
        friendRequestText = (TextView) view.findViewById(R.id.friend_request_text);
        friendsText = (TextView) view.findViewById(R.id.friend_text);
        addFriendButton = (AppCompatButton) view.findViewById(R.id.add_friend_btn);
        confirmFriendButton = (AppCompatButton) view.findViewById(R.id.confirm_friend_button);
        declineFriendButton = (AppCompatButton) view.findViewById(R.id.decline_friend_button);

        presenter.getUserInfo(this.uid);

        return view;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setFriendRequestRecieved(boolean friendRequestReceived) {
        this.friendRequestReceived = friendRequestReceived;
    }

    public void setNotFriend(boolean notFriend) {
        this.notFriend = notFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public void setFriendRequestSend(boolean friendRequestSend) {
        this.friendRequestSend = friendRequestSend;
    }

    public void notFriendLayout(){
        noFriendText.setVisibility(View.VISIBLE);
        addFriendButton.setVisibility(View.VISIBLE);
        friendsText.setVisibility(View.GONE);
        friendRequestText.setVisibility(View.GONE);
        confirmFriendButton.setVisibility(View.GONE);
        declineFriendButton.setVisibility(View.GONE);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Friend request send", Toast.LENGTH_SHORT).show();
                presenter.sendFriendRequest(email);
            }
        });
    }

    public void requestReceivedLayout() {
        noFriendText.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);
        friendsText.setVisibility(View.GONE);
        friendRequestText.setVisibility(View.VISIBLE);
        confirmFriendButton.setVisibility(View.VISIBLE);
        declineFriendButton.setVisibility(View.VISIBLE);
        confirmFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.confirmFriendship(uid);
            }
        });
        declineFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: don't add to friends
            }
        });
    }

    public void requestSendLayout() {
        noFriendText.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);
        friendRequestText.setText("Friend request send!\nPending answer from user...");
        friendRequestText.setVisibility(View.VISIBLE);
        friendsText.setVisibility(View.GONE);
        confirmFriendButton.setVisibility(View.GONE);
        declineFriendButton.setVisibility(View.GONE);
    }

    public void friendLayout() {
        noFriendText.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);
        friendsText.setVisibility(View.VISIBLE);
        friendRequestText.setVisibility(View.GONE);
        confirmFriendButton.setVisibility(View.GONE);
        declineFriendButton.setVisibility(View.GONE);
    }

    @Override
    public void childAdded() {

    }
}
