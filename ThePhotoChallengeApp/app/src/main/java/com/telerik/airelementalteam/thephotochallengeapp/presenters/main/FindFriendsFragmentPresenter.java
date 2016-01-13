package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class FindFriendsFragmentPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    public FindFriendsFragmentPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void populateFriendList(ListView listView) {
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.user_list_item, firebase.getRefUsers()) {
            @Override
            protected void populateView(View convertView, User model) {
                ((TextView) convertView.findViewById(R.id.list_user_name)).setText(model.getName());
                ((TextView) convertView.findViewById(R.id.list_user_email)).setText(model.getEmail());
            }
        };

        listView.setAdapter(listAdapter);
    }

    public void cleanup() {
        listAdapter.cleanup();
    }
}
