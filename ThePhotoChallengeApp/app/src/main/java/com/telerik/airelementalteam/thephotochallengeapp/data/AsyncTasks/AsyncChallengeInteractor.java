package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.Photo;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SingleChallengePresenter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SinglePhotoPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;

import java.util.Random;

import Common.Constants;
import Common.Path;

public class AsyncChallengeInteractor {

    //challenges methods
    public void saveNewChallenge(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Challenge newChallenge) {

        firebase.getRefUsers().child(firebase.currentUserUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User creator = dataSnapshot.getValue(User.class);
                newChallenge.setCreatorName(creator.getName());
                newChallenge.setCreatorID(firebase.currentUserUID());
                Firebase refUserChallenges = firebase.refUserChallenges();
                Firebase refNewChallengeByUser = refUserChallenges.child(newChallenge.getId());
                refNewChallengeByUser.setValue(newChallenge);

                Firebase refChallenges = firebase.getRefChallanges();
                Firebase refNewChallenge = refChallenges.child(newChallenge.getId());
                refNewChallenge.setValue(newChallenge);

                SingleChallengeFragment fragment = new SingleChallengeFragment();

                listener.onSuccess();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public void getChallengeInfo(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String challengeID) {
        Firebase refChallenges = firebase.getRefChallanges();
        Firebase refChallenge = refChallenges.child(challengeID);
        refChallenge.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Challenge challenge = dataSnapshot.getValue(Challenge.class);
                SingleChallengePresenter presenter = (SingleChallengePresenter) listener;
                presenter.setChallengeTitle(challenge.getTitle());
                presenter.setChallengeTheme(challenge.getTheme());
                presenter.setCreatorName(challenge.getCreatorName());
                presenter.setDueDate(challenge.getDueDate());
                presenter.setParticipantsCount(Integer.toString(challenge.getParticipantsCount()));
                presenter.setPhotosCount(Integer.toString(challenge.getPhotosCount()));
                listener.onSuccess();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public void savePhoto(FirebaseAdapter firebase, IOnTaskFinishedListener listener, Photo photo) {
        Firebase refAllPhotos = firebase.getRefAllPhotos();
        Firebase refUserPhotos = firebase.refUserPhotos();
        Firebase refChallengePhotos = new Firebase(String.format(Path.TO_CURRENT_CHALLENGE_PHOTOS, photo.getChallengeId(), photo.getUserID()));
        Firebase refChallenges = firebase.getRefChallanges();
        Random generator = new Random();
        photo.setId(photo.getChallengeId() + Constants.DASH + photo.getUserID() + Math.abs(generator.nextInt()));
        refAllPhotos.child(photo.getId()).setValue(photo);
        refUserPhotos.child(photo.getId()).setValue(photo);
        refChallengePhotos.child(photo.getId()).setValue(photo);
        //TODO: increase photo count in challenge
        updateChallengePhotosCount(firebase, listener, photo.getChallengeId());
    }

    public void updateChallengePhotosCount(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String challengeId) {
        firebase.refUserChallenges().child(challengeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                Challenge challenge = dataSnapshot1.getValue(Challenge.class);
                int photosCount = challenge.getPhotosCount();
                photosCount += 1;
                challenge.setPhotosCount(photosCount);
                dataSnapshot1.getRef().setValue(challenge);
                firebase.getRefChallanges().child(challengeId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Challenge challenge = dataSnapshot.getValue(Challenge.class);
                        int photosCount = challenge.getPhotosCount();
                        photosCount += 1;
                        challenge.setPhotosCount(photosCount);
                        dataSnapshot.getRef().setValue(challenge);
                        listener.onSuccess();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void getPhotoInfo(FirebaseAdapter firebaseAdapter, final IOnTaskFinishedListener listener, final String photoId) {
        firebaseAdapter.getRefAllPhotos().child(photoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                SinglePhotoPresenter presenter = (SinglePhotoPresenter) listener;
                presenter.setBase64(photo.getBase64());
                presenter.setChallengeName(photo.getChallengeName());
                presenter.setChallengeId(photo.getChallengeId());
                presenter.setTheme(photo.getTheme());
                presenter.setLikes(Integer.toString(photo.getLikes()));
                presenter.setViews(Integer.toString(photo.getViews()));
                presenter.setLocation(photo.getLocation());
                presenter.setUserName(photo.getUserName());
                presenter.setUserID(photo.getUserID());
                listener.onSuccess();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
