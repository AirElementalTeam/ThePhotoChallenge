package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FriendsFragmentPresenter;

public class FriendsFragment extends ListFragment {

    private FriendsFragmentPresenter presenter;

    public FriendsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FriendsFragmentPresenter(this.getActivity());
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);
        ListView friendsList = (ListView) view.findViewById(android.R.id.list);
        presenter.populateList(friendsList);

        return view;
    }
}
