package com.framgia.music_20.utils;

import android.support.annotation.IntDef;
import com.framgia.music_20.BuildConfig;

public final class Constant {


    public static final int TIME = 2000;
    public static final int TIME_RESPONE = 1000;
    public static final int TIME_READ = 10000;
    public static final int TIME_CONNECT = 15000;
    public static final String COLLECTION = "collection";
    public static final String USER = "user";
    public static final String NEXT_HREF = "next_href";
    public static final String API_KEY = "?client_id=" + BuildConfig.API_KEY;
    public static final String METHOD_GET = "GET";
    public static final String LIMIT_50 = "&limit=50";
    public static final String LIMIT_20 = "&limit=50";
    public static final String EXTRA_VALUE_LIST = "EXTRA_VALUE_LIST";
    public static final String ALL_LINK =
            "https://api.soundcloud.com/tracks" + API_KEY + "&linked_partitioning=1";
    public static final String GENRES_COUNTRY = ALL_LINK + "&genres=country" + LIMIT_50;
    public static final String GENRES_ROCK = ALL_LINK + "&genres=rock" + LIMIT_50;
    public static final String GENRES_MUSIC = ALL_LINK + "&genres=music" + LIMIT_50;
    public static final String GENRES_AUDIO = ALL_LINK + "&genres=audio" + LIMIT_50;
    public static final String GENRES_AMBIENT = ALL_LINK + "&genres=ambient" + LIMIT_50;
    public static final String GENRES_CLASSIC = ALL_LINK + "&genres=classic" + LIMIT_50;
    public static final String TAG_PLAY_MUSIC ="layout_play_music";
    public static String EXTRA_POSITION = "EXTRA_POSITION";
    public static String EXTRA_CHECK_OFFLINE_ONLINE = "EXTRA_CHECK_OFFLINE_ONLINE";

    public static final String ACTION_PLAY = "com.framgia.music_20.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.framgia.music_20.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.framgia.music_20.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.framgia.music_20.ACTION_NEXT";
    public static final String ACTION_EXIT = "com.framgia.music_20.ACTION_EXIT";

    @IntDef({ Tab.TAB_HOME, Tab.TAB_MY_MUSIC, Tab.TAB_SEARCH, Tab.TAB_ARTIST })
    public @interface Tab {
        int TAB_HOME = 0;
        int TAB_MY_MUSIC = 1;
        int TAB_SEARCH = 2;
        int TAB_ARTIST = 3;
    }
}
