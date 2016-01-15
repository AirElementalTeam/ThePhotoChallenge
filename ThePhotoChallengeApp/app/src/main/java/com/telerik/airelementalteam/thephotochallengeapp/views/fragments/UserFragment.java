package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.user.UserPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private UserPresenter presenter;
    private String email;
    private String name;
    private boolean friendRequestReceived;
    private boolean friendRequestSend;
    private boolean notFriend;
    private boolean isFriend;

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new UserPresenter(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView nameText = (TextView) view.findViewById(R.id.friend_item_name);
        TextView emailText = (TextView) view.findViewById(R.id.friend_item_email);
        System.out.println(this.name);
        nameText.setText(this.name);
        emailText.setText(this.email);

        TextView noFriendText = (TextView) view.findViewById(R.id.private_user_text);
        TextView friendRequestText = (TextView) view.findViewById(R.id.friend_request_text);
        AppCompatButton addFriendButton = (AppCompatButton) view.findViewById(R.id.add_friend_btn);
        AppCompatButton confirmFriendButton = (AppCompatButton) view.findViewById(R.id.confirm_friend_button);
        AppCompatButton declineFriendButton = (AppCompatButton) view.findViewById(R.id.decline_friend_button);


        if(friendRequestReceived) {
            noFriendText.setVisibility(View.GONE);
            addFriendButton.setVisibility(View.GONE);
            friendRequestText.setVisibility(View.VISIBLE);
            confirmFriendButton.setVisibility(View.VISIBLE);
            declineFriendButton.setVisibility(View.VISIBLE);

        } else if(friendRequestSend){
            noFriendText.setVisibility(View.GONE);
            addFriendButton.setVisibility(View.GONE);
            friendRequestText.setText("Pending answer from user...");
            friendRequestText.setVisibility(View.VISIBLE);
            confirmFriendButton.setVisibility(View.GONE);
            declineFriendButton.setVisibility(View.GONE);

        } else if(notFriend){
            noFriendText.setVisibility(View.VISIBLE);
            addFriendButton.setVisibility(View.VISIBLE);
            friendRequestText.setVisibility(View.GONE);
            confirmFriendButton.setVisibility(View.GONE);
            declineFriendButton.setVisibility(View.GONE);
        }
        else if(isFriend){
            noFriendText.setVisibility(View.GONE);
            addFriendButton.setVisibility(View.GONE);
            friendRequestText.setVisibility(View.GONE);
            confirmFriendButton.setVisibility(View.GONE);
            declineFriendButton.setVisibility(View.GONE);
        }

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendFriendRequest(email);
            }
        });

        confirmFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add to friends
            }
        });
        declineFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: don't add to friends
            }
        });
        return view;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
