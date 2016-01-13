package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class UsersListAdapter extends FirebaseListAdapter<User> {

    private String userName;
    private String userEmail;

    public UsersListAdapter(Query refQuery, Activity activity, int layout, String username, String email) {
        super(refQuery, User.class, layout, activity);
        this.userName = username;
        this.userEmail = email;
    }

    @Override
    protected void populateView(View convertView, User user) {
        String username = user.getName();
        String email = user.getEmail();
        TextView usernameText = (TextView) convertView.findViewById(R.id.friend_item_name);
        usernameText.setText(username);
        TextView userEmailText = (TextView) convertView.findViewById(R.id.friend_item_email);
        userEmailText.setText(email);
    }
}
