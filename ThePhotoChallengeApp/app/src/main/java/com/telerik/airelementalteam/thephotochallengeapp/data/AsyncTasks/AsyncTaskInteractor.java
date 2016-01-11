package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

import java.lang.reflect.Field;
import java.util.Map;

public class AsyncTaskInteractor {

    public void asyncRegisterUser(final Firebase refDB, final Firebase refUsers, final IOnTaskFinishedListener listener, final String name, final String email, final String password){
        refDB.createUser(email, password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        User newUser = new User(stringObjectMap.get("uid").toString(), name, email);
                        Firebase refUser = refUsers.child(newUser.getUid());
                        refUser.setValue(newUser);
                        listener.onSuccess();
                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get("uid"));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        System.out.println("error code is ----> "+firebaseError.getCode());
                        System.out.println(firebaseError.getMessage());
                        System.out.println(firebaseError.getDetails());
                        System.out.println("EMAIL IS ------> "+email);
                        switch (firebaseError.getCode()){
                            case FirebaseError.INVALID_EMAIL:
                                System.out.println("INVALID_EMAIL");
                                break;
                            case FirebaseError.EMAIL_TAKEN:
                                System.out.println("EMAIL_TAKEN");
                                break;
                            case FirebaseError.AUTHENTICATION_PROVIDER_DISABLED:
                                System.out.println("AUTHENTICATION_PROVIDER_DISABLED");
                                break;
                            case FirebaseError.INVALID_PROVIDER:
                                System.out.println("INVALID_PROVIDER");
                                break;
                            case FirebaseError.DENIED_BY_USER:
                                System.out.println("DENIED_BY_USER");
                                break;

                        }
                        listener.onError();
                        //check if the error is that this email is already taken
                        System.out.println("Inside onError method when registering user to Firebase");

                    }
                });
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
