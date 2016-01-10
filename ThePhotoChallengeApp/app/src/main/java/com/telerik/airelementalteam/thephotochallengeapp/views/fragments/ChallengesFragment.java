package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.airelementalteam.thephotochallengeapp.R;

public class ChallengesFragment extends Fragment {


    public ChallengesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false);
    }

}
