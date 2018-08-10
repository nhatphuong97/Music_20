package com.framgia.music_20.data.source;

import com.framgia.music_20.data.model.MoreData;

public interface SongDataSource {
    interface RemoteDataSource {
        void getSongByGenre(String genre, DataCallBack<MoreData> callBack);
    }

    interface LocalDataSource extends SongDataSource {
        void getSongByGenreLocal(DataCallbackLocate callBack);
    }
}
