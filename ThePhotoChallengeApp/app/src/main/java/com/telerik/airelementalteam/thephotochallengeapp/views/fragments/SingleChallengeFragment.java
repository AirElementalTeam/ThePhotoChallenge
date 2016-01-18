package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SingleChallengePresenter;

import java.io.File;

public class SingleChallengeFragment extends android.app.Fragment {

    private SingleChallengePresenter presenter;
    private String challengeID;

    private TextView photosCountText;
    private TextView participantsCountText;
    private TextView creatorNameText;
    private TextView challengeTitleText;
    private TextView challengeThemeText;
    private TextView dueDateText;
    private TextView noPhotosText;
    private GridView photosGrid;
    private AppCompatButton takePhotoButton;

    private File newImageFile;

    public SingleChallengeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);
        presenter = new SingleChallengePresenter(this.getActivity(), this);
        View view = inflater.inflate(R.layout.fragment_single_challenge, container, false);
        photosCountText = (TextView) view.findViewById(R.id.photos_count_text);
        participantsCountText = (TextView) view.findViewById(R.id.participants_count_text);
        creatorNameText = (TextView) view.findViewById(R.id.creator_name_text);
        challengeTitleText = (TextView) view.findViewById(R.id.challenge_title);
        challengeThemeText = (TextView) view.findViewById(R.id.theme_name_text);
        dueDateText = (TextView) view.findViewById(R.id.due_date_text);
        noPhotosText = (TextView) view.findViewById(R.id.no_photos_text);
        takePhotoButton = (AppCompatButton) view.findViewById(R.id.take_photo_button);
        photosGrid = (GridView) view.findViewById(android.R.id.list);

        presenter.getChallengeInfo(this.getChallengeID());
        presenter.populatePhotosGrid(photosGrid);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startCamera();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.convertToBase64(this.newImageFile);
    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }

    public TextView getPhotosCountText() {
        return photosCountText;
    }

    public void setPhotosCountText(TextView photosCountText) {
        this.photosCountText = photosCountText;
    }

    public TextView getParticipantsCountText() {
        return participantsCountText;
    }

    public void setParticipantsCountText(TextView participantsCountText) {
        this.participantsCountText = participantsCountText;
    }

    public TextView getCreatorNameText() {
        return creatorNameText;
    }

    public void setCreatorNameText(TextView creatorNameText) {
        this.creatorNameText = creatorNameText;
    }

    public TextView getChallengeTitleText() {
        return challengeTitleText;
    }

    public void setChallengeTitleText(TextView challengeTitleText) {
        this.challengeTitleText = challengeTitleText;
    }

    public TextView getChallengeThemeText() {
        return challengeThemeText;
    }

    public void setChallengeThemeText(TextView challengeThemeText) {
        this.challengeThemeText = challengeThemeText;
    }

    public GridView getPhotosGrid() {
        return photosGrid;
    }

    public void setPhotosGrid(GridView photosGrid) {
        this.photosGrid = photosGrid;
    }

    public TextView getDueDateText() {
        return dueDateText;
    }

    public void setDueDateText(TextView dueDateText) {
        this.dueDateText = dueDateText;
    }

    public TextView getNoPhotosText() {
        return noPhotosText;
    }

    public void setNoPhotosText(TextView noPhotosText) {
        this.noPhotosText = noPhotosText;
    }

    public void setNewImageFile(File newImageFile) {
        this.newImageFile = newImageFile;
    }
}
