package com.framgia.music_20.screen.search;

import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.DataCallBack;

public class SearchSongPresenter implements SearchContract.PresenterSearch {

    private SongRepository mSongRepository;
    private SearchContract.View mView;

    SearchSongPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    public void setView(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void getSongByKey(String link) {
        mSongRepository.getSongByGenre(link, new DataCallBack<MoreData>() {
            @Override
            public void onSuccess(MoreData data) {
                mView.getListSongSucces(data);
            }

            @Override
            public void onFail(Exception e) {
                mView.showError(e);
            }
        });
    }
}
