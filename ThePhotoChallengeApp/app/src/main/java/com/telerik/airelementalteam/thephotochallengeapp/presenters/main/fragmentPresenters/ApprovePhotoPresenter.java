package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ApprovePhotoFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SinglePhotoFragment;

public class ApprovePhotoPresenter implements IOnTaskFinishedListener{


    private Activity activity;
    private FirebaseAdapter firebase;

    private ProgressDialog progressDialog;

    private Photo photo;

    public ApprovePhotoPresenter(Activity activity) {
        this.activity = activity;
        this.firebase = new FirebaseAdapter();
    }

    public void onApprovedPhoto(Photo photo) {
        progressDialog = ProgressDialog.show(activity, "Saving photo...", null);
        progressDialog.show();
        this.photo = photo;
        firebase.savePhoto(photo, this);

    }

    public void onDeclinedPhoto(Photo photo) {
        SingleChallengeFragment fragment = new SingleChallengeFragment();
        fragment.setChallengeID(photo.getChallengeId());
        FragmentTransaction transaction = this.activity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onSuccess() {
        System.out.println("Saved the photo");
        //firebase.getRefToChallengeParticipants(this.photo.getChallengeId()).
        SinglePhotoFragment fragment = new SinglePhotoFragment();
        fragment.setPhotoId(this.photo.getId());
        FragmentTransaction transaction = this.activity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        progressDialog.hide();
        transaction.commit();
    }

    @Override
    public void onError() {

    }
}
