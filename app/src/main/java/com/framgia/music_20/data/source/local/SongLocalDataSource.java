package com.framgia.music_20.data.source.local;

import com.framgia.music_20.data.source.DataCallbackLocate;
import com.framgia.music_20.data.source.SongDataSource;
import com.framgia.music_20.data.source.local.contentData.ContentDataLocal;

public class SongLocalDataSource implements SongDataSource.LocalDataSource {
    private static SongLocalDataSource sInstance;
    private ContentDataLocal mContentDataLocal;

    private SongLocalDataSource(ContentDataLocal contentDataLocal) {
        mContentDataLocal = contentDataLocal;
    }

    public static synchronized SongLocalDataSource getInstance(ContentDataLocal contentDataLocal) {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource(contentDataLocal);
        }
        return sInstance;
    }

    @Override
    public void getSongByGenreLocal(DataCallbackLocate callBack) {
        callBack.onSuccess(mContentDataLocal.getSongList());
    }
}
