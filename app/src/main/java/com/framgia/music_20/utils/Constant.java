package com.framgia.music_20.utils;

import android.support.annotation.IntDef;

public final class Constant {

    public static final int TIME = 2000;
    public static final String BASE_URL = "https://api.soundcloud.com/tracks";
    public static final float MIN_SCALE = 0.25f;
    public static final int TIME_READ = 10000;
    public static final int TIME_CONNECT = 15000;
    public static final int TIME_REQUEST = 15000;
    public static final String COLLECTION = "collection";
    public static final String USER = "user";
    public static final String NEXT_HREF = "next_href";
    public static final String API_KEY = "YOUR_KEY_IS_HERE";
    public static final String METHOD_GET = "GET";
    public static final String ClIENT_ID = "?client_id=" + API_KEY + "&linked_partitioning=1";
    public static final String ALL_LINK = BASE_URL + ClIENT_ID;
    public static final String GENRES_COUNTRY = "&genres=country";
    public static final String GENRES_ROCK = "&genres=rock";
    public static final String GENRES_AMBIENT = "&genres=ambient";
    public static final String GENRES_CLASSIC = "&genres=classic";

    @IntDef({ Tab.TAB_HOME, Tab.TAB_MY_MUSIC, Tab.TAB_ARTIST, Tab.TAB_SEARCH })
    public @interface Tab {
        int TAB_HOME = 0;
        int TAB_MY_MUSIC = 1;
        int TAB_ARTIST = 2;
        int TAB_SEARCH = 3;
    }
}
