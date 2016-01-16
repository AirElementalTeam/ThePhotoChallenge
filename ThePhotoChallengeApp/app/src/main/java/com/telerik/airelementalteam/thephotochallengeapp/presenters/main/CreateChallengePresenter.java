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
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

import Common.Validator;

public class CreateChallengePresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;
    private Validator validator;

    private boolean expanded;
    private boolean[] checkSelected;
    private int count;

    public CreateChallengePresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
        this.validator = new Validator(this.activity);
    }

    //we will never use this
    public void populateDropdown(ListView dropdownList) {
        this.count = 0;
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.fragment_create_challenge, firebase.currentUserFriends()) {
            @Override
            protected void populateView(final View view, User user) {

                ((TextView) view.findViewById(R.id.dropdown_name)).setText(user.getName());
            }
        };
        dropdownList.setAdapter(listAdapter);
        count = dropdownList.getCount();
    }

    public void createChallenge(String title, String theme, String dueDate) {
        boolean valid = validator.validateNewChallenge(title, theme, dueDate);
        if(!valid) {
            return;
        }

        Challenge newChallenge = new Challenge(title, theme, dueDate);
        firebase.createNewChallenge(newChallenge, this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
