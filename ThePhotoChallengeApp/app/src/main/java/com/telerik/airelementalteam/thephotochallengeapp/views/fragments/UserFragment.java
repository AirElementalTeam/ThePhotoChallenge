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

    public UserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new UserPresenter(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //some code to set the email correctly
        //email = "zazu@lionking.com";
        TextView nameText = (TextView) view.findViewById(R.id.friend_item_name);
        System.out.println(name);
        System.out.println(email);
        TextView emailText = (TextView) view.findViewById(R.id.friend_item_email);
        nameText.setText(this.name);
        emailText.setText(this.email);

        AppCompatButton addFriendButton = (AppCompatButton) view.findViewById(R.id.add_friend_btn);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendFriendRequest(email);
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
}
