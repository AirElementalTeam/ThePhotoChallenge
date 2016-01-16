package com.telerik.airelementalteam.thephotochallengeapp.presenters.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

public class CreateChallengePresenter {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;

    private boolean expanded;
    private boolean[] checkSelected;
    private int count;

    public CreateChallengePresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    //we will never use this
    public void populateDropdown(ListView dropdownList) {
        this.count = 0;
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.fragment_create_challenge, firebase.refFriends()) {
            @Override
            protected void populateView(final View view, User user) {

                ((TextView) view.findViewById(R.id.dropdown_name)).setText(user.getName());
            }
        };
        dropdownList.setAdapter(listAdapter);
        count = dropdownList.getCount();
    }
}
