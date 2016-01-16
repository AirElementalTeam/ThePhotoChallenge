package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.Firebase;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;

import java.util.HashMap;

import Common.Constants;

public class AsyncChallengesInteractor {
    public void saveChallenge(FirebaseAdapter firebase, IOnTaskFinishedListener listener, Challenge newChallenge) {
        Firebase refUserChallenges = firebase.getRefUserChallenges();
        HashMap<String, Object> challenge = new HashMap<>();
        String key = newChallenge.getTitle() + Constants.THEMES + newChallenge.getTheme() + Constants.UID + firebase.currentUserUID();
        newChallenge.setId(key);
        challenge.put(key, newChallenge);
        refUserChallenges.updateChildren(challenge);

    }
}
