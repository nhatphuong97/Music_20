package com.framgia.music_20.screen.list_song;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.MoreData;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.data.repository.SongRepository;
import com.framgia.music_20.data.source.remote.SongRemoteDataSource;
import com.framgia.music_20.screen.main.MainActivity;
import java.util.ArrayList;
import java.util.List;

public class ListSongActivity extends AppCompatActivity implements ListSongContract.View {
    private static final String EXTRA_VALUE = "EXTRA_VALUE";
    private RecyclerView mRecyclerView;
    private List<Song> mSongs = new ArrayList<>();
    private ListSongAdapter mListSongAdapter = new ListSongAdapter(ListSongActivity.this, mSongs);

    public static Intent newInstance(Context context, String genre) {
        Intent intent = new Intent(context, ListSongActivity.class);
        intent.putExtra(EXTRA_VALUE, genre);
        System.out.println(genre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        initView();
    }

    private void initView() {
        SongRemoteDataSource songRemoteDataSource = null;
        SongRepository mSongRepository =
                SongRepository.getInstance(songRemoteDataSource.getInstance());
        ImageButton buttonBack = findViewById(R.id.button_back);
        mRecyclerView = findViewById(R.id.recycleview);
        Intent intent = getIntent();
        String genre = intent.getStringExtra(EXTRA_VALUE);
        System.out.println(genre);
        ListSongPresenter listSongPresenter = new ListSongPresenter(mSongRepository);
        listSongPresenter.setView(this);
        listSongPresenter.getSongsByGenre(genre);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSongs.clear();
                mListSongAdapter.updateSong(mSongs);
                Intent intent = new Intent(ListSongActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void showListSong(MoreData moreData) {
        mSongs = moreData.getSongArrayList();
        System.out.println(mSongs.get(1).getTitle());
        mListSongAdapter.updateSong(mSongs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mListSongAdapter);
    }

    @Override
    public void showError(Exception e) {

    }
}
