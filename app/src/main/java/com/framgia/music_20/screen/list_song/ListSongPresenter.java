package com.framgia.music_20.screen.list_song;

import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.DataCallBack;

public class ListSongPresenter implements ListSongContract.Presenter {
    private ListSongContract.View mView;
    private SongRepository mSongRepository ;

    public ListSongPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    public void setView(ListSongContract.View view) {
        mView = view;
    }

    @Override
    public void getSongsByGenre(String genre) {
        mSongRepository.getSongByGenre(genre, new DataCallBack<MoreData>() {
            @Override
            public void onSuccess(MoreData data) {
                mView.showListSong(data);
            }

            @Override
            public void onFail(Exception e) {
                mView.showError(e);
            }
        });

    }
}
