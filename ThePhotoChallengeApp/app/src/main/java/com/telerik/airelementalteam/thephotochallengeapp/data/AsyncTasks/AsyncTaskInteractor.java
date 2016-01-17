//package com.telerik.airelementalteam.thephotochallengeapp.data.AsyncTasks;
//
//import com.firebase.client.AuthData;
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.Query;
//import com.firebase.client.ValueEventListener;
//import com.telerik.airelementalteam.thephotochallengeapp.data.FirebaseAdapter;
//import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestConfirmedListener;
//import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnFriendRequestListener;
//import com.telerik.airelementalteam.thephotochallengeapp.interfaces.IOnTaskFinishedListener;
//import com.telerik.airelementalteam.thephotochallengeapp.models.Challenge;
//import com.telerik.airelementalteam.thephotochallengeapp.models.User;
//import com.telerik.airelementalteam.thephotochallengeapp.presenters.main.MainPresenter;
//import com.telerik.airelementalteam.thephotochallengeapp.presenters.user.UserPresenter;
//import com.telerik.airelementalteam.thephotochallengeapp.views.fragments.SingleChallengeFragment;
//
//import java.net.UnknownServiceException;
//import java.util.HashMap;
//import java.util.Map;
//
//import Common.Constants;
//import Common.Converter;
//
//public class AsyncTaskInteractor {
//
//    Converter converter = new Converter();
//
//    //auth methods
//    public void asyncRegisterUser(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String name, final String email, final String password){
//        firebase.openConnection();
//        Firebase refDB = firebase.getRefDB();
//        final Firebase refUsersByEmail = firebase.getRefUsersByEmail();
//        final Firebase refUsers = firebase.getRefUsers();
//        refDB.createUser(email, password,
//                new Firebase.ValueResultHandler<Map<String, Object>>() {
//
//                    @Override
//                    public void onSuccess(Map<String, Object> stringObjectMap) {
//                        User newUser = new User(stringObjectMap.get(Constants.UID).toString(), name, email);
//                        Firebase refUser = refUsers.child(newUser.getUid());
//                        Firebase refUserByMail = refUsersByEmail.child(converter.escapeEmail(newUser.getEmail()));
//                        refUser.setValue(newUser);
//                        refUserByMail.setValue(newUser);
//                        listener.onSuccess();
//                        System.out.println("Successfully created user account with uid: " + stringObjectMap.get(Constants.UID));
//                    }
//
//                    @Override
//                    public void onError(FirebaseError firebaseError) {
//                        //System.out.println("error code is ----> " + firebaseError.getCode());
//                        //System.out.println(firebaseError.getMessage());
//                        //System.out.println(firebaseError.getDetails());
//                        //TODO: find a way to return proper response when you get some specific error code!
//                        switch (firebaseError.getCode()) {
//                            case FirebaseError.INVALID_EMAIL:
//                                System.out.println("INVALID_EMAIL");
//                                break;
//                            case FirebaseError.EMAIL_TAKEN:
//                                System.out.println("EMAIL_TAKEN");
//                                break;
//                            case FirebaseError.AUTHENTICATION_PROVIDER_DISABLED:
//                                System.out.println("AUTHENTICATION_PROVIDER_DISABLED");
//                                break;
//                            case FirebaseError.INVALID_PROVIDER:
//                                System.out.println("INVALID_PROVIDER");
//                                break;
//                            case FirebaseError.DENIED_BY_USER:
//                                System.out.println("DENIED_BY_USER");
//                                break;
//
//                        }
//                        listener.onError();
//                    }
//                });
//    }
//
//    public void asyncLoginUser(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, String email, String password){
//        Firebase refDB = firebase.getRefDB();
//        refDB.authWithPassword(email, password, new Firebase.AuthResultHandler() {
//            @Override
//            public void onAuthenticated(AuthData authData) {
//                listener.onSuccess();
//            }
//
//            @Override
//            public void onAuthenticationError(FirebaseError firebaseError) {
//                listener.onError();
//            }
//        });
//    }
//
//    public void asyncGetUserNameAndMail(FirebaseAdapter firebase, final IOnTaskFinishedListener listener) {
//        Firebase RefUsers = firebase.getRefUsers();
//        Query userRef = RefUsers.child(firebase.currentUserUID());
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class); //boom
//
//                if (user != null) {
//                    String name = user.getName();
//                    String email = user.getEmail();
//                    MainPresenter presenter = (MainPresenter) listener;
//                    presenter.setCurrentUserName(name);
//                    presenter.setCurrentUserEmail(email);
//
//                    listener.onSuccess();
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                listener.onError();
//            }
//        });
//    }
//
//
//    //friendship handling
//    public void asyncSendAndReceiveFriendRequest(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Query UserAQuery, final Query UserBQuery) {
//        System.out.println("Inside asyncSendAndReceiveFriendRequest");
//        UserBQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User userBByMail = dataSnapshot.getValue(User.class);
//                asyncSendFriendRequest(firebase, listener, UserAQuery, UserBQuery, userBByMail);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                listener.onError();
//            }
//        });
//    }
//
//    public void asyncSendFriendRequest(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Query UserAQuery, final Query UserBQuery, final User userB) {
//        System.out.println("Inside asyncSendFriendRequest");
//        UserAQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User userA = dataSnapshot.getValue(User.class);
//                HashMap<String, User> requestSend_A = userA.getFrinedRequestSend();
//                if(requestSend_A == null) {
//                    requestSend_A = new HashMap<>();
//                }
//                requestSend_A.put(userB.getUid(), userB);
//                Firebase refUserARequestsSend = firebase.getRefUsers().child(userA.getUid()).child(Constants.FRIEND_REQUESTS_SEND);
//                refUserARequestsSend.child(userB.getUid()).setValue(userB);
//                asyncReceiveFriendRequest(firebase.getRefUsers(), listener, userA, UserBQuery);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//    public void asyncReceiveFriendRequest(final Firebase RefUsers, final IOnTaskFinishedListener listener, final User userA, Query UserBQuery) {
//        System.out.println("Inside asyncReceiveFriendRequest");
//        UserBQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User userB = dataSnapshot.getValue(User.class);
//                HashMap<String, User> requestReceived_B = userB.getFriendRequestRecieved();
//                if (requestReceived_B == null) {
//                    requestReceived_B = new HashMap<>();
//                }
//
//                requestReceived_B.put(userA.getUid(), userA);
//                Firebase refUserBRequestReceived = RefUsers.child(userB.getUid()).child(Constants.FRIEND_REQUESTS_RECEIVED);
//                refUserBRequestReceived.child(userA.getUid()).setValue(userA);
//                UserPresenter presenter = (UserPresenter) listener;
//                presenter.setFriendRequestSend(true);
//                listener.onSuccess();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//    public void asyncMakeFriends(FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final String userUID, final String otherUID) {
//
//        Firebase ref = firebase.getRefDB();
//        final Firebase RefUsers = firebase.getRefUsers();
//
//        final String pathToOtherUserFriends = ref.toString() + Constants.SLASH + Constants.FRIENDS + Constants.SLASH + otherUID + "-" + Constants.FRIENDS;
//        final String pathToOtherUserSendRequests = RefUsers.toString() + Constants.SLASH + otherUID + Constants.SLASH + Constants.FRIEND_REQUESTS_SEND;
//        final String pathToAuthUserFriends = ref.toString() + Constants.SLASH + Constants.FRIENDS + Constants.SLASH + userUID + "-" + Constants.FRIENDS;
//        final String pathToAuthUserReceivedRequests = RefUsers.toString() + Constants.SLASH + userUID + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;
//
//        RefUsers.child(userUID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                final User user = dataSnapshot.getValue(User.class);
//                RefUsers.child(otherUID).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        User otherUser = dataSnapshot.getValue(User.class);
//
//                        Firebase RefAuthFriends = new Firebase(pathToAuthUserFriends);
//                        RefAuthFriends.child(otherUID).setValue(otherUser);
//                        Firebase RefOtherFriends = new Firebase(pathToOtherUserFriends);
//                        RefOtherFriends.child(userUID).setValue(user);
//                        Firebase RefAuthUserReceivedRequests = new Firebase(pathToAuthUserReceivedRequests);
//                        RefAuthUserReceivedRequests.child(otherUID).setValue(null);
//                        Firebase RefOtherUserSendRequests = new Firebase(pathToOtherUserSendRequests);
//                        RefOtherUserSendRequests.child(userUID).setValue(null);
//                        UserPresenter presenter = (UserPresenter) listener;
//                        presenter.setFriends(true);
//                        presenter.setFriendRequestSend(false);
//                        listener.onSuccess();
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//    //listeners
//    public void listenForFriendRequestsConfirm(final FirebaseAdapter firebase, final IOnFriendRequestConfirmedListener listener) {
//        final Firebase usersRef = firebase.getRefUsers();
//        String currentUserUID = firebase.currentUserUID();
//        Firebase RefFriends = firebase.refFriends();
//        RefFriends.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                System.out.println("!!!I heard there is a new friend ");
//                System.out.println("new friend UID " + dataSnapshot.getKey());
//                System.out.println(dataSnapshot.getValue());
//                System.out.println("my UID " + firebase.currentUserUID());
//                User newFriend = dataSnapshot.getValue(User.class);
//                MainPresenter presenter = (MainPresenter) listener;
//                presenter.setNewFriendName(newFriend.getName());
//                presenter.setNewFriendEmail(newFriend.getEmail());
//                presenter.setNewFriendUID(newFriend.getUid());
//                listener.onNewFriend();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//
//    }
//
//    public void listenForFriendRequest(FirebaseAdapter firebase, final IOnFriendRequestListener listener) {
//        System.out.println("Inside listenForFriendRequest");
//        final Firebase usersRef = firebase.getRefUsers();
//        String currentUserUID = firebase.currentUserUID();
//        String path = usersRef.toString() + Constants.SLASH + currentUserUID + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;
//        Firebase receivedFriendRequestFromUser = new Firebase(path);
//        receivedFriendRequestFromUser.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                System.out.println(dataSnapshot.getValue());
//                User user = dataSnapshot.getValue(User.class);
//                MainPresenter presenter = (MainPresenter) listener;
//                presenter.setFriendRequestFromUserName(user.getName());
//                presenter.setFriendRequestFromUserEmail(user.getEmail());
//                presenter.setFriendRequestFromUserUID(user.getUid());
//                listener.friendRequestReceived();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//    //challenges methods
//    public void saveNewChallenge(final FirebaseAdapter firebase, final IOnTaskFinishedListener listener, final Challenge newChallenge) {
//
//        firebase.getRefUsers().child(firebase.currentUserUID()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User creator = dataSnapshot.getValue(User.class);
//                newChallenge.setCreatorName(creator.getName());
//                newChallenge.setCreatorID(firebase.currentUserUID());
//                Firebase refUserChallenges = firebase.refUserChallenges();
//                Firebase refNewChallengeByUser = refUserChallenges.child(newChallenge.getId());
//                refNewChallengeByUser.setValue(newChallenge);
//
//                Firebase refChallenges = firebase.getRefChallanges();
//                Firebase refNewChallenge = refChallenges.child(newChallenge.getId());
//                refNewChallenge.setValue(newChallenge);
//
//                SingleChallengeFragment fragment = new SingleChallengeFragment();
//
//                listener.onSuccess();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//
//
//    }
//}
//