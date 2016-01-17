package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.fragmentPresenters.SingleChallengePresenter;
import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;

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
}
