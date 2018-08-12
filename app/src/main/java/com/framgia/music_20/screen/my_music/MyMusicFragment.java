package com.framgia.music_20.screen.my_music;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.local.SongLocalDataSource;
import com.framgia.music_20.data.source.local.contentData.ContentDataLocal;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;
import com.framgia.music_20.screen.list_song.ItemClickListener;
import com.framgia.music_20.screen.play_song.PlayMusicFragment;
import java.util.List;

public class MyMusicFragment extends Fragment implements MyMusicContract.View, ItemClickListener {

    private static final int REQUEST_CODE = 1;
    private MyMusicAdapter mMyMusicAdapter;
    private MyMusicPresenter mMyMusicPresenter;
    private List<Song> mSongs;

    public static MyMusicFragment newInstance() {
        MyMusicFragment fragment = new MyMusicFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mymusic, container, false);
        initView(view);
        checkStoragePermissions();
        swipeRefresh(view);
        return view;
    }

    public void initView(View view) {
        mMyMusicAdapter = new MyMusicAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_offline);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setAdapter(mMyMusicAdapter);
        mMyMusicAdapter.setItemClickListener(this);
    }

    public void initData() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(
                new ContentDataLocal(getContext().getApplicationContext()));
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getInstance();
        SongRepository songRepository =
                SongRepository.getInstance(songRemoteDataSource, songLocalDataSource);
        mMyMusicPresenter = new MyMusicPresenter(songRepository);
        mMyMusicPresenter.setView(this);
        mMyMusicPresenter.getSongOffline();
    }

    private void checkStoragePermissions() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_CODE);
            }
        } else {
            initData();
        }
    }

    @Override
    public void getListSongSucces(List<Song> songList) {
        if (songList != null) {
            mSongs = songList;
            mMyMusicAdapter.updateSong(mSongs);
        } else {
            Toast.makeText(getContext().getApplicationContext(), R.string.text_data_null,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getContext().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onClickListen(int position) {
        Fragment fragment = PlayMusicFragment.getGenreFragment(mSongs, position, true);
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void swipeRefresh(View view) {
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_black10);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                initData();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    Toast.makeText(getContext().getApplicationContext(), R.string.text_deny,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
