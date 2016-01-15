package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
import com.telerik.airelementalteam.thephotochallengeapp.models.User;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.MainPresenter;
import com.telerik.airelementalteam.thephotochallengeapp.presenters.user.UserPresenter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Common.Constants;
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
                        User newUser = new User(stringObjectMap.get(Constants.UID).toString(), name, email);
                        Firebase refUser = refUsers.child(newUser.getUid());
                        Firebase refMail = refUsersByEmail.child(converter.escapeEmail(newUser.getEmail()));
                        refMail.child(Constants.UID).setValue(newUser.getUid());
                        refUser.setValue(newUser);
                        listener.onSuccess();
                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get(Constants.UID));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        System.out.println("error code is ----> " + firebaseError.getCode());
                        System.out.println(firebaseError.getMessage());
                        System.out.println(firebaseError.getDetails());
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

    public void asyncSendAndReceiveFriendRequest(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Query fromUser, final Query toUser) {
        toUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String toUserUID = user.getUid();
                String fromUserUID = firebase.currentUserUID();
                System.out.println(toUserUID);
                asyncSendFriendRequest(firebase, listener, fromUser, toUser, fromUserUID, toUserUID);
                //HALLELUJAH!
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Inside onCancelled of child.addListenerForSingleValueEvent");
                listener.onError();
            }
        });
    }

    public void asyncSendFriendRequest(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Query fromUserRef, final Query toUserRef, final String fromUserUID, final String toUserUID) {
        firebase.openConnection();
        final Firebase usersRef = firebase.getRefUsers();
        fromUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                HashMap<String, Object> currentRequestsSend = currentUser.getFrinedRequestSend();
                if(currentRequestsSend == null) {
                    currentRequestsSend = new HashMap<>();
                }

                String fromUserName = currentUser.getName();
                currentRequestsSend.put(toUserUID, true);
                currentUser.setFrinedRequestSend(currentRequestsSend);
                Firebase user = usersRef.child(currentUser.getUid());
                user.child(Constants.FRIEND_REQUESTS_SEND).updateChildren(currentRequestsSend);
                asyncReceiveFriendRequest(usersRef, listener, toUserRef, fromUserUID, fromUserName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Inside onCancelled of currentUserRef.addListenerForSingleValueEvent");
                listener.onError();
            }
        });
    }

    public void asyncReceiveFriendRequest(final Firebase UsersRef, final IOnTaskFinishedListener listener, Query toUserRef, final String fromUserUID, final String fromUserName) {
        toUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User toUser = dataSnapshot.getValue(User.class);
                HashMap<String, Object> requestsReceived = toUser.getFriendRequestRecieved();
                if(requestsReceived == null) {
                    requestsReceived = new HashMap<>();
                }
                requestsReceived.put(fromUserUID, true);
                toUser.setFriendRequestRecieved(requestsReceived);
                Firebase user = UsersRef.child(toUser.getUid());
                user.child(Constants.FRIEND_REQUESTS_RECEIVED).updateChildren(requestsReceived);

                //UserPresenter presenter = (UserPresenter) listener;
                //presenter.setFriendRequestFromUserName(fromUserName);

                UserPresenter presenter = (UserPresenter) listener;
                presenter.setFriendRequestSend(true);
                listener.onSuccess();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
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

                String name = user.getName();
                String email = user.getEmail();
                MainPresenter presenter = (MainPresenter) listener;
                presenter.setCurrentUserName(name);
                presenter.setCurrentUserEmail(email);

                listener.onSuccess();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onError();
            }
        });
    }

    public void listenForFriendRequestsConfirm(FirebaseAdapter firebase, final IOnChildrenListener listener) {
        final Firebase usersRef = firebase.getRefUsers();
        String currentUserUID = firebase.currentUserUID();
        String path = usersRef.toString() + Constants.SLASH + currentUserUID + Constants.SLASH + Constants.FRIENDS;
        Firebase RefFriends = new Firebase(path);
        RefFriends.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("I heard there is a new friend ");
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void listenForFriendRequest(FirebaseAdapter firebase, final IOnChildrenListener listener) {
        final Firebase usersRef = firebase.getRefUsers();
        String currentUserUID = firebase.currentUserUID();
        String path = usersRef.toString() + Constants.SLASH + currentUserUID + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;
        System.out.println(path);
        Firebase receivedFriendRequestFromUser = new Firebase(path);
        receivedFriendRequestFromUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //find user
                usersRef.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        MainPresenter presenter = (MainPresenter) listener;
                        presenter.setFriendRequestFromUserName(user.getName());
                        presenter.setFriendRequestFromUserEmail(user.getEmail());
                        presenter.setFriendRequestFromUserUID(user.getUid());
                        listener.childAdded();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private Query getCurrentUser(FirebaseAdapter firebase){
        Firebase RefUsers = firebase.getRefUsers();
        return RefUsers.child(firebase.currentUserUID());
    }

    public void asyncMakeFriends(FirebaseAdapter firebase, IOnTaskFinishedListener listener, String userUID, String otherUID) {
        Firebase RefUsers = firebase.getRefUsers();
        String pathToOtherUserFriends = RefUsers.toString() + Constants.SLASH + otherUID + Constants.SLASH + Constants.FRIENDS;
        String pathToOtherUserSendRequests = RefUsers.toString() + Constants.SLASH + otherUID + Constants.SLASH + Constants.FRIEND_REQUESTS_SEND;
        String pathToAuthUserFriends = RefUsers.toString() + Constants.SLASH + userUID + Constants.SLASH + Constants.FRIENDS;
        String pathToAuthUserReceivedRequests = RefUsers.toString() + Constants.SLASH + userUID + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;

        Firebase RefAuthFriends = new Firebase(pathToAuthUserFriends);
        RefAuthFriends.child(otherUID).setValue(true);
        Firebase RefOtherFriends = new Firebase(pathToOtherUserFriends);
        RefOtherFriends.child(userUID).setValue(true);
        Firebase RefAuthUserReceivedRequests = new Firebase(pathToAuthUserReceivedRequests);
        RefAuthUserReceivedRequests.child(otherUID).setValue(null);
        Firebase RefOtherUserSendRequests = new Firebase(pathToOtherUserSendRequests);
        RefOtherUserSendRequests.child(userUID).setValue(null);

        UserPresenter presenter = (UserPresenter) listener;
        presenter.setFriends(true);
        presenter.setFriendRequestSend(false);
        listener.onSuccess();

    }
}
