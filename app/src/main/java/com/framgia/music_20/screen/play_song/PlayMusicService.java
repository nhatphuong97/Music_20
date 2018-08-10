package com.framgia.music_20.screen.play_song;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.framgia.music_20.R;
import com.framgia.music_20.data.model.Song;
import com.framgia.music_20.screen.main.MainActivity;
import com.framgia.music_20.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicService extends Service implements MediaPlayer.OnPreparedListener {

    private static final int ID = 1234;

    public List<Song> mSongs;
    public int mPosition;
    public MediaPlayer mMediaPlayer;
    private IBinder mIBinder = new MusicBinder();

    public static Intent newInstance(Context context, List<Song> songList, int position) {
        Intent intent = new Intent(context, PlayMusicService.class);
        intent.putParcelableArrayListExtra(Constant.EXTRA_VALUE_LIST,
                (ArrayList<? extends Parcelable>) songList);
        intent.putExtra(Constant.EXTRA_POSITION, position);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mSongs = intent.getParcelableArrayListExtra(Constant.EXTRA_VALUE_LIST);
        mPosition = intent.getIntExtra(Constant.EXTRA_POSITION, 0);
        if (mSongs != null) {
            initMediaPlayer();
        }
        return START_REDELIVER_INTENT;
    }

    public void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(mSongs.get(mPosition).getStreamUrl() + Constant.API_KEY);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            stopSelf();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void pauseSong() {
        mMediaPlayer.pause();
        stopForeground(true);
    }

    public void playSong() {
        mMediaPlayer.start();
        startForeground();
    }

    public String getSongName() {
        return mSongs.get(mPosition) != null ? mSongs.get(mPosition).getTitle() : "";
    }

    public String getArtistName() {
        return mSongs.get(mPosition) != null ? mSongs.get(mPosition).getArtist().getUsername() : "";
    }

    public String getUserAvatar() {
        return mSongs.get(mPosition) != null ? mSongs.get(mPosition).getArtist().getAvatarUrl()
                : "";
    }

    public void nextSong() {
        if (mPosition == (mSongs.size() - 1)) {
            mPosition = 0;
        } else {
            mPosition++;
        }
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void previousSong() {
        if (mPosition == 0) {
            mPosition = (mSongs.size() - 1);
        } else {
            mPosition--;
        }
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public String getLinkDownLoad() {
        return mSongs.get(mPosition).getStreamUrl() + Constant.API_KEY;
    }

    public int getDuration() {
        return mSongs.get(mPosition).getDuration();
    }

    public void startForeground() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(getBaseContext());
        builder.setContentIntent(pendIntent);
        builder.setSmallIcon(R.drawable.ic_icon_app);
        builder.setTicker(mSongs.get(mPosition).getArtworkUrl());
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(false);
        builder.setContentTitle(mSongs.get(mPosition).getTitle());
        builder.setContentText(mSongs.get(mPosition).getArtist().getUsername());
        Notification notification = null;
        notification = builder.build();
        startForeground(ID, notification);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playSong();
    }

    public class MusicBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }
}
