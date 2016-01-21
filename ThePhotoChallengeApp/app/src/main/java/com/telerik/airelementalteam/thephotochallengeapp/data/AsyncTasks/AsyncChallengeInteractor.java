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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
                Firebase refUserChallenges = firebase.refAuthUserChallenges();
                Firebase refNewChallengeByUser = refUserChallenges.child(newChallenge.getId());
                refNewChallengeByUser.setValue(newChallenge);

                Firebase refChallenges = firebase.getRefChallanges();
                Firebase refNewChallenge = refChallenges.child(newChallenge.getId());
                refNewChallenge.setValue(newChallenge);

                Map<String, Object> participantID = new HashMap<>();
                participantID.put(newChallenge.getCreatorID(), true);
                firebase.getRefToChallengeParticipants().child(newChallenge.getId()).updateChildren(participantID);

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

    public void savePhoto(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Photo photo) {
        firebase.getRefUsers().child(firebase.currentUserUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println("INSIDE INTERACTOR ____ challengeID ---- >" + photo.getChallengeId());
                photo.setUserName(user.getName());
                Firebase refAllPhotos = firebase.getRefAllPhotos();
                Firebase refUserPhotos = firebase.refUserPhotos();
                Firebase refChallengePhotos = new Firebase(String.format(Path.TO_CURRENT_CHALLENGE_PHOTOS, photo.getChallengeId(), photo.getUserID()));
                Random generator = new Random();
                photo.setId(photo.getChallengeId() + Constants.DASH + photo.getUserID() + Math.abs(generator.nextInt()));
                refAllPhotos.child(photo.getId()).setValue(photo);
                refUserPhotos.child(photo.getId()).setValue(photo);
                refChallengePhotos.child(photo.getId()).setValue(photo);
                updateChallengePhotosCount(firebase, listener, photo.getChallengeId());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void updateChallengePhotosCount(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String challengeId) {
        firebase.getRefChallanges().child(challengeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Challenge challenge = dataSnapshot.getValue(Challenge.class);
                int photosCount = challenge.getPhotosCount();
                photosCount += 1;
                challenge.setPhotosCount(photosCount);
                dataSnapshot.getRef().setValue(challenge);
                firebase.refUserChallenges(challenge.getCreatorID()).child(challengeId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Challenge challenge = dataSnapshot.getValue(Challenge.class);
                        int photosCount = challenge.getPhotosCount();
                        photosCount += 1;
                        challenge.setPhotosCount(photosCount);
                        dataSnapshot.getRef().setValue(challenge);
                        updateChallengeParticipants(firebase, listener, challengeId);
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

    private void updateChallengeParticipants(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String challengeID) {
        firebase.getRefToChallengeParticipants().child(challengeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(firebase.currentUserUID())) {
                    System.out.println("I AM HERE BUT SHOULD I BE HERE");
                    System.out.println(dataSnapshot.getValue());
                    listener.onSuccess();
                } else {
                    dataSnapshot.getRef().child(firebase.currentUserUID()).setValue(true);
                    updateParticipantsCount(firebase, listener, challengeID);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void updateParticipantsCount(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String challengeID) {
        firebase.getRefChallanges().child(challengeID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Challenge challenge = dataSnapshot.getValue(Challenge.class);
                int partCount = challenge.getParticipantsCount();
                partCount += 1;
                challenge.setParticipantsCount(partCount);
                dataSnapshot.getRef().setValue(challenge);
                firebase.refUserChallenges(challenge.getCreatorID()).child(challengeID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Challenge challenge = dataSnapshot.getValue(Challenge.class);
                        int partCount = challenge.getParticipantsCount();
                        partCount += 1;
                        challenge.setParticipantsCount(partCount);
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

    public void updateLike(final FirebaseAdapter firebase, IOnTaskFinishedListener listener, final String photoId) {
        firebase.getRefAllPhotos().child(photoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                int likes = photo.getLikes();
                likes++;
                photo.setLikes(likes);
                dataSnapshot.getRef().setValue(photo);
                String challengeID = photo.getChallengeId();
                firebase.getRefToCurrentChallengePhotos(challengeID).child(photoId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

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
}
