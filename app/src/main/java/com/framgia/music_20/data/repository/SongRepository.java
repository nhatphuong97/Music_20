package com.framgia.music_20.data.repository;

import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.source.DataCallBack;
import com.framgia.music_20.data.source.SongDataSource;
import com.framgia.music_20.data.source.remote.ParseDataSong;

public class SongRepository implements SongDataSource.RemoteDataSource {
    private static SongRepository sInstance;
    private ParseDataSong mParseDataSong;

    private SongRepository(ParseDataSong parseDataSong) {
        mParseDataSong = parseDataSong;
    }

    public static synchronized SongRepository getInstance(ParseDataSong parseDataSong) {
        if (sInstance == null) {
            sInstance = new SongRepository(parseDataSong);
        }
        return sInstance;
    }

    @Override
    public void getSongByGenre(String genre, DataCallBack<MoreData> callBack) {
        mParseDataSong.getSongByGenre(genre, callBack);
    }
}
