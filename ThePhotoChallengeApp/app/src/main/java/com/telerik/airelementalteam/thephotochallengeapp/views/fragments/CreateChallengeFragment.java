package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerik.airelementalteam.thephotochallengeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateChallengeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateChallengeFragment extends Fragment {


    public CreateChallengeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_challenge, container, false);
    }
}
