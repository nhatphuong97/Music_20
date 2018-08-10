package com.framgia.music_20.data.source.local.contentData;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.music_20.data.model.Artist;
import com.framgia.music_20.data.model.Song;
import java.util.ArrayList;
import java.util.List;

public class ContentDataLocal {
    private Context mContext;

    public ContentDataLocal(Context context) {
        mContext = context;
    }

    public List<Song> getSongList() {
        List<Song> mSongs = new ArrayList<>();
        ContentResolver musicContent = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicContent.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int pathSong = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int idSong = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleSong = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistSong = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                int id = musicCursor.getInt(idSong);
                String path = musicCursor.getString(pathSong);
                String title = musicCursor.getString(titleSong);
                String artist = musicCursor.getString(artistSong);
                Song song = new Song.Builder().withId(id)
                        .withGenre("")
                        .withTitle(title)
                        .withArtworkUrl("")
                        .withStreamUrl(path)
                        .withDuration(1)
                        .withArtist(new Artist(1, artist, ""))
                        .build();
                mSongs.add(song);
            } while (musicCursor.moveToNext());
        }
        return mSongs;
    }
}
