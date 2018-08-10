package com.framgia.music_20.screen.my_music;

import com.framgia.music_20.data.model.Song;
import java.util.List;

public interface MyMusicContract {
    interface View {
        void getListSongSucces(List<Song> songList);

        void showError(Exception e);
    }

    interface Presenter {
        void getSongOffline();
    }
}
