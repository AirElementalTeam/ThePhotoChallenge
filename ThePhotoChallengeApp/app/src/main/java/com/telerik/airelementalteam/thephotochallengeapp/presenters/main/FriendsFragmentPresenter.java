package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

import Common.Constants;

public class FriendsFragmentPresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    private ProgressDialog dialog;

    //public FriendsFragmentPresenter(){}

    public FriendsFragmentPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        dialog = new ProgressDialog(activity.getApplicationContext());
    }

    public void populateFriendList(ListView listView, final TextView noFriends) {

        //TODO: fix the bug - current user ref gets only uid-s, we need users
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.user_list_item, firebase.getRefUsers()) {
            @Override
            protected void populateView(final View convertView, final User model) {
                Query userFriends = firebase.getRefUsers().child(firebase.currentUserUID()).child(Constants.FRIENDS).orderByKey();
                userFriends.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(model.getUid())) {
                            ((TextView) convertView.findViewById(R.id.list_user_name)).setText(model.getName());
                            ((TextView) convertView.findViewById(R.id.list_user_email)).setText(model.getEmail());
                            noFriends.setVisibility(View.GONE);
                        } else {
                            //convertView = activity.getLayoutInflater().inflate(R.layout.row_null, null);
                            convertView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        };
        listView.setAdapter(listAdapter);
    }

    public void cleanup() {
        listAdapter.cleanup();
    }
}
