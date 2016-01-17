package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;

public class SingleChallengePresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private SingleChallengeFragment fragment;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<Photo> gridAdapter;

    private String challengeTitle;
    private String challengeTheme;
    private String dueDate;
    private String creatorName;
    private String photosCount;
    private String participantsCount;

    public SingleChallengePresenter(Activity activity, SingleChallengeFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.firebase = new FirebaseAdapter();
    }

    public void populatePhotosGrid(GridView gridView){
        this.gridAdapter = new FirebaseListAdapter<Photo>(this.activity, Photo.class, R.layout.photo_grid_item, firebase.getRefPhotos()) {
            @Override
            protected void populateView(View view, Photo photo) {
                //TODO;(FIX THE REF)
            }
        };

    }

    public void getChallengeInfo(String challengeID) {
        firebase.getChallengeInfo(challengeID, this);

    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public String getChallengeTheme() {
        return challengeTheme;
    }

    public void setChallengeTheme(String challengeTheme) {
        this.challengeTheme = challengeTheme;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(String photosCount) {
        this.photosCount = photosCount;
    }

    public String getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(String participantsCount) {
        this.participantsCount = participantsCount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public void onSuccess() {
        this.fragment.getChallengeTitleText().setText(this.challengeTitle);
        this.fragment.getChallengeThemeText().setText(this.challengeTheme);
        this.fragment.getDueDateText().setText(this.dueDate);
        this.fragment.getCreatorNameText().setText(this.creatorName);
        this.fragment.getParticipantsCountText().setText(this.participantsCount);
        this.fragment.getPhotosCountText().setText(this.photosCount);
        if(this.photosCount.equals("0")) {
            this.fragment.getNoPhotosText().setText(R.string.challengee_no_photos_text);
        } else {
            populatePhotosGrid(this.fragment.getPhotosGrid());
        }
    }

    @Override
    public void onError() {

    }


    public void startCamera() {
    }
}
