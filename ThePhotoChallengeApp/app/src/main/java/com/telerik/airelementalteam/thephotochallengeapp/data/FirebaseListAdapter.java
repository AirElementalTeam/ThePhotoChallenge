package com.telerik.airelementalteam.thephotochallengeapp.data;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class FirebaseListAdapter<T> extends BaseAdapter {

    private Query refQuery;
    private Class<T> modelClass;
    private int layout;
    private LayoutInflater inflater;
    private List<T> models;
    private List<String> keys;
    private ChildEventListener listener;

    public FirebaseListAdapter(){}

    public FirebaseListAdapter(Query refQuery, final Class<T> modelClass, int layout, Activity activity) {
        this.refQuery = refQuery;
        this.modelClass = modelClass;
        this.layout = layout;
        this.inflater = activity.getLayoutInflater();
        this.models = new ArrayList<T>();
        this.keys = new ArrayList<String>();
        this.listener = this.refQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                T model = dataSnapshot.getValue(FirebaseListAdapter.this.modelClass);
                String key = dataSnapshot.getKey();

                // Insert into the correct location, based on previousChildName
                if(previousChildName == null) {
                    models.add(0, model);
                    keys.add(0, key);
                } else {
                    int previousIndex = keys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if(nextIndex == models.size()) {
                        models.add(model);
                        keys.add(key);
                    } else {
                        models.add(nextIndex, model);
                        keys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(FirebaseListAdapter.this.modelClass);
                int index = keys.indexOf(key);

                models.set(index, newModel);

                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = keys.indexOf(key);
                keys.remove(index);
                models.remove(index);

                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                T newModel = dataSnapshot.getValue(FirebaseListAdapter.this.modelClass);
                int index = keys.indexOf(key);
                models.remove(index);
                keys.remove(index);

                if(previousChildName == null) {
                    models.add(0, newModel);
                    keys.add(0, key);
                } else {
                    int previousIndex = keys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if(nextIndex == models.size()) {
                        models.add(newModel);
                        keys.add(key);
                    } else {
                        models.add(nextIndex, newModel);
                        keys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                 Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }
        });
    }

    public void cleanup() {
        refQuery.removeEventListener(listener);
        models.clear();
        keys.clear();
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = this.inflater.inflate(this.layout, parent, false);
        }

        T model = models.get(position);
        populateView(convertView, model);
        return convertView;
    }

    protected abstract void populateView(View convertView, T model);
}
