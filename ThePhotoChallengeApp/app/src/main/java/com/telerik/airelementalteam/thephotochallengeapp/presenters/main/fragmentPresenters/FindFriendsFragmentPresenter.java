package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.FindFriendsFragment;

import java.util.ArrayList;
import java.util.List;

public class FindFriendsFragmentPresenter {

    private Activity activity;
    private FindFriendsFragment fragment;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;
    private List<String> userUIDs;

    public FindFriendsFragmentPresenter(Activity activity, FindFriendsFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.firebase = new FirebaseAdapter();
        this.userUIDs = new ArrayList<>();
    }

    public void populateFriendList(ListView listView) {

        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.user_list_item, firebase.getRefUsers()) {
            @Override
            protected void populateView(View convertView, User model) {
                userUIDs.add(model.getUid());

                ((TextView) convertView.findViewById(R.id.list_user_name)).setText(model.getName());
                ((TextView) convertView.findViewById(R.id.list_user_email)).setText(model.getEmail());
            }
        };

        this.fragment.setUsersUIDS(this.userUIDs);
        listView.setAdapter(listAdapter);
    }

    public void cleanup() {
        listAdapter.cleanup();
    }
}
