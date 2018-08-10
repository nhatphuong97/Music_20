package com.framgia.music_20.screen.list_song;

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
import android.widget.ImageButton;
import android.widget.Toast;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.local.SongLocalDataSource;
import com.framgia.music_20.data.source.local.contentData.ContentDataLocal;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;
import com.framgia.music_20.screen.play_song.PlayMusicFragment;
import java.util.ArrayList;
import java.util.List;

public class ListSongFragment extends Fragment
        implements ListSongContract.View, View.OnClickListener, ItemClickListener {
    private final static String ARGUMENT_GENRE = "ARGUMENT_GENRE";

    private List<Song> mSongs = new ArrayList<>();
    private ListSongAdapter mListSongAdapter;
    private String mGenre;

    public static ListSongFragment getGenreFragment(String genre) {
        ListSongFragment listSongFragment = new ListSongFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_GENRE, genre);
        listSongFragment.setArguments(args);
        return listSongFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_song, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        ImageButton buttonBack = view.findViewById(R.id.button_back);
        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        mListSongAdapter = new ListSongAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mListSongAdapter);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARGUMENT_GENRE);
        }
        buttonBack.setOnClickListener(this);
        mListSongAdapter.setItemClickListener(this);
    }

    public void initData() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(
                new ContentDataLocal(getContext().getApplicationContext()));
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getInstance();
        SongRepository songRepository =
                SongRepository.getInstance(songRemoteDataSource, songLocalDataSource);

        ListSongPresenter listSongPresenter = new ListSongPresenter(songRepository);
        listSongPresenter.setView(this);
        listSongPresenter.getSongsByGenre(mGenre);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                getParentFragment().getChildFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onGetListSongSuccess(MoreData moreData) {
        mSongs = moreData.getSongArrayList();
        mListSongAdapter.updateSong(mSongs);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickListen(int position) {
        Fragment fragment = PlayMusicFragment.getGenreFragment(mSongs, position);
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
