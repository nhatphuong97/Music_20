package com.framgia.music_20.screen.play_song;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
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
    public boolean mIsCheck;
    private IBinder mIBinder = new MusicBinder();
    private int PRIORITY_RECEIVE = 2;
    private NotificationCompat.Builder mBuilder;

    public static Intent newInstance(Context context, List<Song> songList, int position,
            boolean isCheck) {
        Intent intent = new Intent(context, PlayMusicService.class);
        intent.putParcelableArrayListExtra(Constant.EXTRA_VALUE_LIST,
                (ArrayList<? extends Parcelable>) songList);
        intent.putExtra(Constant.EXTRA_POSITION, position);
        intent.putExtra(Constant.EXTRA_CHECK_OFFLINE_ONLINE, isCheck);
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
        registerBroadcastReceive();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mSongs = intent.getParcelableArrayListExtra(Constant.EXTRA_VALUE_LIST);
        mPosition = intent.getIntExtra(Constant.EXTRA_POSITION, 0);
        mIsCheck = intent.getBooleanExtra(Constant.EXTRA_CHECK_OFFLINE_ONLINE, false);
        if (mSongs != null) {
            initMediaPlayer(mIsCheck);
        }
        return START_REDELIVER_INTENT;
    }

    public void initMediaPlayer(boolean isCheck) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.reset();
        try {
            if (!isCheck) {
                mMediaPlayer.setDataSource(mSongs.get(mPosition).getStreamUrl() + Constant.API_KEY);
            } else {
                mMediaPlayer.setDataSource(mSongs.get(mPosition).getStreamUrl());
            }
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            stopSelf();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void pauseSong() {
        mMediaPlayer.pause();
        updateNotification(Constant.ACTION_PAUSE);
    }

    public void playSong() {
        mMediaPlayer.start();
        buildNotification();
        updateNotification(Constant.ACTION_PLAY);
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
        initMediaPlayer(mIsCheck);
        updateNotification(Constant.ACTION_NEXT);
    }

    public void previousSong() {
        if (mPosition == 0) {
            mPosition = (mSongs.size() - 1);
        } else {
            mPosition--;
        }
        mMediaPlayer.reset();
        initMediaPlayer(mIsCheck);
        updateNotification(Constant.ACTION_PREVIOUS);
    }

    public String getLinkDownLoad() {
        return mSongs.get(mPosition).getStreamUrl() + Constant.API_KEY;
    }

    public int getDuration() {
        if (mIsCheck) {
            return mMediaPlayer.getDuration();
        } else {
            return mSongs.get(mPosition).getDuration();
        }
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

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("WrongConstant")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case Constant.ACTION_PREVIOUS:
                        previousSong();
                        break;
                    case Constant.ACTION_NEXT:
                        nextSong();
                        break;
                    case Constant.ACTION_PLAY:
                        playSong();
                        break;
                    case Constant.ACTION_PAUSE:
                        pauseSong();
                        break;
                    case  Constant.ACTION_EXIT:
                        pauseSong();
                        stopForeground(true);
                        break;
                }
            }
        }
    };

    private void registerBroadcastReceive() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_PLAY);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_PAUSE);
        intentFilter.addAction(Constant.ACTION_PREVIOUS);
        intentFilter.addAction(Constant.ACTION_EXIT);
        intentFilter.setPriority(PRIORITY_RECEIVE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    private void buildNotification() {
        int apiVersion = Build.VERSION.SDK_INT;

        String songName = mSongs.get(mPosition).getTitle();
        String artistName = mSongs.get(mPosition).getArtist().getUsername();
        String imageString = mSongs.get(mPosition).getArtworkUrl();
        Intent intent = getNotificationIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        mNotification = new Notification.Builder(getApplicationContext()).setContentTitle("text")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(songName)
                .setContentText(artistName)
                .setContentIntent(pendingIntent)
                .build();
        mNotification.contentView = remoteViews;
        mNotification.contentView.setTextViewText(R.id.text_track_name, songName);
        mNotification.contentView.setTextViewText(R.id.text_user_name, artistName);
        NotificationTarget notificationTarget =
                new NotificationTarget(this, R.id.image_song, mNotification.contentView,
                        mNotification, ID);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(imageString)
                .apply(new RequestOptions().placeholder(R.drawable.ic_icon_app))
                .into(notificationTarget);
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(ID, mNotification);
        setListener(mNotification.contentView);
        startForeground(ID, mNotification);
    }

    public void setListener(RemoteViews views) {
        Intent previous = new Intent(Constant.ACTION_PREVIOUS);
        Intent pause = new Intent(Constant.ACTION_PAUSE);
        Intent next = new Intent(Constant.ACTION_NEXT);
        Intent play = new Intent(Constant.ACTION_PLAY);
        Intent exit = new Intent(Constant.ACTION_EXIT);

        PendingIntent pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_previous, pPrevious);

        PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_pause, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_next, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.image_play, pPlay);
        PendingIntent pExit = PendingIntent.getBroadcast(getApplicationContext(), 0, exit,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_close_notification, pExit);
    }

    public void updateNotification(String action) {
        if (action.equals(Constant.ACTION_NEXT) || action.equals(Constant.ACTION_PREVIOUS)) {
            mNotification.contentView.setTextViewText(R.id.text_track_name,
                    mSongs.get(mPosition).getTitle());
            mNotification.contentView.setTextViewText(R.id.text_user_name,
                    mSongs.get(mPosition).getArtist().getUsername());
            String art = mSongs.get(mPosition).getArtworkUrl();
            NotificationTarget notificationTarget =
                    new NotificationTarget(this, R.id.image_song, mNotification.contentView,
                            mNotification, ID);
            if (art == null) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(R.drawable.ic_icon_app)
                        .into(notificationTarget);
            } else {
                Glide.with(getApplicationContext()).asBitmap().load(art).into(notificationTarget);
            }
        } else if (action.equals(Constant.ACTION_PLAY)) {
            mNotification.contentView.setTextViewText(R.id.text_track_name,
                    mSongs.get(mPosition).getTitle());
            mNotification.contentView.setTextViewText(R.id.text_user_name,
                    mSongs.get(mPosition).getArtist().getUsername());
            String art = mSongs.get(mPosition).getArtworkUrl();
            NotificationTarget notificationTarget =
                    new NotificationTarget(this, R.id.image_song, mNotification.contentView,
                            mNotification, ID);
            if (art == null) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(R.drawable.ic_icon_app)
                        .into(notificationTarget);
            } else {
                Glide.with(getApplicationContext()).asBitmap().load(art).into(notificationTarget);
            }
            mNotification.contentView.setViewVisibility(R.id.image_play, View.GONE);
            mNotification.contentView.setViewVisibility(R.id.image_pause, View.VISIBLE);
            mNotificationManager.notify(ID, mNotification);
        } else if (action.equals(Constant.ACTION_PAUSE)) {
            mNotification.contentView.setViewVisibility(R.id.image_pause, View.GONE);
            mNotification.contentView.setViewVisibility(R.id.image_play, View.VISIBLE);
            mNotificationManager.notify(ID, mNotification);
        }
    }
}
