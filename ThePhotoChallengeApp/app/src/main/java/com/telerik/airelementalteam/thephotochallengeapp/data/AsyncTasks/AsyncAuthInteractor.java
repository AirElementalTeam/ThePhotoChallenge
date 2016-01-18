package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.MainPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.user.UserPresenter;

import java.util.Map;

import Common.Constants;
import Common.Converter;

public class AsyncAuthInteractor {

    Converter converter = new Converter();

    //auth methods
    public void asyncRegisterUser(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String name, final String email, final String password){
        firebase.openConnection();
        Firebase refDB = firebase.getRefDB();
        final Firebase refUsersByEmail = firebase.getRefUsersByEmail();
        final Firebase refUsers = firebase.getRefUsers();
        refDB.createUser(email, password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        User newUser = new User(stringObjectMap.get(Constants.UID).toString(), name, email);
                        Firebase refUser = refUsers.child(newUser.getUid());
                        Firebase refUserByMail = refUsersByEmail.child(converter.escapeEmail(newUser.getEmail()));
                        refUser.setValue(newUser);
                        refUserByMail.setValue(newUser);
                        listener.onSuccess();
                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get(Constants.UID));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        //TODO: find a way to return proper response when you get some specific error code!
                        switch (firebaseError.getCode()) {
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

    public void asyncGetUserNameAndMail(FirebaseAdapter firebase, final IOnTaskFinishedListener listener) {
        Firebase RefUsers = firebase.getRefUsers();
        Query userRef = RefUsers.child(firebase.currentUserUID());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class); //boom

                if (user != null) {
                    String name = user.getName();
                    String email = user.getEmail();
                    MainPresenter presenter = (MainPresenter) listener;
                    presenter.setCurrentUserName(name);
                    presenter.setCurrentUserEmail(email);

                    listener.onSuccess();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onError();
            }
        });
    }

    public void getFriendshipState(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String uid) {
        final UserPresenter presenter = (UserPresenter) listener;
        firebase.refUserFriends().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uid)) {
                    System.out.println("dataSnapshot ----->" + dataSnapshot);
                    System.out.println("dataSnapshot.hasChild(firebase.currentUserUID() ----->" + dataSnapshot.hasChild(firebase.currentUserUID()));
                    //friends
                    presenter.setFriends(true);
                    presenter.setFriendRequestSend(false);
                    presenter.setFriendRequestReceived(false);
                    listener.onSuccess();
                } else {
                    firebase.getRefUsers().child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(Constants.FRIEND_REQUESTS_RECEIVED).hasChild(firebase.currentUserUID())){
                                System.out.println("dataSnapshot ----->" + dataSnapshot);
                                System.out.println("dataSnapshot.child(Constants.FRIEND_REQUESTS_RECEIVED).hasChild(firebase.currentUserUID()) ----->" + dataSnapshot.child(Constants.FRIEND_REQUESTS_RECEIVED).hasChild(firebase.currentUserUID()));
                                //auth user send this user a friend request
                                presenter.setFriendRequestSend(true);
                                presenter.setFriends(false);
                                presenter.setFriendRequestReceived(false);
                                listener.onSuccess();
                            } else if(dataSnapshot.child(Constants.FRIEND_REQUESTS_SEND).hasChild(firebase.currentUserUID())){
                                System.out.println("dataSnapshot.child(Constants.FRIEND_REQUESTS_SEND).hasChild(firebase.currentUserUID()) ----->" + dataSnapshot.child(Constants.FRIEND_REQUESTS_SEND).hasChild(firebase.currentUserUID()));
                                //this user send auth user friend request
                                presenter.setFriendRequestReceived(true);
                                presenter.setFriendRequestSend(false);
                                presenter.setFriends(false);
                                listener.onSuccess();
                            } else {
                                System.out.println("else ----->" + dataSnapshot);
                                //no friends
                                presenter.setFriends(false);
                                presenter.setFriendRequestReceived(false);
                                presenter.setFriendRequestSend(false);
                                listener.onSuccess();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
