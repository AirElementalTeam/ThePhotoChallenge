package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.Fragment;

import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ApprovePhotoFragment;

public class ApprovePhotoPresenter {

    private Activity activity;
    private ApprovePhotoFragment fragment;

    public ApprovePhotoPresenter(Activity activity, ApprovePhotoFragment fragment) {
        this.activity = activity;
    }

    public void takeLocation(Photo photo) {
    }

    public void onApprovedPhoto(Photo photo) {
    }

    public void onDeclinedPhoto(Photo photo) {

    }
}
