package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ApprovePhotoFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SinglePhotoFragment;

public class ApprovePhotoPresenter {

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private Activity activity;
    private ApprovePhotoFragment fragment;

    private GoogleApiClient googleApiClient;
    private Location gastLocation;
    private LocationRequest locationRequest;

    public ApprovePhotoPresenter(Activity activity, ApprovePhotoFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;

    }

    public void takeLocation(Photo photo) {
    }

    public void onApprovedPhoto(Photo photo) {
        //save photo to db
        //on success navigate to single photo view
    }

    public void onDeclinedPhoto(Photo photo) {
        SingleChallengeFragment fragment = new SingleChallengeFragment();
        fragment.setChallengeID(photo.getChallengeId());
        FragmentTransaction transaction = this.activity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
