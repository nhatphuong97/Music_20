package com.framgia.music_20.screen.play_song;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private static final int REQUEST_CODE = 1;

    public boolean mIsBound;
    private ImageButton mButtonPlay, mButtonPlayMini;
    private SeekBar mSeekBar;
    private TextView mTextSong, mTextArtist, mTextCurrent, mTextDuration;
    private CircleImageView mImageAvata, mImageAvataMini;
    private PlayMusicService mPlayMusicService;
    private boolean mIsCheck;
    private ImageButton mButtonShuffle;
    private ImageButton mButtonLoopAll;
    private ConstraintLayout mLayoutHide, mLayoutMini;
    private ImageButton mButtonDownload;
    private Animation mAnimationUp;

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

    public static PlayMusicFragment newInstance() {
        return new PlayMusicFragment();
    }

    public static Fragment getGenreFragment(List<Song> songList, int position, boolean isCheck) {
        PlayMusicFragment listSongFragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_GENRE, (ArrayList<? extends Parcelable>) songList);
        args.putInt(Constant.EXTRA_POSITION, position);
        args.putBoolean(Constant.EXTRA_CHECK_OFFLINE_ONLINE, isCheck);
        listSongFragment.setArguments(args);
        return listSongFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_play_music, container, false);
        initView(view);
        initData();
        registerBroadcastReciever();
        return view;
    }

    private void initView(View view) {

        mLayoutHide = view.findViewById(R.id.layout_hide);
        mLayoutMini = getActivity().findViewById(R.id.layout_mini);

        mImageAvata = view.findViewById(R.id.image_avatar);
        mImageAvataMini = getActivity().findViewById(R.id.image_avatar_mini);
        mTextSong = view.findViewById(R.id.text_song_name);
        mTextArtist = view.findViewById(R.id.text_artist_name);
        ImageButton buttonExit = view.findViewById(R.id.button_exit);
        mLayoutHide = view.findViewById(R.id.layout_hide);
        mLayoutMini = getActivity().findViewById(R.id.layout_mini);
        mButtonDownload = view.findViewById(R.id.button_download);
        mButtonLoopAll = view.findViewById(R.id.button_loop_all);
        ImageButton buttonNext = view.findViewById(R.id.button_next);
        ImageButton buttonNextMini = getActivity().findViewById(R.id.button_next_mini);
        mButtonPlay = view.findViewById(R.id.button_play);
        mButtonPlayMini = getActivity().findViewById(R.id.button_play_mini);
        ImageButton buttonPrevious = view.findViewById(R.id.button_previous);
        ImageButton buttonPreviousMini = getActivity().findViewById(R.id.button_previous_mini);
        mButtonShuffle = view.findViewById(R.id.button_shuffle);
        mTextCurrent = view.findViewById(R.id.text_current_position);
        mTextDuration = view.findViewById(R.id.text_duration);
        mSeekBar = view.findViewById(R.id.seek_bar);
        mLayoutMini.setVisibility(View.VISIBLE);

        mAnimationUp = AnimationUtils.loadAnimation(getContext().getApplicationContext(),
                R.anim.slide_in_up);
        mLayoutHide.setAnimation(mAnimationUp);

        buttonExit.setOnClickListener(this);
        mButtonDownload.setOnClickListener(this);
        mButtonLoopAll.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        mButtonShuffle.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        buttonNextMini.setOnClickListener(this);
        mButtonPlayMini.setOnClickListener(this);
        buttonPreviousMini.setOnClickListener(this);
        mImageAvataMini.setOnClickListener(this);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Song> songs = bundle.getParcelableArrayList(ARGUMENT_GENRE);
            int position = bundle.getInt(Constant.EXTRA_POSITION);
            mIsCheck = bundle.getBoolean(Constant.EXTRA_CHECK_OFFLINE_ONLINE);
            Intent intent = PlayMusicService.newInstance(getActivity(), songs, position, mIsCheck);
            intent.setPackage(getContext().getPackageName());
            getContext().getApplicationContext().startService(intent);
            getContext().getApplicationContext()
                    .bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            if (mIsCheck) {
                mButtonDownload.setVisibility(View.GONE);
            } else {
                mButtonDownload.setVisibility(View.VISIBLE);
            }
            mButtonPlayMini.setImageResource(R.drawable.ic_pause_white);
            mButtonPlay.setImageResource(R.drawable.ic_pause_white);
        }
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
        if (getActivity() != null) {
            if (mIsCheck) {
                Glide.with(getActivity())
                        .load(R.drawable.ic_icon_app)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_icon_app))
                        .into(mImageAvata);
                Glide.with(getActivity().getApplication())
                        .load(R.drawable.ic_icon_app)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_icon_app))
                        .into(mImageAvataMini);
            } else {
                Glide.with(getActivity())
                        .load(mPlayMusicService.getUserAvatar())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_icon_app))
                        .into(mImageAvata);
                Glide.with(getActivity().getApplication())
                        .load(mPlayMusicService.getUserAvatar())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_icon_app))
                        .into(mImageAvataMini);
            }
        }
    }

    private void checkStoragePermisson(String link) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            downloadSong(link);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                mLayoutHide.setVisibility(View.GONE);
                mLayoutMini.setVisibility(View.VISIBLE);
                break;
            case R.id.button_download:
                checkStoragePermisson(mPlayMusicService.getLinkDownLoad());
                break;
            case R.id.button_loop_all:
                checkLoopAll();
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
            case R.id.button_next_mini:
                mPlayMusicService.nextSong();
                checkNextPrevious();
                setView();
                break;
            case R.id.button_previous_mini:
                mPlayMusicService.previousSong();
                checkNextPrevious();
                setView();
                break;
            case R.id.button_play_mini:
                checkPlay();
                setView();
                break;
            case R.id.image_avatar_mini:
                mLayoutMini.setVisibility(View.GONE);
                mLayoutHide.setVisibility(View.VISIBLE);
                mLayoutHide.setAnimation(mAnimationUp);
        }
    }

    private void checkNextPrevious() {
        mButtonPlay.setImageResource(R.drawable.ic_pause_white);
        mButtonPlayMini.setImageResource(R.drawable.ic_pause_white);
    }

    private void checkPlay() {
        if (mPlayMusicService.isPlaying()) {
            mPlayMusicService.pauseSong();
            mButtonPlay.setImageResource(R.drawable.ic_play_white);
            mButtonPlayMini.setImageResource(R.drawable.ic_play_white);
        } else {
            mPlayMusicService.playSong();
            mButtonPlay.setImageResource(R.drawable.ic_pause_white);
            mButtonPlayMini.setImageResource(R.drawable.ic_pause_white);
        }
        setView();
    }

    private void checkLoopAll() {
        if (!mPlayMusicService.mMediaPlayer.isLooping()) {
            mButtonLoopAll.setColorFilter(R.color.colorAccent);
        } else {
            mButtonLoopAll.setColorFilter(R.color.color_black10);
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

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Constant.ACTION_PREVIOUS:
                        setView();
                        break;
                    case Constant.ACTION_NEXT:
                        setView();
                        break;
                    case Constant.ACTION_PLAY:
                        setView();
                        mButtonPlay.setImageResource(R.drawable.ic_pause_white);
                        mButtonPlayMini.setImageResource(R.drawable.ic_pause_white);
                        break;
                    case Constant.ACTION_PAUSE:
                        setView();
                        mButtonPlay.setImageResource(R.drawable.ic_play_white);
                        mButtonPlayMini.setImageResource(R.drawable.ic_play_white);
                        break;
                    case Constant.ACTION_EXIT:
                        mButtonPlay.setImageResource(R.drawable.ic_play_white);
                        mButtonPlayMini.setImageResource(R.drawable.ic_play_white);
                        setView();
                        break;
                }
            }
        }
    };

    private void registerBroadcastReciever() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_PAUSE);
        intentFilter.addAction(Constant.ACTION_PLAY);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_PREVIOUS);
        intentFilter.addAction(Constant.ACTION_EXIT);
        getContext().getApplicationContext().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    //    @Override
    //    public void onPause() {
    //        super.onPause();
    //        getContext().getApplicationContext().unregisterReceiver(mBroadcastReceiver);
    //    }
    //
    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        registerBroadcastReciever();
    //    }
}
