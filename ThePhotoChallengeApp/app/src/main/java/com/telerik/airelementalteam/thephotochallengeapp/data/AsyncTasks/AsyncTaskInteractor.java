package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class AsyncTaskInteractor {

    public void asyncRegisterUser(Firebase refDB, final IOnTaskFinishedListener listener, String email, String password){
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
                        //check if the error is that this email is already taken
                        System.out.println("Inside onError method when registering user to Firebase");

                    }
                });
        System.out.println("Before exiting asyncRegisterUser in AsyncRegisterInteractor");
    }

    public void asyncLoginUser(Firebase refDB, final IOnTaskFinishedListener listener, String email, String password){
        refDB.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.onError();
            }
        });
    }
}
