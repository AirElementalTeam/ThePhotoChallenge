package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SinglePhotoPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.LoginActivity;
import com.telerik.airelementalteam.thephotochallengeapp.views.MainActivity;

import org.w3c.dom.Text;

public class SinglePhotoFragment extends android.app.Fragment {

    private final int LIKE_TIME_OUT = 2000;

    SinglePhotoPresenter presenter;

    private ImageView imageView;
    private ImageView likesIcon;
    private ImageView bigHeart;
    private TextView challengeTitle;
    private TextView challengeTheme;
    private TextView userName;
    private TextView viewsCount;
    private TextView likesCount;
    private TextView location;

    private String photoId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_photo, container, false);
        imageView = (ImageView) view.findViewById(R.id.photo_view);
        likesIcon = (ImageView) view.findViewById(R.id.likes_icon);
        bigHeart = (ImageView) view.findViewById(R.id.liked_view);
        bigHeart.setVisibility(View.INVISIBLE);
        challengeTitle = (TextView) view.findViewById(R.id.challenge_title);
        challengeTheme = (TextView) view.findViewById(R.id.theme_name_text);
        userName = (TextView) view.findViewById(R.id.photo_creator_text);
        viewsCount = (TextView) view.findViewById(R.id.views_count);
        likesCount = (TextView) view.findViewById(R.id.likes_count);
        location = (TextView) view.findViewById(R.id.location_text);
        this.presenter = new SinglePhotoPresenter(getActivity(), this);
        presenter.getPhotoInfo(getPhotoId());

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 1/18/16
                        //bigHeart.setVisibility(View.VISIBLE);
                    }
                }, LIKE_TIME_OUT);

                bigHeart.setVisibility(View.INVISIBLE);
                likesIcon.setBackground(getResources().getDrawable(R.drawable.ic_action_heart_yellow));
                return false;
            }
        });
        return view;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public TextView getChallengeTitle() {
        return challengeTitle;
    }

    public void setChallengeTitle(TextView challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public TextView getChallengeTheme() {
        return challengeTheme;
    }

    public void setChallengeTheme(TextView challengeTheme) {
        this.challengeTheme = challengeTheme;
    }

    public TextView getUserName() {
        return userName;
    }

    public void setUserName(TextView userName) {
        this.userName = userName;
    }

    public TextView getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(TextView viewsCount) {
        this.viewsCount = viewsCount;
    }

    public TextView getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(TextView likesCount) {
        this.likesCount = likesCount;
    }

    public TextView getLocation() {
        return location;
    }

    public void setLocation(TextView location) {
        this.location = location;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
