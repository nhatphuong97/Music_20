package com.framgia.music_20.screen.play_song;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.utils.Constant;
import de.hdodenhof.circleimageview.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends Fragment
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String ARGUMENT_GENRE = "ARGUMENT_GENRE";
    private static final String FORMAT_TIME = "mm:ss";
    private static final int DELAY_MIN_MORE = 500;
    private static final int DELAY_MIN_LITTLE = 100;
    private static final String PATH_DOWNLOAD = "file://sdcard/Download/";
    private static final String PATH = "/";
    private static final String PATH_MP3 = ".mp3";

    public boolean mIsBound;
    private ImageButton mButtonPlay;
    private SeekBar mSeekBar;
    private TextView mTextSong, mTextArtist, mTextCurrent, mTextDuration;
    private CircleImageView mImageAvata;
    private PlayMusicService mPlayMusicService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayMusicService.MusicBinder binder = (PlayMusicService.MusicBinder) service;
            mPlayMusicService = binder.getService();
            setView();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    public static Fragment getGenreFragment(List<Song> songList, int position) {
        PlayMusicFragment listSongFragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_GENRE, (ArrayList<? extends Parcelable>) songList);
        args.putInt(Constant.EXTRA_POSITION, position);
        listSongFragment.setArguments(args);
        return listSongFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_play_music, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mImageAvata = view.findViewById(R.id.image_avatar);
        mTextSong = view.findViewById(R.id.text_song_name);
        mTextArtist = view.findViewById(R.id.text_artist_name);
        ImageButton buttonExit = view.findViewById(R.id.button_exit);
        ImageButton buttonDownLoad = view.findViewById(R.id.button_download);
        ImageButton buttonLoopAll = view.findViewById(R.id.button_loop_all);
        ImageButton buttonNext = view.findViewById(R.id.button_next);
        mButtonPlay = view.findViewById(R.id.button_play);
        ImageButton buttonPrevious = view.findViewById(R.id.button_previous);
        ImageButton buttonShuffle = view.findViewById(R.id.button_shuffle);
        mTextCurrent = view.findViewById(R.id.text_current_position);
        mTextDuration = view.findViewById(R.id.text_duration);
        mSeekBar = view.findViewById(R.id.seek_bar);

        buttonExit.setOnClickListener(this);
        buttonDownLoad.setOnClickListener(this);
        buttonLoopAll.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonShuffle.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initData() {
        List<Song> songs = getArguments().getParcelableArrayList(ARGUMENT_GENRE);
        int position = getArguments().getInt(Constant.EXTRA_POSITION);
        Intent intent = PlayMusicService.newInstance(getActivity(), songs, position);
        getActivity().startService(intent);
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setView() {
        mTextSong.setText(mPlayMusicService.getSongName());
        mTextArtist.setText(mPlayMusicService.getArtistName());
        SimpleDateFormat timeFormat = new SimpleDateFormat(FORMAT_TIME);
        mTextDuration.setText(timeFormat.format(mPlayMusicService.getDuration()));
        loadImageSong();
        mSeekBar.setMax(mPlayMusicService.getDuration());
        updateTimeSong();
    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat(FORMAT_TIME);
                mTextCurrent.setText(
                        timeFormat.format(mPlayMusicService.mMediaPlayer.getCurrentPosition()));
                handler.postDelayed(this, DELAY_MIN_MORE);
                mSeekBar.setProgress(mPlayMusicService.mMediaPlayer.getCurrentPosition());
            }
        }, DELAY_MIN_LITTLE);
    }

    public void downloadSong(String link) {
        DownloadManager downloadManager =
                (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);
        DownloadManager.Request mRequest = new DownloadManager.Request(uri);
        mRequest.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        mRequest.setDestinationUri(Uri.parse(PATH_DOWNLOAD + mTextSong.getText()));
        mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                PATH + mTextSong.getText() + PATH_MP3);
        Long reference = downloadManager.enqueue(mRequest);
    }

    public void loadImageSong() {
        Glide.with(getContext()).load(mPlayMusicService.getUserAvatar()).into(mImageAvata);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.button_download:
                downloadSong(mPlayMusicService.getLinkDownLoad());
                break;
            case R.id.button_loop_all:
                break;
            case R.id.button_previous:
                mPlayMusicService.previousSong();
                checkNextPrevious();
                setView();
                break;
            case R.id.button_play:
                checkPlay();
                setView();
                break;
            case R.id.button_next:
                mPlayMusicService.nextSong();
                checkNextPrevious();
                setView();
                break;
            case R.id.button_shuffle:
                break;
        }
    }

    private void checkNextPrevious() {
        mButtonPlay.setImageResource(R.drawable.ic_play);
    }

    private void checkPlay() {
        if (mPlayMusicService.isPlaying()) {
            mPlayMusicService.pauseSong();
            mButtonPlay.setImageResource(R.drawable.ic_play);
            setView();
        } else {
            mPlayMusicService.playSong();
            mButtonPlay.setImageResource(R.drawable.ic_pause);
            setView();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPlayMusicService.mMediaPlayer.seekTo(mSeekBar.getProgress());
    }
}
