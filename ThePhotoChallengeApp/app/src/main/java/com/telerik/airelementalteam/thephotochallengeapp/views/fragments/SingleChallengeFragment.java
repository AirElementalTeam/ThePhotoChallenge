package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.SingleChallengePresenter;

import org.w3c.dom.Text;

public class SingleChallengeFragment extends android.app.Fragment {

    private SingleChallengePresenter presenter;
    private String challengeID;

    private TextView photosCountText;
    private TextView participantsCountText;
    private TextView creatorNameText;
    private TextView challengeTitleText;
    private TextView challengeThemeText;
    private TextView dueDateText;
    private GridView photosGrid;

    public SingleChallengeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new SingleChallengePresenter(this.getActivity(), this);
        View view = inflater.inflate(R.layout.fragment_single_challenge, container, false);
        photosCountText = (TextView) view.findViewById(R.id.photos_count_text);
        participantsCountText = (TextView) view.findViewById(R.id.participants_count_text);
        creatorNameText = (TextView) view.findViewById(R.id.creator_name_text);
        challengeTitleText = (TextView) view.findViewById(R.id.challenge_title);
        challengeThemeText = (TextView) view.findViewById(R.id.theme_name_text);
        dueDateText = (TextView) view.findViewById(R.id.due_date_text);

        presenter.getChallengeInfo(this.getChallengeID());


        return view;
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
}
