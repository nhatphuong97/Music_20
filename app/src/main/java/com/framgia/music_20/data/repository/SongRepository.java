package com.framgia.music_20.data.repository;

import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.source.DataCallBack;
import com.framgia.music_20.data.source.DataCallbackLocate;
import com.framgia.music_20.data.source.SongDataSource;
import com.framgia.music_20.data.source.local.SongLocalDataSource;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;

public class SongRepository
        implements SongDataSource.RemoteDataSource, SongDataSource.LocalDataSource {
    private static SongRepository sInstance;
    private SongRemoteDataSource mSongRemoteDataSource;
    private SongLocalDataSource mSongLocalDataSource;

    private SongRepository(SongRemoteDataSource songRemoteDataSource,
            SongLocalDataSource songLocalDataSource) {
        mSongRemoteDataSource = songRemoteDataSource;
        mSongLocalDataSource = songLocalDataSource;
    }

    public static synchronized SongRepository getInstance(SongRemoteDataSource songRemoteDataSource,
            SongLocalDataSource songLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(songRemoteDataSource, songLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public void getSongByGenre(String genre, DataCallBack<MoreData> callBack) {
        mSongRemoteDataSource.getSongByGenre(genre, callBack);
    }

    @Override
    public void getSongByGenreLocal(DataCallbackLocate callBack) {
        mSongLocalDataSource.getSongByGenreLocal(callBack);
    }
}
