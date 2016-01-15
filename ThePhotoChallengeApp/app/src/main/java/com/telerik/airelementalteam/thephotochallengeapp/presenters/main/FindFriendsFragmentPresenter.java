package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.Activity;
import android.view.View;
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
                System.out.println(model.toString());
                if(model.getUid().equals(firebase.currentUserUID())){
                    convertView = activity.getLayoutInflater().inflate(R.layout.row_null, null);
                    convertView.setVisibility(View.GONE);
                    //TODO: fix list item visibility
                    //http://stackoverflow.com/questions/13646147/hide-row-from-listview-without-taking-up-space
                    //yeah I agree - that's why I didn't go that route for this particular instance :-) I forgot to mention in my comment above: once getCount() returns a value that excludes the data rows you want to hide, you should locate the hidden data rows to the end of the array that's used by your adapter.
                }
                else {
                    ((TextView) convertView.findViewById(R.id.list_user_name)).setText(model.getName());
                    ((TextView) convertView.findViewById(R.id.list_user_email)).setText(model.getEmail());
                }
            }
        };

        listView.setAdapter(listAdapter);
    }

    public void cleanup() {
        listAdapter.cleanup();
    }
}
