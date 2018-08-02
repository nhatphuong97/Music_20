package com.framgia.music_20.data.repository;

import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.source.DataCallBack;
import com.framgia.music_20.data.source.SongDataSource;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;

public class SongRepository implements SongDataSource.RemoteDataSource {
    private static SongRepository sInstance;
    private SongRemoteDataSource mSongRemoteDataSource;

    private SongRepository(SongRemoteDataSource songRemoteDataSource) {
        mSongRemoteDataSource = songRemoteDataSource;
    }

    public static synchronized SongRepository getInstance(SongRemoteDataSource songRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(songRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void getSongByGenre(String genre, DataCallBack<MoreData> callBack) {
        mSongRemoteDataSource.getSongByGenre(genre, callBack);
    }
}
