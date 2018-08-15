package com.framgia.music_20.screen.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.local.SongLocalDataSource;
import com.framgia.music_20.data.source.local.contentData.ContentDataLocal;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;
import com.framgia.music_20.screen.list_song.ItemClickListener;
import com.framgia.music_20.screen.play_song.PlayMusicFragment;
import com.framgia.music_20.utils.Constant;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements View.OnClickListener, SearchContract.View, ItemClickListener {

    private static final String PATH_SEARCH = "&q=";

    private EditText mTextSearch;
    private List<Song> mSongs = new ArrayList<>();
    private SearchAdapter mSearchAdapter;
    private SearchSongPresenter mSearchSongPresenter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_search, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        mTextSearch = view.findViewById(R.id.edit_search);
        ImageButton buttonSearch = view.findViewById(R.id.button_search);
        RecyclerView recyclerViewSearch = view.findViewById(R.id.recycle_search);

        mSearchAdapter = new SearchAdapter();
        recyclerViewSearch.setLayoutManager(
                new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerViewSearch.setAdapter(mSearchAdapter);

        mSearchAdapter.setItemClickListener(this);
        buttonSearch.setOnClickListener(this);
    }

    public void initData() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(
                new ContentDataLocal(getContext().getApplicationContext()));
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getInstance();
        SongRepository songRepository =
                SongRepository.getInstance(songRemoteDataSource, songLocalDataSource);
        mSearchSongPresenter = new SearchSongPresenter(songRepository);
        mSearchSongPresenter.setView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                String link = Constant.ALL_LINK + PATH_SEARCH + mTextSearch.getText();
                mSearchSongPresenter.getSongByKey(link);
                hideKeyboardFrom(getActivity().getApplicationContext(), view);
                break;
        }
    }

    @Override
    public void onClickListen(int position) {
        Fragment fragment = PlayMusicFragment.getGenreFragment(mSongs, position, false);
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        transaction.add(R.id.container, fragment, Constant.TAG_PLAY_MUSIC);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void getListSongSucces(MoreData data) {
        if (data != null) {
            mSongs = data.getSongArrayList();
            mSearchAdapter.updateSong(mSongs);
        } else {
            Toast.makeText(getContext(), R.string.text_data_null, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
