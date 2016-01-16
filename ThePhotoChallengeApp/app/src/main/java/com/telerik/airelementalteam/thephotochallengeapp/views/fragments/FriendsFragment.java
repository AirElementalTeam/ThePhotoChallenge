package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.FriendsFragmentPresenter;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsFragment extends ListFragment {

    private FriendsFragmentPresenter presenter;

    public FriendsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new FriendsFragmentPresenter(this.getActivity());
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);
        ListView friendsList = (ListView) view.findViewById(android.R.id.list);
        TextView noFriends = (TextView) view.findViewById(R.id.no_friends_id);
        CircleImageView findFriendsButton = (CircleImageView) view.findViewById(R.id.find_friends_button);

        findFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindFriendsFragment fragment = new FindFriendsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.commit();
            }
        });

        presenter.populateFriendList(friendsList, noFriends);
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: floating button shiuld be extracted in separate fragment so items can be clickable
                //If a view contains either focusable or clickable item the OnItemCLickListener won't be called.
                //System.out.println("Clicked on friend item");
                TextView nameText = (TextView) view.findViewById(R.id.list_user_name);
                TextView emailText = (TextView) view.findViewById(R.id.list_user_email);

                String name = nameText.getText().toString();
                String email = emailText.getText().toString();

                UserFragment fragment = new UserFragment();
                fragment.setName(name);
                fragment.setEmail(email);
                fragment.setNotFriend(false);
                fragment.setFriendRequestRecieved(false);
                fragment.setFriendRequestSend(false);
                fragment.setIsFriend(true);
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
