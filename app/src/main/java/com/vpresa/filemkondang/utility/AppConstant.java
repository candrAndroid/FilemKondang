package com.vpresa.filemkondang.utility;

/**
 * Created by Dwicandra on 9/1/2017.
 */

public class AppConstant {
    public static final long SPLASH_TIME                = 1567;
    public static final long HEADER_TIME                = 5432;

    public static final String MOVIE_ID                 = "id";
    public static final String MOVIE_TITLE              = "title";

//    public static final String ANDROID_KEJAR_IMAGE_URL  = "http://rectmedia.com/wp-content/uploads/2016/04/android-indonesia-kejar.jpg";
//    public static final String GOOGLE_DEV_IMAGE_URL     = "http://dash.coolsmartphone.com/wp-content/uploads/2013/07/Google-Developers-Logo.png";
    public static final String VPRESA_LOGO_IMAGE_URL    = "http://vpresa.com/back/img/backend-v-logo.png";

    public static final String ERROR_CONNECTION_TEXT    = "Connection Problem. Please try again.";
    public static final String NO_MOVIES                = "No Movies Available";
    public static final String NO_GENRES                = "No Genre Available";
    public static final String NO_RATING                = "Not Rated";
    public static final String NO_REVENUE               = "Revenue Not Recorded";
    public static final String NO_BUDGET                = "Budget Not Recorded";
    public static final String NO_RELEASE_DATE          = "Release Date Unpublished";

    public static final int RATING_MAX                  = 10;
//    public static final String PLAY_STORE_URL           = "https://play.google.com/store/apps/details?id=androidkejar.app.mymovielist";
    public static final String SHARE_TITLE              = "[Filem Kondang - Info]";
    public static final int MAX_REVIEW_LENGTH           = 3;
    public static final int RATING_MAX_COUNT            = 80;

    public enum ErrorType {CONNECTION, EMPTY}

    public static final String[] SORT_BY_LIST           = new String[]{"Now Playing", "Popular", "Top Rated", "Coming Soon"};

    public static final String[] MOVIE_LIST_TYPE        = new String[]{"now_playing", "popular", "top_rated", "upcoming"};
}
