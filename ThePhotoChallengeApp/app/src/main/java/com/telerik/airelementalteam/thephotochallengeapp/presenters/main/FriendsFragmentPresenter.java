package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class FriendsFragmentPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    public FriendsFragmentPresenter(){}

    public FriendsFragmentPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void populateList(ListView listView) {
        this.listAdapter = new FirebaseListAdapter<User>() {
            @Override
            protected void populateView(View convertView, User model) {
                ((TextView)convertView.findViewById(R.id.friend_item_name)).setText(model.getName());
                ((TextView)convertView.findViewById(R.id.friend_item_email)).setText(model.getEmail());
            }
        };
        //listView.setListAdapter(listAdapter);
    }
}
