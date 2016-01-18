package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.ChallengesPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Common.Path;

public class ChallengesFragment extends Fragment {

    private ChallengesPresenter presenter;
    private List<String> challengesIds;

    private GridView challengesGrid;
    public ChallengesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        this.presenter = new ChallengesPresenter(getActivity(), this);
        this.challengesIds = new ArrayList<>();
        ListView challengesList = (ListView) view.findViewById(android.R.id.list);
        presenter.populateChallengesList(challengesList);

        challengesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String challengeId = challengesIds.get(position);
                SingleChallengeFragment fragment = new SingleChallengeFragment();
                fragment.setChallengeID(challengeId);
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.commit();
            }
        });
        return view;
    }

    public List<String> getChallengesIds() {
        return challengesIds;
    }

    public void setChallengesIds(List<String> challengesIds) {
        this.challengesIds = challengesIds;
    }
}
