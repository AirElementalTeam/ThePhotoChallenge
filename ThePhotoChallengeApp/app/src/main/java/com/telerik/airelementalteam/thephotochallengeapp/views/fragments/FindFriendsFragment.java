package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FindFriendsFragmentPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FriendsFragmentPresenter;

public class FindFriendsFragment extends Fragment {

    private FindFriendsFragmentPresenter presenter;

    public FindFriendsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FindFriendsFragmentPresenter(this.getActivity());
        View view =  inflater.inflate(R.layout.fragment_find_friends, container, false);
        ListView userList = (ListView) view.findViewById(android.R.id.list);
        presenter.populateFriendList(userList);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cleanup();
    }
}
