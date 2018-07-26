package com.framgia.music_20.utils;

import android.support.annotation.IntDef;

public final class Constant {

    public static final int TIME = 2000;
    public static final String BASE_URL = "https://api.soundcloud.com/tracks";
    public static final String API_KEY = "KEY_YOU_HERE";
    public static final String ClIENT_ID = "?client_id=" + API_KEY;
    public static final String GENRES_COUNTRY = BASE_URL + ClIENT_ID + "&genres=country";
    public static final String GENRES_ROCK = BASE_URL + ClIENT_ID + "&genres=rock";
    public static final String GENRES_AMBIENT = BASE_URL + ClIENT_ID + "&genres=ambient";

    @IntDef({ Tab.TAB_HOME, Tab.TAB_MY_MUSIC, Tab.TAB_ARTIST, Tab.TAB_SEARCH })
    public @interface Tab {
        int TAB_HOME = 0;
        int TAB_MY_MUSIC = 1;
        int TAB_ARTIST = 2;
        int TAB_SEARCH = 3;
    }
}
