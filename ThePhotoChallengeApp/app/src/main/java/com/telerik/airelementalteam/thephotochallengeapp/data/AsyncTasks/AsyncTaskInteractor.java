package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;

import java.lang.reflect.Field;
import java.util.Map;

import Common.Converter;

public class AsyncTaskInteractor {

    Converter converter = new Converter();

    public void asyncRegisterUser(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String name, final String email, final String password){
        firebase.openConnection();
        Firebase refDB = firebase.getRefDB();
        final Firebase refUsersByEmail = firebase.getRefUsersByEmail();
        final Firebase refUsers = firebase.getRefUsers();
        refDB.createUser(email, password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        User newUser = new User(stringObjectMap.get("uid").toString(), name, email);
                        Firebase refUser = refUsers.child(newUser.getUid());
                        Firebase refMail = refUsersByEmail.child(converter.escapeEmail(newUser.getEmail()));
                        refMail.setValue(newUser.getUid());
                        refUser.setValue(newUser);
                        listener.onSuccess();
                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get("uid"));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        System.out.println("error code is ----> "+firebaseError.getCode());
                        System.out.println(firebaseError.getMessage());
                        System.out.println(firebaseError.getDetails());
                        //TODO: find a way to return proper response when you get some specific error code!
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
                    }
                });
    }

    public void asyncLoginUser(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, String email, String password){
        Firebase refDB = firebase.getRefDB();
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
