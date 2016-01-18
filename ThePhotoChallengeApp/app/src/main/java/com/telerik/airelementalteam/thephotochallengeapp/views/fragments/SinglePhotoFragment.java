package com.telerik.airelementalteam.thephotochallengeapp.views.fragments;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SinglePhotoPresenter;

public class SinglePhotoFragment extends android.app.Fragment {


    private final int LIKE_TIME_OUT = 2000;

    SinglePhotoPresenter presenter;
    GestureDetector gestureDetector;;
    boolean tapped;

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
        gestureDetector = new GestureDetector(getActivity(), new GestureListener());
        View view = inflater.inflate(R.layout.fragment_single_photo, container, false);
        imageView = (ImageView) view.findViewById(R.id.photo_container);
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

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("   on touch");
                return gestureDetector.onTouchEvent(event);
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

    public ImageView getLikesIcon() {
        return likesIcon;
    }

    public void setLikesIcon(ImageView likesIcon) {
        this.likesIcon = likesIcon;
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //System.out.print("tapped");
            likesIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_heart_red));

            tapped = !tapped;
            if (tapped) {
                System.out.print("tapped");



            } else {
                System.out.print(" not tapped");



            }
            return true;
        }
    }
}
