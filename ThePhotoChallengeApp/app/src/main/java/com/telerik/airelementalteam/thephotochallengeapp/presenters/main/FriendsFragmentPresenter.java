package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.app.DownloadManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class FriendsFragmentPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    //public FriendsFragmentPresenter(){}

    public FriendsFragmentPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void populateFriendList(ListView listView, TextView noFriends) {

        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, android.R.layout.two_line_list_item, firebase.currentUserFriends()) {
            @Override
            protected void populateView(View convertView, User model) {
                ((TextView) convertView.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView) convertView.findViewById(android.R.id.text2)).setText(model.getEmail());
            }
        };

        if(listAdapter.getCount() == 0) {
            noFriends.setText("You don't have any friends. Try searching for some!");
        }
        listView.setAdapter(listAdapter);
    }

    public void cleanup() {
        listAdapter.cleanup();
    }
}
