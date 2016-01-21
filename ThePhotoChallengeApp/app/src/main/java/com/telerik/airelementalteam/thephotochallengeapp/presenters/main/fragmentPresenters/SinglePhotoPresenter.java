package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SinglePhotoFragment;

public class SinglePhotoPresenter implements IOnTaskFinishedListener {

    private final Activity activity;
    private SinglePhotoFragment fragment;
    private FirebaseAdapter firebase;

    private String base64;
    private String challengeName;
    private String challengeId;
    private String likes;
    private String views;
    private String location;
    private String theme;
    private String userName;
    private String userID;

    public SinglePhotoPresenter(Activity activity, SinglePhotoFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.firebase = new FirebaseAdapter();
    }

    public void getPhotoInfo(String photoId) {

        firebase.getPhotoInfo(this, photoId);
    }


    public void newLike(String photoId) {
        firebase.updateLike(this, photoId);
    }


    @Override
    public void onSuccess() {
        byte[] photoBytes = Base64.decode(this.base64, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
        this.fragment.getImageView().setImageBitmap(bmp);
        this.fragment.getChallengeTitle().setText(this.challengeName);
        this.fragment.getChallengeTheme().setText(this.theme);
        this.fragment.getUserName().setText(this.userName);
        this.fragment.getViewsCount().setText(this.views);
        this.fragment.getLikesCount().setText("1");
        this.fragment.getLocation().setText(this.location);
    }

    @Override
    public void onError() {
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
