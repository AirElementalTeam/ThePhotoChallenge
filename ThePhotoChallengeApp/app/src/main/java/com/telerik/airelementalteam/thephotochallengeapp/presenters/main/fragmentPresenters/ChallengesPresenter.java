package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ChallengesFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ChallengesPresenter {

    private FragmentActivity activity;
    private ChallengesFragment fragment;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<Challenge> listAdapter;
    List<String> challengesIds;

    public ChallengesPresenter(FragmentActivity activity, ChallengesFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.firebase = new FirebaseAdapter();
        challengesIds = new ArrayList<>();
    }

    public void populateChallengesList(ListView challengesList) {
        this.listAdapter = new FirebaseListAdapter<Challenge>(this.activity, Challenge.class, R.layout.challenge_list_item, firebase.getRefChallanges()) {

            @Override
            protected void populateView(View convertView, Challenge challenge) {
                challengesIds.add(challenge.getId());
                ((TextView) convertView.findViewById(R.id.challenge_list_title)).setText(challenge.getTitle());
                ((TextView) convertView.findViewById(R.id.challenge_list_theme)).setText(challenge.getTheme());
                System.out.println(challengesIds);
            }
        };
        this.fragment.setChallengesIds(this.challengesIds);
        challengesList.setAdapter(listAdapter);
    }
}
