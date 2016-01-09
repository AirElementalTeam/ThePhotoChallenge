package com.telerik.airelementalteam.thephotochallengeapp.data;

import android.app.Application;

import com.firebase.client.Firebase;

public class ThePhotoChallangeApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
