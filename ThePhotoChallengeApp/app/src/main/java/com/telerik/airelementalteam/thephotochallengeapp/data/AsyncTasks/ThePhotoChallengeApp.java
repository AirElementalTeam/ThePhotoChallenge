package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.firebase.client.Firebase;

public class ThePhotoChallengeApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}
