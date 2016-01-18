package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.ChallengesPresenter;

public class ChallengesFragment extends Fragment {

    private ChallengesPresenter presenter;

    private GridView challengesGrid;
    public ChallengesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);
        this.presenter = new ChallengesPresenter(getActivity());
        return view;
    }

}
