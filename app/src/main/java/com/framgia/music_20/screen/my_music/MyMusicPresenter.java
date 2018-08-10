package com.framgia.music_20.screen.my_music;

import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.DataCallbackLocate;
import java.util.List;

public class MyMusicPresenter implements MyMusicContract.Presenter {
    private SongRepository mSongRepository;
    private MyMusicContract.View mView;

    MyMusicPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    public void setView(MyMusicContract.View view) {
        mView = view;
    }

    @Override
    public void getSongOffline() {
        mSongRepository.getSongByGenreLocal(new DataCallbackLocate() {
            @Override
            public void onSuccess(List<Song> songList) {
                mView.getListSongSucces(songList);
            }

            @Override
            public void onFail(Exception e) {
                mView.showError(e);
            }
        });
    }
}
