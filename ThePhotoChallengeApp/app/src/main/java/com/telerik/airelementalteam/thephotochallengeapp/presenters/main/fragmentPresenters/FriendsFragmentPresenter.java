package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class FriendsFragmentPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    private ProgressDialog dialog;

    public FriendsFragmentPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        dialog = new ProgressDialog(activity.getApplicationContext());
    }

    public void populateFriendList(ListView listView, final TextView noFriends) {

        //TODO: fix
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.user_list_item, firebase.refUserFriends()) {
            @Override
            protected void populateView(final View convertView, final User model) {
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
