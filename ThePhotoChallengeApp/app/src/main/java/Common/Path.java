package Common;

import java.util.StringTokenizer;

public class Path {
    public static final String TO_REF_DB = Constants.FIREBASE_PATH;
    public static final String TO_USERS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.USERS;
    public static final String TO_USERS_BY_EMAIL = Constants.FIREBASE_PATH + Constants.SLASH + Constants.USERS_BY_EMAIL;
    public static final String TO_CHALLENGES = Constants.FIREBASE_PATH + Constants.SLASH + Constants.CHALLENGES;
    public static final String TO_CHALLENGES_BY_USER = TO_REF_DB + Constants.SLASH + Constants.CHALLENGES_BY_USER + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.CHALLENGES;    public static final String TO_PHOTOS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.PHOTOS;
    public static final String TO_ALL_PHOTOS = TO_PHOTOS + Constants.SLASH + Constants.ALL_PHOTOS;
    public static final String TO_PHOTOS_BY_CHALLENGES = TO_PHOTOS + Constants.SLASH + Constants.PHOTOS_BY_CHALLENGE;
    public static final String TO_PHOTOS_BY_USERS = TO_PHOTOS + Constants.SLASH + Constants.PHOTOS_BY_USER;

    //placeholder1 - challengeID
    public static final String TO_CURRENT_CHALLENGE_PHOTOS = TO_PHOTOS_BY_CHALLENGES + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.PHOTOS;

    //placeholder - userUID
    public static final String TO_USER_PHOTOS = TO_PHOTOS_BY_USERS + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.PHOTOS;

    public static final String TO_FRIENDS = Constants.FIREBASE_PATH + Constants.SLASH + Constants.FRIENDS;
    public static final String TO_FRIEND_REQUESTS_RECEIVED = TO_USERS + Constants.SLASH + Constants.PLACEHOLDER + Constants.SLASH + Constants.FRIEND_REQUESTS_RECEIVED;
    public static final String TO_FRIEND_REQUESTS_SEND = TO_USERS + Constants.SLASH + Constants.PLACEHOLDER + Constants.SLASH + Constants.FRIEND_REQUESTS_SEND;
    public static final String TO_USER_FRIENDS = TO_FRIENDS + Constants.SLASH + Constants.PLACEHOLDER + Constants.DASH + Constants.FRIENDS;

}
