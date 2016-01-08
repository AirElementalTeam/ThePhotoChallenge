package com.telerik.airelementalteam.thephotochallengeapp.presenters;

import android.app.Activity;

public interface IPresenter {
    public void bind(Object object, Activity activity);
    public void go(Activity activity);
}
