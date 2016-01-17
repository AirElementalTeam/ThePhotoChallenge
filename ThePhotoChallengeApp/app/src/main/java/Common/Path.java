package Common;

import java.util.StringTokenizer;

public class Path {


    final String firebaseConnection = Constants.FIREBASE_PATH;
    final String usersConnection = firebaseConnection + Constants.SLASH + Constants.USERS;
    final String usersByEmailConnection = firebaseConnection + Constants.SLASH + Constants.USERS_BY_EMAIL;
    final String challengesConnection = firebaseConnection + Constants.SLASH + Constants.CHALLENGES;
    final String photosConnection = firebaseConnection + Constants.SLASH + Constants.PHOTOS;
    final String themesConnection = firebaseConnection + Constants.SLASH + Constants.THEMES;
    final String userFriends = Constants.FRIENDS;

    //final String pathToOtherUserSendRequests = RefUsers.toString() + Constants.SLASH + otherUID + Constants.SLASH + Constants.FRIEND_REQUESTS_SEND;
    ////

    public static final String TO_REF_DB = Constants.FIREBASE_PATH;
    public static final String TO_USERS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.USERS;
    public static final String TO_USERS_BY_EMAIL = Constants.FIREBASE_PATH + Constants.SLASH + Constants.USERS_BY_EMAIL;
    public static final String TO_CHALLENGES = Constants.FIREBASE_PATH + Constants.SLASH + Constants.CHALLENGES;
    public static final String TO_CHALLENGES_BY_USER = TO_REF_DB + Constants.SLASH + Constants.CHALLENGES_BY_USER + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.CHALLENGES;
    public static final String TO_PHOTOS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.PHOTOS;
    public static final String TO_FRIENDS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.FRIENDS;
    public static final String TO_FRIEND_REQUESTS_RECEIVED = TO_USERS + Constants.SLASH + Constants.PLACEHOLDER + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;
    public static final String TO_FRINED_REQUESTS_SEND = TO_USERS + Constants.SLASH + Constants.PLACEHOLDER + Constants.SLASH + Constants.FRIEND_REQUESTS_SEND;
    public static final String TO_USER_FRIENDS = TO_FRIENDS + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.FRIENDS;

}
