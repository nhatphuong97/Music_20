package com.framgia.music_20.screen.search;

import com.framgia.music_20.data.model.MoreData;

public interface SearchContract {
    interface PresenterSearch {
        void getSongByKey(String Link);
    }

    interface View {
        void getListSongSucces(MoreData data);

        void showError(Exception e);
    }
}
