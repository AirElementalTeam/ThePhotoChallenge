package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class AsyncRegisterInteractor {

    public void asyncRegisterUser(Firebase refDB, final IOnRegisterFinishedListener listener, String email, String password){
        System.out.println("Inside asyncRegisterUser in AsyncRegisterInteractor");
        refDB.createUser(email, password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        listener.onSuccess();
                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get("uid"));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        listener.onError();
                        System.out.println("Inside onError method when registering user to Firebase");

                    }
                });
        System.out.println("Before exiting asyncRegisterUser in AsyncRegisterInteractor");
    }
}
