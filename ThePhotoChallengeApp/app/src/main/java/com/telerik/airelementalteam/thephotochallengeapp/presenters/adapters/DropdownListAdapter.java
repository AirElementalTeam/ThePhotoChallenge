package com.telerik.airelementalteam.thephotochallengeapp.presenters.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

public class DropdownListAdapter extends FirebaseListAdapter {

    public DropdownListAdapter(Activity activity, Class<?> modelClass, int i, Query query){
        super(activity, modelClass, i, query);
    }

    @Override
    protected void populateView(View view, Object o) {

    }

    @Override
    public long getItemId(int i) {
        return super.getItemId(i);
    }
}
