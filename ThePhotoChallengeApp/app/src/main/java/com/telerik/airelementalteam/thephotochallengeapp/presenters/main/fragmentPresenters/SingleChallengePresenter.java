package com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.ui.FirebaseListAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.R;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.views.MainActivity;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.ApprovePhotoFragment;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class SingleChallengePresenter implements IOnTaskFinishedListener {

    private Activity activity;
    private SingleChallengeFragment fragment;
    private FirebaseAdapter firebase;
    private FirebaseListAdapter<Photo> gridAdapter;
    private File imageFile;
    private String convertedPhotoString;

    private String challengeID;
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
        this.challengeID = challengeID;
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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "snimchica.jpg");
        this.fragment.setNewImageFile(imageFile);
        Uri tempUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        this.fragment.startActivityForResult(cameraIntent, 0);
    }

    public void convertToBase64(File imageFile){
        String pathName = imageFile.getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bmp = BitmapFactory.decodeFile(pathName, options);
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        //bmp.recycle();
        final byte[] byteArray = bYtE.toByteArray();
        convertedPhotoString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Photo newPhoto = new Photo();
        newPhoto.setBase64(this.convertedPhotoString);
        newPhoto.setUserID(this.firebase.currentUserUID());
        newPhoto.setUserName(this.creatorName);
        newPhoto.setChallengeName(this.challengeTitle);
        newPhoto.setChallengeId(this.challengeID);
        newPhoto.setTheme(this.challengeTheme);
        newPhoto.setViews(0);
        newPhoto.setLikes(0);
        navigateToApproval(bmp, newPhoto);
    }

    public void navigateToApproval(Bitmap bmp, Photo photo) {
        ApprovePhotoFragment newFragment = new ApprovePhotoFragment();
        newFragment.setBmp(bmp);
        newFragment.setPhoto(photo);
        FragmentTransaction transaction = this.activity.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }
}
