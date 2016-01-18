package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;

import java.util.Date;
import java.util.Random;

import Common.Constants;
import Common.Validator;

public class CreateChallengePresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private Context context;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<User> listAdapter;
    private Validator validator;

    private ProgressDialog progressDialog;

    private String newChallengeID;

    public CreateChallengePresenter(Activity activity) {
        this.activity = activity;
        this.context =  this.activity;
        this.firebase = new FirebaseAdapter();
        this.validator = new Validator(this.activity);
    }

    public void createChallenge(String title, String theme, String dueDate) {
        boolean valid = validator.validateNewChallenge(title, theme, dueDate);
        if (!valid) {
            return;
        }

        progressDialog = ProgressDialog.show(activity, "Creating new challenge...", null);
        progressDialog.show();
        Challenge newChallenge = new Challenge(title, theme, dueDate);
        Random generator = new Random();
        newChallengeID = newChallenge.getTitle() + "-" + Constants.THEMES + "-" + newChallenge.getTheme() + Constants.UID + firebase.currentUserUID();
        newChallenge.setId(newChallengeID);
        firebase.createNewChallenge(newChallenge, this);
    }

    //we will never use this
    public void populateDropdown(ListView dropdownList) {
        this.listAdapter = new FirebaseListAdapter<User>(this.activity, User.class, R.layout.fragment_create_challenge, firebase.refUserFriends()) {
            @Override
            protected void populateView(final View view, User user) {
                ((TextView) view.findViewById(R.id.dropdown_name)).setText(user.getName());
            }
        };
        dropdownList.setAdapter(listAdapter);
    }

    public boolean validateDate(Date today, Date picked) {
        if(validator.validateDates(today, picked)){
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess() {

        progressDialog.cancel();
        SingleChallengeFragment fragment = new SingleChallengeFragment();
        fragment.setChallengeID(newChallengeID);
        FragmentTransaction transaction = this.activity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onError() {

    }
}
