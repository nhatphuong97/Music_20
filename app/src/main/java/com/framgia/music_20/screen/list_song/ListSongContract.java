package com.framgia.music_20.screen.list_song;

import com.framgia.music_20.data.model.MoreData;

public interface ListSongContract {
    interface View {
        void showListSong(MoreData moreData);

        void showError(Exception e);
    }

    interface Presenter {
        void getSongsByGenre(String genre);
    }
}
