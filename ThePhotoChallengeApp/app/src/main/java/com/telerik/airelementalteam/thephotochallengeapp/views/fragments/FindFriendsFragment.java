package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FindFriendsFragmentPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FriendsFragmentPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.LoginActivity;
import com.telerik.airelementalteam.thephotochallengeapp.views.MainActivity;

public class FindFriendsFragment extends Fragment {

    private FindFriendsFragmentPresenter presenter;
    private ProgressDialog bar;

    public FindFriendsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FindFriendsFragmentPresenter(this.getActivity());

        View view =  inflater.inflate(R.layout.fragment_find_friends, container, false);
        ListView userList = (ListView) view.findViewById(android.R.id.list);

        presenter.populateFriendList(userList);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameText = (TextView) view.findViewById(R.id.list_user_name);
                TextView emailText = (TextView) view.findViewById(R.id.list_user_email);

                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                System.out.println(name);
                System.out.println(email);
                System.out.println("----------------------");
                UserFragment fragment = new UserFragment();
                fragment.setName(name);
                fragment.setEmail(email);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cleanup();
    }
}
